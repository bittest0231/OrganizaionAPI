package com.organization.demo.entity;

import lombok.Getter;

@Getter
public class MemberResult {

	private Long id;
	private String name;
	private String type;
	private boolean manager;
	
	public MemberResult(final MemberEntity entity) { 
		this.id = entity.getId();
		this.type= entity.getType();
		this.name = entity.getName();
		this.manager = entity.isManager();
	}
}
