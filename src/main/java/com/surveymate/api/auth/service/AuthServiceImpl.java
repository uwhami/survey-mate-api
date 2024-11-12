package com.surveymate.api.auth.service;

import com.surveymate.api.auth.dto.RegisterRequest;
import com.surveymate.api.auth.mapper.AuthMemberMapper;
import com.surveymate.api.common.enums.FilePath;
import com.surveymate.api.common.util.CodeGenerator;
import com.surveymate.api.file.entity.UploadedFile;
import com.surveymate.api.file.service.FileService;
import com.surveymate.api.member.dto.MemberDTO;
import com.surveymate.api.member.entity.Member;
import com.surveymate.api.member.exception.UserAlreadyExistsException;
import com.surveymate.api.member.mapper.MemberMapper;
import com.surveymate.api.member.repository.MemberRepository;
import com.surveymate.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


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


    @Override
    public MemberDTO createMember(RegisterRequest registerRequest) throws Exception {
        Member member = authMemberMapper.toEntity(registerRequest);

        if(memberService.isUserIdDuplicate(member.getUserId())){
            throw new UserAlreadyExistsException("이미 존재하는 ID 입니다. : " + member.getUserId());
        }

        MultipartFile file = registerRequest.getProfileImage();
        UploadedFile savedFile = null;
        if(file != null){
            savedFile = fileService.uploadFileAndCreateThumbnail(file, FilePath.MEMBER_PROFILE);
            member.setProfileImageUuid(savedFile.getFileId());
        }

        member.setMemNum(codeGenerator.generateCode("MU01"));
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member = memberRepository.save(member);

        MemberDTO memberDTO = memberMapper.toDTO(member);
        if(savedFile != null){
            memberDTO.setProfileImageUri(savedFile.getFilePath());
        }

        return memberDTO;
    }
}
