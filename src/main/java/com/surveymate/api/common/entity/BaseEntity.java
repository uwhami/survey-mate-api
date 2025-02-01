package com.surveymate.api.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedBy
    @Column(name = "createId", updatable = false, nullable = false, length = 50)
    private String createId;

    @CreatedDate
    @Column(name = "create_date", updatable = false, nullable = false)
    private LocalDateTime create_date;

    @LastModifiedBy
    @Column(name = "update_id", nullable = false, length = 50)
    private String update_id;

    @LastModifiedDate
    @Column(name = "update_date", nullable = false)
    private LocalDateTime update_date;

    @PrePersist
    public void prePersist() {
        if (this.createId == null) {
            this.createId = "System"; // 기본값
        }
        if (this.create_date == null) {
            this.create_date = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (this.update_id == null) {
            this.update_id = "System"; // 기본값
        }
        this.update_date = LocalDateTime.now();
    }

}
