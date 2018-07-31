package com.watsonsoftware.document.model.entity;

import com.watsonsoftware.document.util.TimestampConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class BaseEntity {
    @Column(name = "created", nullable = false)
    @Convert(converter = TimestampConverter.class)
    private LocalDateTime created;

    @Column(name = "updated")
    @Convert(converter = TimestampConverter.class)
    private LocalDateTime updated;

    @PrePersist
    void preInsert() {
        if (this.created == null)
            this.created = LocalDateTime.now();
    }
}
