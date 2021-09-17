package com.organization.demo.exception;

public class InvalidDataException extends Exception{
	
	private String message;
	
    public InvalidDataException(String message) {
        super(message);
        this.message = message;
    }
    public InvalidDataException() {
    }

}
