package com.surveymate.api.domain.auth.service;

import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.auth.dto.LoginRequest;
import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.auth.mapper.AuthMemberMapper;
import com.surveymate.api.common.enums.FilePath;
import com.surveymate.api.common.util.CodeGenerator;
import com.surveymate.api.email.service.EmailService;
import com.surveymate.api.file.entity.UploadedFile;
import com.surveymate.api.file.service.FileService;
import com.surveymate.api.domain.member.dto.MemberResponseDTO;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.exception.UserAlreadyExistsException;
import com.surveymate.api.domain.member.mapper.MemberMapper;
import com.surveymate.api.domain.member.repository.MemberRepository;
import com.surveymate.api.domain.member.service.MemberService;
import com.surveymate.api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Random;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final AuthMemberMapper authMemberMapper;
    private final MemberMapper memberMapper;
    private final CodeGenerator codeGenerator;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;

    @Override
    public boolean checkDuplicateId(String userId) {
        return memberService.checkDuplicateId(userId);
    }

    @Override
    public String sendVerificationCode(String email) {
        String code = generateVerificationCode();
        emailService.sendEmail(email, "회원가입 인증번호", code);
        return code;
    }

    public static String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(10000); // 0부터 9999 사이의 숫자 생성
        return String.format("%04d", code); // 4자리 숫자로 포맷 (예: 0001, 0234)
    }


    @Override
    public MemberResponseDTO createMember(RegisterRequest registerRequest) throws Exception {
        Member member = authMemberMapper.toEntity(registerRequest);

        if(memberService.existsByUserId(member.getUserId())){
            throw new UserAlreadyExistsException("이미 존재하는 ID 입니다. : " + member.getUserId());
        }

        try {
            MultipartFile file = registerRequest.getProfileImage();
            UploadedFile savedFile;
            if (file != null && !file.isEmpty()) {
                savedFile = fileService.uploadFileAndCreateThumbnail(file, FilePath.MEMBER_PROFILE);
                member.setProfileImageUuid(savedFile.getFileId());
            } else {
                savedFile = fileService.getDefaultFilePath();
            }

            member.setMemNum(codeGenerator.generateCode("MU01"));
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member = memberRepository.save(member);

            MemberResponseDTO memberResponseDTO = memberMapper.toDTO(member);
            memberResponseDTO.setProfileImageUri(savedFile.getFilePath());

            return memberResponseDTO;

        }catch(RuntimeException e){
            throw new CustomRuntimeException("회원가입 에러", e);
        }

    }


    public Map<String, String> loginMember(LoginRequest loginRequest) {

        try{

            // 사용자 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword())
            );

            memberService.resetPasswordError(loginRequest.getUserId());

            // 인증 성공 시 JWT 토큰 생성
            String accessToken = jwtTokenProvider.generateToken(authentication);
            String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

            return Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            );
        }catch (BadCredentialsException ex) {   // 아이디나 비밀번호가 틀린 경우.
            // 사용자 ID가 존재하는지 확인
            if (memberService.existsByUserId(loginRequest.getUserId())) {
                // throw new UsernameNotFoundException("User not found");
                // 아이디는 맞지만 비밀번호가 틀렸을 경우 오류 횟수 증가
                memberService.increasePasswordError(loginRequest.getUserId());
            }
            throw new RuntimeException("Authentication failed: " + ex.getMessage());
        } catch(Exception ex){
            throw new RuntimeException("Authentication failed: " + ex.getMessage());
        }

    }

    public Map<String, String> refreshTokens(String authorizationHeader) {

        try{

            String refreshToken = authorizationHeader.replace("Bearer ", "");
            String accessToken = null;

            if(jwtTokenProvider.validateToken(refreshToken)){
                Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);

                // 인증 성공 시 JWT 토큰 생성
                accessToken = jwtTokenProvider.generateToken(authentication);
                refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
            }

            return Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            );

        } catch(Exception ex){
            throw new RuntimeException("Authentication failed: " + ex.getMessage());
        }

    }
}
