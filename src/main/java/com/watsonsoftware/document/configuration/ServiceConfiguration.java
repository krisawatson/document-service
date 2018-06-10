package com.watsonsoftware.document.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("service")
public class ServiceConfiguration {

    @NotNull
    private String corsAllowedOrigin = "*";

    @NotNull
    private String publicKey = "s1gn1ngK3y";
}
