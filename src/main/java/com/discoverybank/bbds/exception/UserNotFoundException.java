package com.discoverybank.bbds.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("No user found" );
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
