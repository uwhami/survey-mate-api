package com.surveymate.api.file.repository;

import com.surveymate.api.file.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<UploadedFile, String> {
}
