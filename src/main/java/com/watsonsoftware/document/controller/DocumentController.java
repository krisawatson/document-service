package com.watsonsoftware.document.controller;

import com.watsonsoftware.document.model.dto.Document;
import com.watsonsoftware.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Resources<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping(value = "/{docId}")
    @ResponseStatus(HttpStatus.OK)
    public Document getDocuments(@PathVariable final Long docId) {
        return documentService.getDocument(docId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity storeDocument(@RequestParam("file") final MultipartFile file) {
        final Long docId = documentService.storeDocument(file);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{docId}")
                .buildAndExpand(docId).toUri();
        return ResponseEntity.created(location).build();
    }
}
