package com.watsonsoftware.document.service;

import com.watsonsoftware.document.model.dto.Document;
import com.watsonsoftware.document.model.entity.DocumentEntity;
import com.watsonsoftware.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public Resources<Document> getAllDocuments() {
        return null;
    }

    @Override
    public Integer storeDocument(final String ownerId, final MultipartFile file) {
        // TODO Call the service to store the doc

        // TODO create record entry in the DB
        final Document document = Document.builder()
                .name(file.getName())
                .type(file.getContentType())
                .size(file.getSize())
                .storageLocation(file.getName())
                .ownerId(ownerId)
                .build();
        final DocumentEntity documentEntity = documentRepository.save(document.toDocumentEntity());
        return documentEntity.getDocId();
    }
}
