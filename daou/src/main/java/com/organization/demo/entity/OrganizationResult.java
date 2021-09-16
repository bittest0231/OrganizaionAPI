package com.organization.demo.entity;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class OrganizationResult {

	private String code;
	private String name;
	private String type;
	private List<MemberResult> member;
	private List<OrganizationResult> children;
	
	public OrganizationResult(final OrganizationsEntity entity) { 
		this.code = entity.getCode();
		this.type = entity.getType();
		this.name = entity.getName();
		this.member = entity.getMembers();
		this.children = entity.getChildren().stream().map(OrganizationResult::new).collect(Collectors.toList()); 
	}
}
