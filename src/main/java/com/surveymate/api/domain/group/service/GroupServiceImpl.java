package com.surveymate.api.domain.group.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surveymate.api.domain.group.entity.Group;
import com.surveymate.api.domain.group.entity.QGroup;
import com.surveymate.api.domain.group.exception.GroupCodeGenerationException;
import com.surveymate.api.domain.group.exception.GroupNotFoundException;
import com.surveymate.api.domain.group.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
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

        groupRepository.save(group);
        return group;
    }

    @Override
    public boolean isExistsGroupCode(String groupCode) {
        return groupRepository.findByGroupCode(groupCode).isPresent();
    }

    @Override
    public void setGroupAuthCode(String groupCode, String authCode) {
        QGroup qGroup = QGroup.group;

        Group group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(GroupNotFoundException::new);

        jpaQueryFactory
                .update(qGroup)
                .set(qGroup.groupAuthCode, authCode) // authCode 업데이트
                .where(qGroup.id.eq(group.getId())) // groupId 기준
                .execute();
    }

    @Override
    public Group validateGroupCodeAndGroupAuthCode(String groupCode, String groupAuthCode) {
        Optional<Group> group = groupRepository.findByGroupCodeAndGroupAuthCode(groupCode, groupAuthCode);
        if(group.isEmpty()){
            throw new GroupNotFoundException();
        }
        return group.get();
    }
}
