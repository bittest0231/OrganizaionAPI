package com.organization.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 부서원 관련 파라미터 받아줄 모델

@ToString
@Getter
@Setter
public class MemberModel {

	
	private String name;
	private String type = "Memeber";
	private boolean manager = false;
	
	private List<Long> team = new ArrayList<>();
	
	@Builder
	@JsonCreator
	public MemberModel(
			@JsonProperty("name")String name,
			@JsonProperty("manager")boolean manager,
			@JsonProperty("team")List<Long> team){
		this.name = name;
		this.manager = manager;
		this.team = team;
	}
	
}
