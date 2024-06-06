package kz.bitlab.middle02.fileserviceapplication.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import kz.bitlab.middle02.fileserviceapplication.dto.FileAttachmentDto;
import kz.bitlab.middle02.fileserviceapplication.mapper.FileAttachmentMapper;
import kz.bitlab.middle02.fileserviceapplication.model.FileAttachment;
import kz.bitlab.middle02.fileserviceapplication.reposiroty.FileAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;
    private final FileAttachmentRepository fileAttachmentRepository;
    private final FileAttachmentMapper fileAttachmentMapper;

    @Value("${minio.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setOriginalFileName(file.getOriginalFilename());
        fileAttachment.setSize(file.getSize());
        fileAttachment.setMIMEType(file.getContentType());
        fileAttachment = fileAttachmentRepository.save(fileAttachment);

        String fileName = convertToSha1("Random2332"+fileAttachment.getId());

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            fileAttachment.setFileName(fileName);
            fileAttachmentRepository.save(fileAttachment);
            return fileAttachment.getFileName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error uploading file";
    }

    public ByteArrayResource downloadFile(String fileName) {
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build();
            InputStream stream = minioClient.getObject(args);
            byte[] content = IOUtils.toByteArray(stream);
            stream.close();
            return new ByteArrayResource(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String convertToSha1(String text) {
        return DigestUtils.sha1Hex(text);
    }

    public FileAttachmentDto getFileAttachment(Long id) {
        return fileAttachmentMapper.toDto(fileAttachmentRepository.findById(id).orElse(null));
    }

    public List<FileAttachmentDto> getFileAttachments() {
        return fileAttachmentMapper.toDtoList(fileAttachmentRepository.findAll());
    }

    public FileAttachmentDto getFileAttachmentByFileName(String fileName) {
        return fileAttachmentMapper.toDto(fileAttachmentRepository.findByFileName(fileName));
    }


}
