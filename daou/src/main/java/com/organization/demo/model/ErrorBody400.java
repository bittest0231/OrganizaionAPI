package com.organization.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorBody400 {
	
	private String code = "BAD_REQUEST";
	private String message;
	
	@Builder
	public ErrorBody400(String message) {
		
		this.message = message; 
	}
}
