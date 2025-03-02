package com.surveymate.api.domain.group.entity;


import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
@Table(name = "groups")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId; // 그룹 ID (PK)

    @Column(nullable = false, length = 100)
    private String groupName; // 그룹 이름

    @Column(nullable = false, unique = true, length = 6)
    private String groupCode; // 초대 코드 (유니크)

    @Column(nullable = false, length = 20)
    private String groupAuthCode; // 그룹 인증번호

    public void updateGroup(String groupName, String groupAuthCode) {
        this.groupName = groupName;
        this.groupAuthCode = groupAuthCode;
    }

}
