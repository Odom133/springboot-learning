package com.balancika.Exception;

public class LocationExistedException extends RuntimeException {
    public LocationExistedException(String message) {
        super(String.format("Location already exists: " + message));
    }
}
