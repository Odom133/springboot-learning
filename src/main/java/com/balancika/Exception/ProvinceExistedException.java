package com.balancika.Exception;

public class ProvinceExistedException extends RuntimeException {

    public ProvinceExistedException(String message) {
        super(String.format("The value already exist: " + message));
    }
}
