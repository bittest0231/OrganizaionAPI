package com.organization.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorBody500 {
	
	private String code = "INTERNAL_SERVER_ERROR";
	private String message = "내부 서버 오류가 발생했습니다.";
	
	@Builder
	public ErrorBody500() {
		
//		this.message = message; 
	}
}
