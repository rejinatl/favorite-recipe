package com.favorite.recipes.exception;

public class DuplicateRecordErrorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateRecordErrorException() {

        super();
    }

    public DuplicateRecordErrorException(String message) {

        super(message);
    }

}
