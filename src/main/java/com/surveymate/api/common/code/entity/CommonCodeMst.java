package com.surveymate.api.common.code.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "common_code_mst")
public class CommonCodeMst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commoncode_id", nullable = false, unique = true)
    private Long commonCodeId;

    @Column(name = "code_group", nullable = false, unique = true)
    private String codeGroup;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "codeMaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommonCodeDtl> details = new ArrayList<>();

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
