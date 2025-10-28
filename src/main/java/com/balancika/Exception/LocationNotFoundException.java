package com.balancika.Exception;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(Long id) {
        super("Location not found with ID: " + id);
    }
}

