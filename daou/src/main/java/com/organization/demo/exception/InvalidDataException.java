package com.organization.demo.exception;

// 커스텀 Exception 메시지 전달하기 위해 생성
public class InvalidDataException extends Exception{
	
	private String message;
	
    public InvalidDataException(String message) {
        super(message);
        this.message = message;
    }
    public InvalidDataException() {
    }

}
