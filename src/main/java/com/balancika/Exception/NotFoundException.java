package com.balancika.Exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Not Found ID: "+id);
    }
    public NotFoundException(String message) {
        super(message);
    }

    // For option 2
    public NotFoundException() {
        super("Not Found");
    }
}
