package com.favorite.recipes.exception;

public class ErrorSavingRecordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ErrorSavingRecordException() {

        super();
    }

    public ErrorSavingRecordException(String message) {

        super(message);
    }

}
