package com.watsonsoftware.document.model.entity;

import com.watsonsoftware.document.util.TimestampConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "document", schema = "document_details")
public class DocumentEntity {

    @Id
    @Column(name = "doc_id", nullable = false, updatable = false)
    private Long docId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "storage_location", nullable = false)
    private String storageLocation;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(name = "created", nullable = false)
    @Convert(converter = TimestampConverter.class)
    private LocalDateTime created;

    @Column(name = "updated")
    @Convert(converter = TimestampConverter.class)
    private LocalDateTime updated;
}
