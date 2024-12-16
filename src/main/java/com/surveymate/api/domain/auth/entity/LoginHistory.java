package com.surveymate.api.domain.auth.entity;

import com.surveymate.api.common.converter.UUIDBinaryConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "login_history")
public class LoginHistory {

    @Id
    @Column(name = "uuid", columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "mem_num", nullable = false)
    private String memNum;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @CreationTimestamp
    @Column(name = "last_access_time", nullable = false)
    private LocalDateTime lastAccessTime;

}
