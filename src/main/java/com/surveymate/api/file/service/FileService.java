package com.surveymate.api.file.service;

import com.surveymate.api.common.enums.FilePath;
import com.surveymate.api.file.entity.UploadedFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    public UploadedFile uploadFile(MultipartFile multipartFile, FilePath filePath) throws Exception;

    public List<UploadedFile> uploadFiles(List<MultipartFile> multipartFiles, FilePath filePath) throws Exception;

    public UploadedFile uploadFileAndCreateThumbnail(MultipartFile multipartFile, FilePath filePath) throws Exception;

    public UploadedFile uploadFile(MultipartFile multipartFile, boolean thumbnail, FilePath filePath) throws Exception;

}
