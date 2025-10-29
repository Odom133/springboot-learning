package com.balancika.Exception;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {
    private final String field;
    private final Object value;

    public ResourceAlreadyExistsException(String field, Object value) {
        super(String.format("%s with value '%s' already exists", field, value));
        this.field = field;
        this.value = value;
    }
}

