package com.surveymate.api.domain.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "login_history")
public class LoginHistory {

    @Id
    @Column(name = "uuid", nullable = false, unique = true, columnDefinition = "BINARY(16)")
    @JdbcTypeCode(SqlTypes.BINARY)
    private byte[] uuid;

    @Column(name = "mem_num", nullable = false)
    private String memNum;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;


}
