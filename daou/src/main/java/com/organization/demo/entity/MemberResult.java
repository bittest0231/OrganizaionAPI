package com.organization.demo.entity;

import lombok.Getter;

@Getter
public class MemberResult {

	private long id;
	private String name;
	private boolean manager;
	
	public MemberResult(final MemberEntity entity) { 
		this.id = entity.getId();
		this.name = entity.getName();
		this.manager = entity.isManager();
	}
}
