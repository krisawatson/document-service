package com.watsonsoftware.document.service;

import com.watsonsoftware.document.controller.DocumentController;
import com.watsonsoftware.document.exception.FileException;
import com.watsonsoftware.document.exception.NotFoundException;
import com.watsonsoftware.document.model.dto.Document;
import com.watsonsoftware.document.model.entity.DocumentEntity;
import com.watsonsoftware.document.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;


    @Override
    public Document getDocument(final Integer docId, final String ownerId) {
        DocumentEntity documentEntity = documentRepository.findOneByDocIdAndOwnerId(docId, ownerId);
        Document document = Document.builder().build();
        document.fromDocumentEntity(documentEntity);
        return document;
    }

    @Override
    public Resources<Document> getAllDocuments(final String ownerId) {
        return extractDocumentsFromEntities(documentRepository.findAllByOwnerId(ownerId));
    }

    @Override
    public Integer storeDocument(final String ownerId, final MultipartFile multipart) {
        File destination = buildDestinationFile(ownerId, multipart.getOriginalFilename());
        try {
            log.info("Copying file " + destination.getName());
            destination.getParentFile().mkdirs();
            multipart.transferTo(destination);
        } catch (IOException e) {
            log.error("Failed to copy file " + e.getLocalizedMessage());
            throw new FileException("Failed to transfer file");
        }

        final Document document = Document.builder()
                .name(multipart.getOriginalFilename())
                .type(multipart.getContentType())
                .size(multipart.getSize())
                .storageLocation(destination.getAbsolutePath())
                .ownerId(ownerId)
                .build();
        final DocumentEntity documentEntity = documentRepository.save(document.toDocumentEntity());
        return documentEntity.getDocId();
    }

    @Override
    public File getDocumentFile(final Integer docId, final String ownerId) {
        try {
            DocumentEntity documentEntity = documentRepository.findOneByDocIdAndOwnerId(docId, ownerId);
            return new File(documentEntity.getStorageLocation());
        } catch (NullPointerException e) {
            throw new NotFoundException("File does not exists");
        }
    }

    private Document extractDocumentFromEntity(final DocumentEntity documentEntity) {
        Document document = Document.builder()
                .docId(documentEntity.getDocId())
                .name(documentEntity.getName())
                .size(documentEntity.getSize())
                .type(documentEntity.getType())
                .build();
        document.add(linkTo(
                methodOn(DocumentController.class)
                        .getDocument(documentEntity.getDocId())).withSelfRel());
        return document;
    }

    private Resources<Document> extractDocumentsFromEntities(final List<DocumentEntity> documentEntityList) {
        List<Document> documents = documentEntityList.stream().map(entity ->
                extractDocumentFromEntity(entity))
                .collect(Collectors.toList());

        return new Resources<Document>(documents, linkTo(
                methodOn(DocumentController.class)
                        .getAllDocuments()).withSelfRel());
    }

    private File buildDestinationFile(final String ownerId, final String filename) {
        return new File(System.getenv("STORAGE") + File.separator
                + ownerId + File.separator + filename);
    }
}
