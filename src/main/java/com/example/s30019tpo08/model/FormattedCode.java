package com.example.s30019tpo08.model;


import java.io.Serializable;
import java.time.LocalDateTime;

public class FormattedCode implements Serializable {
    private String originalCode;
    private String formattedCode;
    private LocalDateTime expirationTime;

    public FormattedCode(String originalCode, String formattedCode, long durationInSeconds) {
        this.originalCode = originalCode;
        this.formattedCode = formattedCode;
        this.expirationTime = LocalDateTime.now().plusSeconds(durationInSeconds);
    }

    public String getOriginalCode() {
        return originalCode;
    }

    public String getFormattedCode() {
        return formattedCode;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
