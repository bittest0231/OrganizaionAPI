package com.organization.demo.entity;

import lombok.Getter;

@Getter
public class OrganizationWithParentResult {

	
	private long id;
	private String name;
	private String type;
	private boolean manager;
	private OrganizationsEntity team;
	
	
	public OrganizationWithParentResult(final MemberEntity entity) { 
		this.name = entity.getName();
		this.id = entity.getId();
		this.type = entity.getType();
		this.manager = entity.isManager();
		this.team = entity.getTeam();
	}
}