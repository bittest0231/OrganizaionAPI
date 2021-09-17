package com.organization.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptModel {

	
	private String code;
	private String name;
	private String type;
	private Long parentId;
	
	@Builder
	public DeptModel(String code, String name, String type, Long parentId) {
		this.code = code;
		this.name = name;
		this.type = type;
		this.parentId = parentId; 
	} 
	
}
