package com.organization.demo.entity;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class OnlyOrganizationResult {

	private long id;
	private String code;
	private String name;
	private String type;
	private List<OnlyOrganizationResult> children;
	
	public OnlyOrganizationResult(final OrganizationsEntity entity) {
		this.id = entity.getId();
		this.code = entity.getCode();
		this.type = entity.getType();
		this.name = entity.getName();
		this.children = entity.getChildren().stream().map(OnlyOrganizationResult::new).collect(Collectors.toList()); 
	}
}
