package com.watsonsoftware.document.controller;

import com.watsonsoftware.document.exception.FileException;
import com.watsonsoftware.document.model.dto.Document;
import com.watsonsoftware.document.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

@RestController
@Slf4j
@RequestMapping(value = "/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Resources<Document> getAllDocuments() {
        return documentService.getAllDocuments(getOwnerId());
    }

    @GetMapping(value = "/{docId}")
    @ResponseStatus(HttpStatus.OK)
    public Document getDocument(@PathVariable final Integer docId) {
        return documentService.getDocument(docId, getOwnerId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity storeDocument(@RequestParam("file") final MultipartFile file) {
        final Integer docId = documentService.storeDocument(getOwnerId(), file);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{docId}")
                .buildAndExpand(docId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/{docId}/download")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity downloadDocument(@PathVariable final Integer docId) {
        File file = documentService.getDocumentFile(docId, getOwnerId());
        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (IOException e) {
            throw new FileException("Failed to parse file");
        }

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private String getOwnerId() {
        log.info(SecurityContextHolder.getContext().getAuthentication().getName());
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
