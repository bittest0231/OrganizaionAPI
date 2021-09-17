package com.organization.demo.entity;

import java.util.List;

import lombok.Getter;

@Getter
public class OrganizationWithParentResult {

	
	private Long id;
	private String name;
	private String type;
	private boolean manager;
//	private OrganizationsEntity team;
	private List<OrganizationsEntity> team;
	
	
	public OrganizationWithParentResult(final MemberEntity entity) { 
		this.name = entity.getName();
		this.id = entity.getId();
		this.type = entity.getType();
		this.manager = entity.isManager();
		this.team = entity.getTeam();
	}
}
