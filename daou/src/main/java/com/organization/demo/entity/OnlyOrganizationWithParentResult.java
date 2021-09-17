package com.organization.demo.entity;

import lombok.Getter;

@Getter
public class OnlyOrganizationWithParentResult {

	private Long id;
	private String code;
	private String name;
	private String type;
	private OrganizationsEntity parent;
	
	public OnlyOrganizationWithParentResult(final OrganizationsEntity entity) {
		this.id = entity.getId();
		this.code = entity.getCode();
		this.type = entity.getType();
		this.name = entity.getName();
		this.parent = entity.getParent();
	}
}
