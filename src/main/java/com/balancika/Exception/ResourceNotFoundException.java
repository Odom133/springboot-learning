package com.balancika.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(String.format("Not found : "+ message));
    }
}

