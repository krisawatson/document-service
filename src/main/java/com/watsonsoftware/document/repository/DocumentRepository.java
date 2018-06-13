package com.watsonsoftware.document.repository;

import com.watsonsoftware.document.model.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends
        JpaRepository<DocumentEntity, Integer> {

    DocumentEntity findOneByDocIdAndOwnerId(Integer docId, String ownerId);

    List<DocumentEntity> findAllByOwnerId(String ownerId);

}
