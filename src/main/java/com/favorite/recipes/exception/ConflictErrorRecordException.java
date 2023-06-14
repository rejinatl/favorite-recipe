package com.favorite.recipes.exception;

public class ConflictErrorRecordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConflictErrorRecordException() {

        super();
    }

    public ConflictErrorRecordException(String message) {

        super(message);
    }

}
