package com.epam.victor.service.exception;

public class IdAlreadyExistException extends RuntimeException{

    public IdAlreadyExistException() {
    }

    public IdAlreadyExistException(String message) {
        super(message);
    }
}
