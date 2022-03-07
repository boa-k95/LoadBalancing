package com.CHAT01.exceptions;

public class InvalidTaskResponseException extends RuntimeException {
    public InvalidTaskResponseException(String message) {
        super(message);
    }
}
