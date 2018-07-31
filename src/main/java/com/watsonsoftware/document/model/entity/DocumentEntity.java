package com.watsonsoftware.document.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "document", schema = "document_details")
public class DocumentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "doc_id", unique = true, nullable = false, updatable = false)
    private Integer docId;

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
}
