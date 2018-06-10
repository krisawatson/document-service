package com.watsonsoftware.document.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ErrorResponse {

    @Builder.Default
    private String timestamp = LocalDateTime.now().toString();
    private HttpStatus status;
    private String message;
}
