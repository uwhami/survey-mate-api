package com.surveymate.api.domain.auth.service;

import com.surveymate.api.common.enums.MemberStatus;
import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.auth.dto.LoginRequest;
import com.surveymate.api.domain.auth.dto.PasswordResetRequest;
import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.auth.entity.LoginHistory;
import com.surveymate.api.domain.auth.mapper.AuthMemberMapper;
import com.surveymate.api.common.enums.FilePath;
import com.surveymate.api.common.util.CodeGenerator;
import com.surveymate.api.domain.auth.model.CustomUserDetails;
import com.surveymate.api.domain.auth.repository.LoginHistoryRepository;
import com.surveymate.api.domain.member.exception.UserNotFoundException;
import com.surveymate.api.email.exception.EmailAlreadyExistsException;
import com.surveymate.api.email.service.EmailService;
import com.surveymate.api.file.entity.UploadedFile;
import com.surveymate.api.file.service.FileService;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.exception.UserAlreadyExistsException;
import com.surveymate.api.domain.member.repository.MemberRepository;
import com.surveymate.api.domain.member.service.MemberService;
import com.surveymate.api.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final AuthMemberMapper authMemberMapper;
    private final CodeGenerator codeGenerator;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;
    private final LoginHistoryRepository loginHistoryRepository;
    private final GuavaCacheService cacheService;

    @Override
    public boolean checkDuplicateId(String userId) {
        return memberService.checkDuplicateId(userId);
    }

    @Override
    public String sendVerificationCode(String email) {
        try{
            if(memberService.checkDuplicatedEmail(email)) {
                throw new EmailAlreadyExistsException();
            }
            String code = generateVerificationCode();
            emailService.sendEmail(email, "회원가입 인증번호", code);
            return code;
        }catch (EmailAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomRuntimeException("이메일 인증코드 발송 중 에러가 발생했습니다.", e);
        }

    }

    public static String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(10000); // 0부터 9999 사이의 숫자 생성
        return String.format("%04d", code); // 4자리 숫자로 포맷 (예: 0001, 0234)
    }


    @Override
    public void createMember(RegisterRequest registerRequest) throws Exception {
        Member member = authMemberMapper.toEntity(registerRequest);

        if (memberService.existsByUserId(member.getUserId())) {
            throw new UserAlreadyExistsException("이미 존재하는 ID 입니다. : " + member.getUserId());
        }

        if(memberService.checkDuplicatedEmail(member.getUserEmail())){
            throw new EmailAlreadyExistsException();
        }

        try {
            MultipartFile file = registerRequest.getProfileImage();
            UploadedFile savedFile;
            if (file != null && !file.isEmpty()) {
                savedFile = fileService.uploadFileAndCreateThumbnail(file, FilePath.MEMBER_PROFILE);
                member.setProfileImageUuid(savedFile.getFileId());
            }

            member.setMemNum(codeGenerator.generateCode("MU01"));
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setMemStatus(MemberStatus.ACTIVE);
            memberRepository.save(member);

        } catch (RuntimeException e) {
            throw new CustomRuntimeException("회원가입 에러", e);
        }

    }


    public Map<String, String> loginMember(LoginRequest loginRequest) {

        try {

            // 사용자 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword())
            );

            memberService.resetPasswordError(loginRequest.getUserId());

            UUID uuid = UUID.randomUUID();
            String memNum = ((CustomUserDetails) authentication.getPrincipal()).getMemNum();
            LoginHistory loginHistory = LoginHistory.builder()
                    .uuid(uuid)
                    .memNum(memNum)
                    .build();
            loginHistoryRepository.save(loginHistory);

            cacheService.saveToCache(uuid.toString(), memNum);

            List<GrantedAuthority> authorities = authentication.getAuthorities().stream().collect(Collectors.toList());
            // 인증 성공 시 JWT 토큰 생성
            String accessToken = jwtTokenProvider.generateToken(uuid.toString(), authorities);
            String refreshToken = jwtTokenProvider.generateRefreshToken(uuid.toString(), authorities);

            return Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            );

        } catch (BadCredentialsException ex) {   // 아이디나 비밀번호가 틀린 경우.
            // 사용자 ID가 존재하는지 확인
            if (memberService.existsByUserId(loginRequest.getUserId())) {
                // throw new UsernameNotFoundException("User not found");
                // 아이디는 맞지만 비밀번호가 틀렸을 경우 오류 횟수 증가
                memberService.increasePasswordError(loginRequest.getUserId());
            }
            throw new RuntimeException("Authentication failed: " + ex.getMessage());
        } catch (Exception ex) {
            throw new CustomRuntimeException(ex.getMessage(), ex);
        }

    }

    public Map<String, String> refreshTokens(String authorizationHeader) {

        try {

            String refreshToken = authorizationHeader.replace("Bearer ", "");

            Claims claims = jwtTokenProvider.validateToken(refreshToken);
            String uuid = claims.getSubject();
            List<GrantedAuthority> authorities = getAuthoritiesFromToken(claims);


            // 인증 성공 시 JWT 토큰 생성
            String accessToken = jwtTokenProvider.generateToken(uuid, authorities);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(uuid, authorities);

            return Map.of(
                    "accessToken", accessToken,
                    "refreshToken", newRefreshToken
            );

        } catch (Exception ex) {
            throw new CustomRuntimeException(ex.getMessage(), ex);
        }
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(Claims claims) {

        // roles 클레임 추출
        List<String> roles = claims.get("roles", List.class);

        // SimpleGrantedAuthority 리스트로 변환
        return roles.stream()
                .map(SimpleGrantedAuthority::new) // ROLE_USER, ROLE_MANAGER 등을 SimpleGrantedAuthority로 매핑
                .collect(Collectors.toList());
    }


    public String findUserIdByUSerEmail(String email) {
        try{
            Member member = memberRepository.findByUserEmail(email)
                    .orElseThrow(() -> new UserNotFoundException());

            return member.getUserId();
        } catch (UserNotFoundException e){
            throw e;
        } catch (Exception ex) {
            throw new CustomRuntimeException("회원 아이디 찾기에서 에러가 발생했습니다.", ex);
        }

    }

    @Override
    public void passwordReset(PasswordResetRequest request) {
        memberService.passwordReset(request);
    }
}
