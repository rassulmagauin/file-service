package kz.bitlab.middle02.fileserviceapplication.reposiroty;

import kz.bitlab.middle02.fileserviceapplication.model.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {
    public FileAttachment findByFileName(String fileName);
}
