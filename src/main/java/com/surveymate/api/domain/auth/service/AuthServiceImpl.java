package com.surveymate.api.domain.auth.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surveymate.api.common.enums.FilePath;
import com.surveymate.api.common.enums.MemberRole;
import com.surveymate.api.common.enums.MemberStatus;
import com.surveymate.api.common.enums.SocialType;
import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.common.util.CodeGenerator;
import com.surveymate.api.domain.auth.dto.LoginRequest;
import com.surveymate.api.domain.auth.dto.PasswordResetRequest;
import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.auth.entity.LoginHistory;
import com.surveymate.api.domain.auth.entity.QLoginHistory;
import com.surveymate.api.domain.auth.mapper.AuthMemberMapper;
import com.surveymate.api.domain.auth.repository.LoginHistoryRepository;
import com.surveymate.api.domain.group.entity.Group;
import com.surveymate.api.domain.group.service.GroupService;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.entity.QMember;
import com.surveymate.api.domain.member.exception.UserAlreadyExistsException;
import com.surveymate.api.domain.member.exception.UserNotFoundException;
import com.surveymate.api.domain.member.repository.MemberRepository;
import com.surveymate.api.domain.member.service.MemberService;
import com.surveymate.api.email.exception.EmailAlreadyExistsException;
import com.surveymate.api.email.service.EmailService;
import com.surveymate.api.file.entity.UploadedFile;
import com.surveymate.api.file.service.FileService;
import com.surveymate.api.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

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
    private final GroupService groupService;
    private final JPAQueryFactory queryFactory;

    @Value("${IP}")
    private String prodIp;

    @Override
    public boolean checkDuplicateId(String userId) {
        return memberService.checkDuplicateId(userId);
    }

    @Override
    public String sendVerificationCode(String email) {
        try {
            if (memberService.checkDuplicatedEmail(email)) {
                throw new EmailAlreadyExistsException(email);
            }
            String code = generateVerificationCode();
            emailService.sendEmail(email, "회원가입 인증번호", code);
            return code;
        } catch (Exception e) {
            throw new CustomRuntimeException(e.getMessage(), e);
        }

    }

    public static String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(10000); // 0부터 9999 사이의 숫자 생성
        return String.format("%04d", code); // 4자리 숫자로 포맷 (예: 0001, 0234)
    }

    public String generatePassword() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    @Transactional
    @Override
    public Map<String, String> socialLoginOrRegisterUser(Map<String, Object> userInfo, SocialType socialType) throws Exception {
        String socialEmail = String.valueOf(userInfo.get("email"));
        String userId = String.valueOf(userInfo.get("id"));
        String userName = String.valueOf(userInfo.get("name"));
        try {
            Optional<Member> member = memberRepository.findByUserEmail(socialEmail, MemberStatus.ACTIVE);
            if (member.isPresent()) {
                userId = member.get().getUserId();
            } else {
                RegisterRequest registerRequest = RegisterRequest.builder()
                        .userId(userId)
                        .password(generatePassword())
                        .userName(userName)
                        .userEmail(socialEmail)
                        .joinDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                        .memRole("0")
                        .build();
                createMember(registerRequest, socialType);
            }

        } catch (Exception e) {
            throw new CustomRuntimeException("소셜 로그인 중 에러 발생.", e);
        }

        LoginRequest loginRequest = LoginRequest.builder().userId(userId).build();
        return loginMember(loginRequest, socialType.getValue());
    }

    @Transactional
    @Override
    public void createMember(RegisterRequest registerRequest) throws Exception {
        createMember(registerRequest, SocialType.HOMEPAGE);
    }

    @Transactional
    @Override
    public void createMember(RegisterRequest registerRequest, SocialType socialType) throws Exception {
        Member member = authMemberMapper.toEntity(registerRequest);

        if (memberService.existsByUserId(member.getUserId())) {
            throw new UserAlreadyExistsException("이미 존재하는 ID 입니다. : " + member.getUserId());
        }

        if (StringUtils.hasText(member.getUserEmail()) && memberService.checkDuplicatedEmail(member.getUserEmail())) {
            throw new EmailAlreadyExistsException(member.getUserEmail());
        }

        try {
            MultipartFile file = registerRequest.getProfileImage();
            UploadedFile savedFile;
            if (file != null && !file.isEmpty()) {
                savedFile = fileService.uploadFileAndCreateThumbnail(file, FilePath.MEMBER_PROFILE);
                member.setProfileImageUuid(savedFile.getFileId());
            }
            if(!StringUtils.hasText(member.getUserEmail())){
                member.setUserEmail(null);
            }

            member.setMemNum(codeGenerator.generateCode("MU01"));
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setMemStatus(MemberStatus.ACTIVE);
            member.setSocialType(socialType);
            member.setMemRole(MemberRole.USER);

            if(StringUtils.hasText(registerRequest.getGroupName())){
                Group group = groupService.createGroup(registerRequest.getGroupName(), registerRequest.getGroupAuthCode(), member.getMemNum());
                member.setGroup(group);
                member.setMemRole(MemberRole.MANAGER);
            }else if(StringUtils.hasText(registerRequest.getGroupCode())){
                Group group = groupService.validateGroupCodeAndGroupAuthCode(registerRequest.getGroupCode(), registerRequest.getGroupAuthCode());
                member.setGroup(group);
            }

            memberRepository.save(member);

        } catch (Exception ex) {
            throw new CustomRuntimeException(ex.getMessage(), ex);
        }

    }

    @Override
    public Map<String, String> loginMember(LoginRequest loginRequest) {
        return loginMember(loginRequest, 0);
    }

    @Override
    public Map<String, String> loginMember(LoginRequest loginRequest, int social) {

        try {

            if (social == 0) {
                // 사용자 인증
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword())
                );
            }

            Optional<Member> optionalMember = memberRepository.findByUserId(loginRequest.getUserId(), MemberStatus.ACTIVE);
            if (optionalMember.isEmpty()) {
                throw new UserNotFoundException();
            }
            Member member = optionalMember.get();
            memberService.resetPasswordError(loginRequest.getUserId());

            UUID uuid = UUID.randomUUID();
            ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
            bb.putLong(uuid.getMostSignificantBits());
            bb.putLong(uuid.getLeastSignificantBits());
            byte[] uuidBytes = bb.array();
            LoginHistory loginHistory = LoginHistory.builder()
                    .uuid(uuidBytes)
                    .memNum(member.getMemNum())
                    .build();
            loginHistoryRepository.save(loginHistory);

            cacheService.saveToCache(uuid.toString(), member.getMemNum());
            String profileImgPath = "/" + fileService.getThumbnailFilePath(member.getProfileImageUuid());

            // 인증 성공 시 JWT 토큰 생성
            String accessToken = jwtTokenProvider.generateToken(uuid.toString(), member);
            String refreshToken = jwtTokenProvider.generateRefreshToken(uuid.toString(), member);

            return Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken,
                    "profileImgPath", profileImgPath
            );

        } catch (BadCredentialsException ex) {   // 아이디나 비밀번호가 틀린 경우.
            // 사용자 ID가 존재하는지 확인
            if (memberService.existsByUserId(loginRequest.getUserId())) {
                // throw new UsernameNotFoundException("User not found");
                // 아이디는 맞지만 비밀번호가 틀렸을 경우 오류 횟수 증가
                memberService.increasePasswordError(loginRequest.getUserId());
            }
            throw new CustomRuntimeException("Authentication failed: " + ex.getMessage());
        } catch (Exception ex) {
            throw new CustomRuntimeException(ex.getMessage(), ex);
        }

    }

    public Map<String, String> refreshTokens(String authorizationHeader) {

        try {

            String refreshToken = authorizationHeader.replace("Bearer ", "");

            Claims claims = jwtTokenProvider.validateToken(refreshToken);
            String uuid = claims.getSubject();
            Member member = findMemberByLoginHistoryUuid(UUID.fromString(uuid));

            // 인증 성공 시 JWT 토큰 생성
            String accessToken = jwtTokenProvider.generateToken(uuid, member);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(uuid, member);

            return Map.of(
                    "accessToken", accessToken,
                    "refreshToken", newRefreshToken
            );

        } catch (Exception ex) {
            throw new CustomRuntimeException(ex.getMessage(), ex);
        }
    }


    public String findUserIdByUSerEmail(String email) {
        try {
            Member member = memberRepository.findByUserEmail(email, MemberStatus.ACTIVE)
                    .orElseThrow(() -> new UserNotFoundException());

            return member.getUserId();
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception ex) {
            throw new CustomRuntimeException("회원 아이디 찾기에서 에러가 발생했습니다.", ex);
        }

    }

    @Override
    public void passwordReset(PasswordResetRequest request) {
        memberService.passwordReset(request);
    }

    @Override
    public Member findMemberByLoginHistoryUuid(UUID uuid) {

        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        byte[] uuidBytes = bb.array();

        QMember qMember = QMember.member;
        QLoginHistory qLoginHistory = QLoginHistory.loginHistory;
        Member member = queryFactory
                        .select(qMember)
                        .from(qMember)
                        .join(qLoginHistory).on(qMember.memNum.eq(qLoginHistory.memNum))
                        .where(qLoginHistory.uuid.eq(uuidBytes))
                        .fetchOne();
        if(member == null){
            throw new UserNotFoundException();
        }
        return member;
    }

}
