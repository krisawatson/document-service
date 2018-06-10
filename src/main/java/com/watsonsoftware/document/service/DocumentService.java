package com.watsonsoftware.document.service;

import com.watsonsoftware.document.model.dto.Document;
import org.springframework.hateoas.Resources;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    Document getDocument(final Long docId);

    Resources<Document> getAllDocuments();

    Long storeDocument(final MultipartFile file);
}
