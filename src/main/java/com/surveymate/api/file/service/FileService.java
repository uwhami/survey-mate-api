package com.surveymate.api.file.service;

import com.surveymate.api.common.enums.FilePath;
import com.surveymate.api.file.entity.UploadedFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    UploadedFile getDefaultFilePath() throws Exception;

    UploadedFile uploadFile(MultipartFile multipartFile, FilePath filePath) throws Exception;

    List<UploadedFile> uploadFiles(List<MultipartFile> multipartFiles, FilePath filePath) throws Exception;

    UploadedFile uploadFileAndCreateThumbnail(MultipartFile multipartFile, FilePath filePath) throws Exception;

    UploadedFile uploadFile(MultipartFile multipartFile, boolean thumbnail, FilePath filePath) throws Exception;

    UploadedFile deleteAndSaveFile(String fileId, MultipartFile newFile, FilePath filePath) throws Exception;

    void deleteFile(String fildId) throws Exception;

}
