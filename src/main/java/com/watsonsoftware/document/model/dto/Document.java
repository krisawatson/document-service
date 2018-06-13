package com.watsonsoftware.document.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.watsonsoftware.document.model.entity.DocumentEntity;
import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonInclude(Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Document extends ResourceSupport {

    private Integer docId;

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private String storageLocation;

    @NotNull
    private Long size;

    @NotNull
    private String ownerId;

    public DocumentEntity toDocumentEntity() {
        return DocumentEntity.builder()
                .docId(this.docId)
                .name(this.name)
                .type(this.type)
                .storageLocation(this.storageLocation)
                .size(this.size)
                .created(LocalDateTime.now())
                .build();
    }

    public void fromDocumentEntity(final DocumentEntity documentEntity) {
        this.name = documentEntity.getName();
        this.type = documentEntity.getType();
        this.storageLocation = documentEntity.getStorageLocation();
        this.size = documentEntity.getSize();
        this.ownerId = documentEntity.getOwnerId();
    }
}
