package kz.bitlab.middle02.fileserviceapplication.controller;

import kz.bitlab.middle02.fileserviceapplication.dto.FileAttachmentDto;
import kz.bitlab.middle02.fileserviceapplication.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        ByteArrayResource resource = fileService.downloadFile(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping
    public List<FileAttachmentDto> getAllFiles() {
        return fileService.getFileAttachments();
    }

    @GetMapping("/{id}")
    public FileAttachmentDto getFile(@PathVariable Long id) {
        return fileService.getFileAttachment(id);
    }

    @GetMapping("/filename/{fileName}")
    public FileAttachmentDto getFile(@PathVariable String fileName) {
        return fileService.getFileAttachmentByFileName(fileName);
    }

}
