package com.surveymate.api.member.service;

import com.surveymate.api.common.enums.FilePath;
import com.surveymate.api.file.entity.UploadedFile;
import com.surveymate.api.file.service.FileService;
import com.surveymate.api.member.dto.MemberDTO;
import com.surveymate.api.member.dto.MemberSignupDTO;
import com.surveymate.api.member.entity.Member;
import com.surveymate.api.member.mapper.MemberMapper;
import com.surveymate.api.member.repository.MemberRepository;
import com.surveymate.api.member.exception.UserAlreadyExistsException;
import com.surveymate.api.common.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;
    private final CodeGenerator codeGenerator;
    private final FileService fileService;

    @Override
    public Map<String, String> checkDuplicateId(String userId) {
        boolean existId = isUserIdDuplicate(userId);
        Map<String, String> param = new HashMap<>();
        if(existId){
            param.put("status", "FAILURE");
            param.put("errorCode", "USERID_TAKEN");
        }else{
            param.put("status", "SUCCESS");
        }
        return param;
    }

    @Transactional
    @Override
    public MemberDTO createMember(MemberSignupDTO memberSignupDTO) throws Exception {
        Member member = memberMapper.toEntity(memberSignupDTO);

        if(isUserIdDuplicate(member.getUserId())){
            throw new UserAlreadyExistsException("이미 존재하는 ID 입니다. : " + member.getUserId());
        }

        MultipartFile file = memberSignupDTO.getProfileImage();
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

    public boolean isUserIdDuplicate(String userId) {
        Optional<Member> duplicate = memberRepository.findByUserId(userId);
        return duplicate.isPresent();
    }
}
