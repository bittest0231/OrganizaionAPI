package com.organization.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	@JsonCreator
	@Builder
	public DeptModel(
			@JsonProperty("code")String code, @JsonProperty("name")String name, 
			@JsonProperty("type")String type, @JsonProperty("parentId")Long parentId) {
		this.code = code;
		this.name = name;
		this.type = type;
		this.parentId = parentId; 
	} 
	
}
