package kz.bitlab.middle02.fileserviceapplication.mapper;

import kz.bitlab.middle02.fileserviceapplication.dto.FileAttachmentDto;
import kz.bitlab.middle02.fileserviceapplication.model.FileAttachment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileAttachmentMapper {
    FileAttachmentDto toDto(FileAttachment fileAttachment);
    FileAttachment toEntity(FileAttachmentDto fileAttachmentDto);

    List<FileAttachmentDto> toDtoList(List<FileAttachment> fileAttachments);

}
