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
    @Column(name = "create_id", updatable = false, nullable = false, length = 50)
    private String createId;

    @CreatedDate
    @Column(name = "create_date", updatable = false, nullable = false)
    private LocalDateTime createDate;

    @LastModifiedBy
    @Column(name = "update_id", nullable = false, length = 50)
    private String updateId;

    @LastModifiedDate
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        if (this.createId == null) {
            this.createId = "System"; // 기본값
        }
        if (this.createDate == null) {
            this.createDate = LocalDateTime.now();
        }

        if (this.updateId == null) {
            this.updateId = "System"; // 기본값
        }
        this.updateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        if (this.updateId == null) {
            this.updateId = "System"; // 기본값
        }
        this.updateDate = LocalDateTime.now();
    }

}
