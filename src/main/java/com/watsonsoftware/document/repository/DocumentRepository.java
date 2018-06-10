package com.watsonsoftware.document.repository;

import com.watsonsoftware.document.model.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends
        JpaRepository<DocumentEntity, Integer> {

    DocumentEntity findOneByDocIdAndOwnerId(Long docId, String ownerId);

}
