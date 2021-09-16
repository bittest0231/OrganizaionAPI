package com.organization.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorBody500 {
	
	private String code = "INTERNAL_SERVER_ERROR";
	private String message;
	
	@Builder
	public ErrorBody500(String message) {
		
		this.message = message; 
	}
}
