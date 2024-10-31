package com.surveymate.api.file.service;

import com.surveymate.api.common.enums.FilePath;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    public String uploadFile(MultipartFile multipartFile, FilePath filePath) throws Exception;

    public List<String> uploadFiles(List<MultipartFile> multipartFiles, FilePath filePath) throws Exception;

    public String uploadFileAndCreateThumbnail(MultipartFile multipartFile, FilePath filePath) throws Exception;

    public String uploadFile(MultipartFile multipartFile, boolean thumbnail, FilePath filePath) throws Exception;

}
