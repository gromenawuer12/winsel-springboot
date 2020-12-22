package com.albertocn.springboot;

public class NoDateFoundException extends Exception {
    public NoDateFoundException(String errorMessage) {
        super(errorMessage);
    }
}