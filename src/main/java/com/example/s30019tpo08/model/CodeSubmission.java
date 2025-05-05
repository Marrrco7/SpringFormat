package com.example.s30019tpo08.model;


public class CodeSubmission {
    private String code;
    private String textId;
    private long durationInSeconds;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public long getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }
}
