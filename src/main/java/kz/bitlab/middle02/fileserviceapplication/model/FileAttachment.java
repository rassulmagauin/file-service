package kz.bitlab.middle02.fileserviceapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String originalFileName;
    private String MIMEType;
    private Long size;
    private LocalDateTime uploadedAt;

    @PrePersist
    public void setTime() {
        uploadedAt = LocalDateTime.now();
    }
}
