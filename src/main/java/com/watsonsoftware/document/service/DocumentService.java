package com.watsonsoftware.document.service;

import com.watsonsoftware.document.model.dto.Document;
import org.springframework.hateoas.Resources;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    Document getDocument(final Integer docId, final String ownerId);

    Resources<Document> getAllDocuments();

    Integer storeDocument(final String ownerId, final MultipartFile file);
}
