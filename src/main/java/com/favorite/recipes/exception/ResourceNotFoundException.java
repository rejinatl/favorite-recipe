package com.favorite.recipes.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {

        super();
    }

    public ResourceNotFoundException(String message) {

        super(message);
    }

}
