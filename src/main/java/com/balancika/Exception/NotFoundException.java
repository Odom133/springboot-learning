package com.balancika.Exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Not Found");
    }
}
