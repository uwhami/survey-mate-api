package com.surveymate.api.file.service;

import com.surveymate.api.common.enums.FilePath;
import com.surveymate.api.file.config.FileUploadProperties;
import com.surveymate.api.file.entity.UploadedFile;
import com.surveymate.api.file.exception.FileNameTooLongException;
import com.surveymate.api.file.exception.ThumbnailCreationException;
import com.surveymate.api.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
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

@Log4j2
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

    @Override
    public UploadedFile getDefaultFilePath() throws Exception {
        // 기본 파일 경로 설정
        String defaultFileName = "default.png";
        String defaultFilePath = getUploadPath(FilePath.MEMBER_PROFILE) + "/" + defaultFileName;

        // 파일 경로 확인
        Path defaultFile = Paths.get(defaultFilePath);
        if (!Files.exists(defaultFile)) {
            throw new FileNotFoundException("기본 파일이 존재하지 않습니다: " + defaultFilePath);
        }

        // UploadedFile 객체 생성
        return UploadedFile.builder()
                .fileId(defaultFileName)
                .fileName(defaultFileName)
                .filePath(defaultFile.toString())
                .thumbnail(false)
                .build();
    }

    @Override
    @Transactional
    public UploadedFile uploadFile(MultipartFile multipartFile, FilePath filePath) throws Exception {
        boolean thumbnail = filePath.equals(FilePath.MEMBER_PROFILE);
        return uploadFile(multipartFile, thumbnail, filePath);
    }

    @Override
    @Transactional
    public List<UploadedFile> uploadFiles(List<MultipartFile> multipartFiles, FilePath filePath) throws Exception {
        List<UploadedFile> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            files.add(uploadFile(multipartFile, filePath));
        }
        return files;
    }

    @Override
    @Transactional
    public UploadedFile uploadFileAndCreateThumbnail(MultipartFile multipartFile, FilePath filePath) throws Exception{
        return uploadFile(multipartFile, true, filePath);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadedFile uploadFile(MultipartFile multipartFile, boolean thumbnail, FilePath filePath) throws Exception {
        String fileUuid = UUID.randomUUID().toString();
        String filePathString = getUploadPath(filePath);
        Path directory = createDirectoryIfNotExists(filePathString);

        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename.length() > 100){
            throw new FileNameTooLongException("파일 이름이 너무 깁니다.");
        }
        String saveName = fileUuid + "_" + originalFilename;

        Path savedFilePath = directory.resolve(saveName);
        try {
            Files.copy(multipartFile.getInputStream(), savedFilePath);

            if(thumbnail) {
                validateImageFile(multipartFile);

                String thumbnailPathString = filePathString + "/thumbnail";
                Path thumbnailDirectory = createDirectoryIfNotExists(thumbnailPathString);
                Path thumbnailPath = thumbnailDirectory.resolve("s_" + saveName);
                Thumbnails.of(savedFilePath.toFile()).size(200, 200).toFile(thumbnailPath.toFile());
            }
        } catch (ThumbnailCreationException | IOException e){
            handleThumbnailCreationError(savedFilePath, e);
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

    /* 파일 폴더 설정 */
    private Path createDirectoryIfNotExists(String directoryPath) throws IOException {
        Path directory = Paths.get(directoryPath);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        return directory;
    }

    /* 이미지 파일 검증 */
    private void validateImageFile(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            throw new ThumbnailCreationException("image 파일만 등록 가능 합니다.");
        }
    }

    /* 에러 발생 시 파일 삭제 및 예외 처리 */
    private void handleThumbnailCreationError(Path savedFilePath, Exception e) {
        try {
            Files.deleteIfExists(savedFilePath);
        } catch (IOException ioException) {
            log.error("파일 삭제 중 에러 발생: {}", savedFilePath, ioException);
        }
        throw new ThumbnailCreationException(e.getMessage(), e);
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

    @Override
    @Transactional
    public UploadedFile deleteAndSaveFile(String fileId, MultipartFile newFile, FilePath filePath) throws Exception {
        // 1. 기존 파일 삭제
        if (fileId != null && !fileId.trim().isEmpty()) {
            deleteFile(fileId);
        }

        // 2. 새 파일 저장
        return uploadFile(newFile, filePath);
    }



    @Override
    @Transactional
    public void deleteFile(String fileId) throws Exception {

        UploadedFile fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with ID: " + fileId));

        Path filePath = Paths.get(fileEntity.getFilePath());

        Path directoryPath = filePath.getParent();
        Path thumbnailDirectoryPath = directoryPath.resolve("thumbnail");
        Path thumbNailPath = thumbnailDirectoryPath.resolve("s_" + fileEntity.getFileId());
        try {
            if(Files.deleteIfExists(filePath)){
                log.info("파일 삭제 성공: {}", filePath);
            }

            if(Files.deleteIfExists(thumbNailPath)){
                log.info("Thumbnail 파일 삭제 성공: {}", filePath);
            }

        } catch (IOException e) {
            log.error("파일 삭제 실패: {}", filePath, e);
            throw new IOException("파일 삭제 중 오류가 발생했습니다: " + filePath);
        }

        fileRepository.delete(fileEntity);
        log.info("파일 메타데이터 삭제 성공: {}", fileId);
    }

    @Override
    public String getFilePath(String fileId) throws FileNotFoundException {
        UploadedFile fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with ID: " + fileId));
        return fileEntity.getFilePath();
    }
}
