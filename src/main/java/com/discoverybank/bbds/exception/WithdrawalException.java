package com.discoverybank.bbds.exception;


public class NoAccountsToDisplayException extends RuntimeException {

    public NoAccountsToDisplayException() {
        super("No accounts to display");
    }

    public NoAccountsToDisplayException(String message) {
        super(message);
    }
}