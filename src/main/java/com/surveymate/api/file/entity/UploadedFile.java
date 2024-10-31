package com.surveymate.api.file.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UploadedFile {

    @Id
    private String fileId;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private boolean thumbnail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date uploadDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    private Date createDate;


    @PrePersist
    protected void onCreate() {
        this.createDate = new Date();
        this.uploadDate = new Date();
    }

}
