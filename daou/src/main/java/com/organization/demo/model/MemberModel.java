package com.organization.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MemberModel {

	
	private String name;
	private String type = "Memeber";
	private boolean manager = false;
	
	private List<Long> team = new ArrayList<>();
	
	@Builder
	public MemberModel(String name, boolean manager, List<Long> team){
		this.name = name;
		this.manager = manager;
		this.team = team;
	}
	
}
