package com.surveymate.api.common.code.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "common_code_dtl")
public class CommonCodeDtl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_group_id", nullable = false)
    private CommonCodeMst codeMaster;

    @Column(name = "code_value", nullable = false)
    private String codeValue; // 코드의 실제 값 (예: ROLE_ADMIN, SURVEY_TYPE_TEXT)

    @Column(name = "code_name", nullable = false)
    private String codeName; // 코드의 사용자 친화적 이름 (예: Administrator, Text Question)

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "active", nullable = false)
    private Boolean active;

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
