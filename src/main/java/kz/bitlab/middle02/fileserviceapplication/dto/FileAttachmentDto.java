package kz.bitlab.middle02.fileserviceapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileAttachmentDto {
    private Long id;
    private String fileName;
    private String originalFileName;
    private String MIMEType;
    private Long size;
    private String uploadedAt;
}
