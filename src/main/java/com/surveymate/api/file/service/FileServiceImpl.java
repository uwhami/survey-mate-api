package com.surveymate.api.file.service;

import com.surveymate.api.common.enums.FilePath;
import com.surveymate.api.config.FileUploadProperties;
import com.surveymate.api.file.entity.UploadedFile;
import com.surveymate.api.file.exception.FileNameTooLongException;
import com.surveymate.api.file.exception.ThumbnailCreationException;
import com.surveymate.api.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileUploadProperties fileUploadProperties;

    public String getUploadPath(FilePath filePath){
        String uploadPath = null;
        switch (filePath){
            case MEMBER_PROFILE :
                uploadPath = fileUploadProperties.getMemberProfile();
                break;
            case TEST:
                uploadPath = "test";
            default:
                throw new IllegalArgumentException("Invalid file path type");
        }
        return uploadPath;
    }

    @Transactional
    public UploadedFile uploadFile(MultipartFile multipartFile, FilePath filePath) throws Exception {
        return uploadFile(multipartFile, false, filePath);
    }

    @Transactional
    public List<UploadedFile> uploadFiles(List<MultipartFile> multipartFiles, FilePath filePath) throws Exception {
        List<UploadedFile> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            files.add(uploadFile(multipartFile, filePath));
        }
        return files;
    }

    @Transactional
    public UploadedFile uploadFileAndCreateThumbnail(MultipartFile multipartFile, FilePath filePath) throws Exception{
        return uploadFile(multipartFile, true, filePath);
    }

    @Transactional
    public UploadedFile uploadFile(MultipartFile multipartFile, boolean thumbnail, FilePath filePath) throws Exception {
        String fileUuid = UUID.randomUUID().toString();
        String filePathString = getUploadPath(filePath);
        Path directory = Paths.get(filePathString);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename.length() > 100){
            throw new FileNameTooLongException("파일 이름이 너무 깁니다.");
        }
        String saveName = fileUuid + "_" + originalFilename;

        Path savedFilePath = directory.resolve(saveName);
        Files.copy(multipartFile.getInputStream(), savedFilePath);

        if(thumbnail) {
            try {
                String contentType = multipartFile.getContentType();
                //이미지 파일이라면
                if (contentType != null && contentType.startsWith("image")) {
                    String thumbnailPathString = filePathString + "/thumbnail";
                    Path thumbnailDirectory = Paths.get(thumbnailPathString);
                    if (!Files.exists(thumbnailDirectory)) {
                        Files.createDirectories(thumbnailDirectory);
                    }

                    Path thumbnailPath = Paths.get(thumbnailPathString, "s_" + saveName);
                    Thumbnails.of(savedFilePath.toFile()).size(200, 200).toFile(thumbnailPath.toFile());
                }else{
                    throw new ThumbnailCreationException("image 파일만 등록 가능 합니다.");
                }
            } catch (IOException e) {
                throw new ThumbnailCreationException("이미지 파일 썸네일 생성 중 에러 발생.");
            }
        }

        // 파일 메타데이터 저장
        UploadedFile file = UploadedFile.builder()
                                        .fileId(saveName)
                                        .fileName(originalFilename)
                                        .filePath(savedFilePath.toString())
                                        .thumbnail(thumbnail)
                                        .build();
        fileRepository.save(file);

        return file;
    }

    public Resource downloadFile(String fileId) throws Exception {
        UploadedFile fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with ID: " + fileId));
        Path filePath = Paths.get(fileEntity.getFilePath());
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException("Could not read the file: " + fileEntity.getFilePath());
        }
    }
}
