package com.surveymate.api.domain.group.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.group.dto.GroupReponse;
import com.surveymate.api.domain.group.dto.GroupRequest;
import com.surveymate.api.domain.group.entity.Group;
import com.surveymate.api.domain.group.entity.QGroup;
import com.surveymate.api.domain.group.exception.GroupCodeGenerationException;
import com.surveymate.api.domain.group.exception.GroupNotFoundException;
import com.surveymate.api.domain.group.mapper.GroupMapper;
import com.surveymate.api.domain.group.repository.GroupRepository;
import com.surveymate.api.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final GroupMapper groupMapper;

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
    public Group createGroup(String groupName, String groupAuthCode, String userId) {
        if(!StringUtils.hasText(groupName) || !StringUtils.hasText(groupAuthCode)) {
            throw new GroupCodeGenerationException();
        }

        String groupCode = createGroupCode();
        Group group = Group.builder()
                .groupName(groupName)
                .groupCode(groupCode)
                .groupAuthCode(groupAuthCode)
                .build();

        if(userId != null) {
            group.setCreateId(userId);
            group.setUpdateId(userId);
        }

        try{
            groupRepository.save(group);
        }catch(Exception e){
            throw new CustomRuntimeException("GROUP_SAVE_ERROR", e);
        }
        return group;
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
                    .where(qGroup.id.eq(group.getId())) // groupId 기준
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
    public GroupReponse selectGroupByUserId(GroupRequest request) {
        QMember member = QMember.member;
        try{
            Group group = jpaQueryFactory
                    .select(member.group)
                    .from(member)
                    .where(member.memNum.eq(request.getMemNum()))
                    .fetchOne();
            if (group == null) {
                throw new GroupNotFoundException();
            }
            return groupMapper.toDto(group);

        }catch (Exception e){
            throw new CustomRuntimeException("INTERNAL_SERVER_ERROR", e);
        }
    }
}
