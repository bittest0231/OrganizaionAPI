package com.organization.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptModel {

	
	private String code;
	private String name;
	private String type;
	private long parentId;
	
}
