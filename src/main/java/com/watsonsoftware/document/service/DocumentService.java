package com.watsonsoftware.document.service;

import com.watsonsoftware.document.model.dto.Document;
import org.springframework.hateoas.Resources;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface DocumentService {

    Document getDocument(final Integer docId, final String ownerId);

    Resources<Document> getAllDocuments(final String ownerId);

    Integer storeDocument(final String ownerId, final MultipartFile file);

    File getDocumentFile(final Integer docId, final String ownerId);
}
