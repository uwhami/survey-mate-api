package com.surveymate.api.domain.group.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.common.enums.MemberRole;
import com.surveymate.api.common.enums.MemberStatus;
import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.group.dto.GroupReponse;
import com.surveymate.api.domain.group.dto.GroupRequest;
import com.surveymate.api.domain.group.entity.Group;
import com.surveymate.api.domain.group.entity.QGroup;
import com.surveymate.api.domain.group.exception.GroupCodeGenerationException;
import com.surveymate.api.domain.group.exception.GroupNotFoundException;
import com.surveymate.api.domain.group.mapper.GroupMapper;
import com.surveymate.api.domain.group.repository.GroupRepository;
import com.surveymate.api.domain.member.dto.MemberResponse;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.mapper.MemberMapper;
import com.surveymate.api.domain.member.repository.MemberRepository;
import com.surveymate.api.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final GroupMapper groupMapper;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;



    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();


    private String generateRandomCode() {

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    private String createGroupCode() {
        String randomCode;
        int maxAttempts = 100; // 최대 시도 횟수
        int attempts = 0;

        do {
            if (attempts >= maxAttempts) {
                throw new GroupCodeGenerationException();
            }
            randomCode = generateRandomCode();
            attempts++;
        } while (isExistsGroupCode(randomCode));

        return randomCode;
    }

    @Transactional
    @Override
    public Group createGroup(String groupName, String groupAuthCode, String memNum) {
        if(!StringUtils.hasText(groupName) || !StringUtils.hasText(groupAuthCode)) {
            throw new GroupCodeGenerationException();
        }

        String groupCode = createGroupCode();
        Group group = Group.builder()
                .groupName(groupName)
                .groupCode(groupCode)
                .groupAuthCode(groupAuthCode)
                .build();

        /* 회원가입 시에는 "시스템"으로 들어가는 것 방지 */
        if(memNum != null) {
            group.setCreateMemNum(memNum);
            group.setUpdateMemNum(memNum);
        }

        try{
            groupRepository.save(group);
        }catch(Exception e){
            throw new CustomRuntimeException("GROUP_SAVE_ERROR", e);
        }
        return group;
    }

    @Transactional
    @Override
    public GroupReponse createGroupFromRequest(GroupRequest groupRequest) throws Exception{
        Group group = createGroup(groupRequest.getGroupName(), groupRequest.getGroupAuthCode(), null);
        /* 사용자에 그룹정보 저장. */
        memberService.assignGroupToMember(groupRequest.getMemNum(), group);
        memberService.updateMemberRoleToUser(groupRequest.getMemNum(), MemberRole.MANAGER);
        return groupMapper.toDto(group);
    }

    @Override
    public boolean isExistsGroupCode(String groupCode) {
        return groupRepository.findByGroupCode(groupCode) != null;
    }

    @Override
    public void setGroupAuthCode(String groupCode, String authCode) {
        QGroup qGroup = QGroup.group;

        Group group = groupRepository.findByGroupCode(groupCode);
        if (group == null) {
            throw new GroupNotFoundException();
        }

        try{
            long appectedRows =  jpaQueryFactory
                    .update(qGroup)
                    .set(qGroup.groupAuthCode, authCode) // authCode 업데이트
                    .where(qGroup.groupId.eq(group.getGroupId())) // groupId 기준
                    .execute();
            if(appectedRows == 0){
                throw new GroupNotFoundException();
            }
        }catch(Exception e){
            throw new CustomRuntimeException(e.getMessage(), e);
        }


    }

    @Override
    public Group validateGroupCodeAndGroupAuthCode(String groupCode, String groupAuthCode) {
        Group group = groupRepository.findByGroupCodeAndGroupAuthCode(groupCode, groupAuthCode);
        if(group == null){
            throw new GroupNotFoundException();
        }
        return group;
    }

    @Override
    public GroupReponse selectGroupByUserId(long groupId) {
        try{
            Optional<Group> group = groupRepository.findById(groupId);
            if(group.isEmpty()){
                throw new GroupNotFoundException();
            }
            return groupMapper.toDto(group.get());
        }catch (Exception e){
            throw new CustomRuntimeException("INTERNAL_SERVER_ERROR", e);
        }
    }

    @Override
    public GroupReponse updateGroup(GroupRequest request) {

        Optional<Group> optionalGroup = groupRepository.findById(request.getGroupId());
        if(optionalGroup.isEmpty()){
            throw new GroupNotFoundException();
        }
        Group group = optionalGroup.get();
        group.updateGroup(request.getGroupName(), request.getGroupAuthCode());

        groupRepository.save(group);

        return groupMapper.toDto(group);
    }

    @Override
    public PagedResponse<MemberResponse> getActiveMembersByGroupId(Long groupId, Pageable pageable) {
        Page<Member> memberPage = memberRepository.findActiveMembersByGroupId(groupId, MemberStatus.ACTIVE, pageable);
        return new PagedResponse<>(memberPage.map(memberMapper::toDTO));
    }
}
