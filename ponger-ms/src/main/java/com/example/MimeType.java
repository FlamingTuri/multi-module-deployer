package com.example;

public enum MimeType {
    APPLICATION_JSON("application/json"), TEXT_PLAIN("text/plain");

    private final String value;

    private MimeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
