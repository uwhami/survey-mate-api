package com.surveymate.api.domain.member.entity;

import com.surveymate.api.common.converter.MemberRoleConverter;
import com.surveymate.api.common.converter.MemberStatusConverter;
import com.surveymate.api.common.enums.MemberRole;
import com.surveymate.api.common.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
@Table(name = "member")
public class Member implements UserDetails {

    @Id
    @Column(name = "mem_num", nullable = false)
    private String memNum;

    @Column(name = "user_id", length = 30, nullable = false, unique = true)
    private String userId;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    @Column(name = "user_email", length = 50, nullable = false)
    private String userEmail;

    @Column(name = "profile_image_uuid", length = 200)
    private String profileImageUuid;

    @Column(name = "join_date", length = 8, nullable = false)
    private String joinDate;

    @Column(name = "password_error", nullable = false)
    @Builder.Default
    private Integer passwordError = 0;

    @Convert(converter = MemberStatusConverter.class)
    @Column(name = "mem_status", length = 1, nullable = false)
    private MemberStatus memStatus;

    @Convert(converter = MemberRoleConverter.class)
    @Column(name = "mem_role", length = 1, nullable = false)
    private MemberRole memRole;


    @Column(name = "create_date", nullable = false, updatable = false)
    private java.time.LocalDateTime createDate;

    @Column(name = "update_date")
    private java.time.LocalDateTime updateDate;


    @PrePersist
    protected void onCreate() {
        this.createDate = java.time.LocalDateTime.now();
        this.updateDate = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = java.time.LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(memRole.getAuthority()));
    }

    public String getUserName(){
        return this.userName;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.memStatus == MemberStatus.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.memStatus == MemberStatus.ACTIVE;
    }

    @Override
    public boolean isEnabled() {
        return this.memStatus == MemberStatus.ACTIVE;
    }


}
