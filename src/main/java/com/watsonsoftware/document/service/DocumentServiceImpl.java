package com.watsonsoftware.document.service;

import com.watsonsoftware.document.controller.DocumentController;
import com.watsonsoftware.document.exception.FileException;
import com.watsonsoftware.document.model.dto.Document;
import com.watsonsoftware.document.model.entity.DocumentEntity;
import com.watsonsoftware.document.repository.DocumentRepository;
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
        String storage = System.getenv("STORAGE");
        File convFile = new File(storage + File.separator + multipart.getOriginalFilename());
        try {
            multipart.transferTo(convFile);
        } catch (IOException e) {
            throw new FileException("Failed to transfer file");
        }

        final Document document = Document.builder()
                .name(multipart.getOriginalFilename())
                .type(multipart.getContentType())
                .size(multipart.getSize())
                .storageLocation(convFile.getAbsolutePath())
                .ownerId(ownerId)
                .build();
        final DocumentEntity documentEntity = documentRepository.save(document.toDocumentEntity());
        return documentEntity.getDocId();
    }

    @Override
    public File getDocumentFile(final Integer docId, final String ownerId) {
        DocumentEntity documentEntity = documentRepository.findOneByDocIdAndOwnerId(docId, ownerId);
        return new File(documentEntity.getStorageLocation());
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
}
