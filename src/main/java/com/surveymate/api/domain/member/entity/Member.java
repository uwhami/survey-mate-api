package com.surveymate.api.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "mem_num", nullable = false)
    private String memNum;

    @Column(name = "user_id", length = 20, nullable = false, unique = true)
    private String userId;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    @Column(name = "profile_image", length = 50)
    private String profileImage;

    @Column(name = "join_date", length = 8, nullable = false)
    private String joinDate;

    @Column(name = "password_error", nullable = false)
    @Builder.Default
    private Integer passwordError = 0;

    @Column(name = "mem_status", length = 1, nullable = false)
    private String memStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @PrePersist
    protected void onCreate() {
        this.createDate = new Date();
    }


}
