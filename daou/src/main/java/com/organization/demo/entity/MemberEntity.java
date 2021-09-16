package com.organization.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Entity
public class MemberEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private boolean manager;
	
	@ManyToOne
	@JoinColumn(name="TEAM_CODE")
	private OrganizationsEntity team;
	
	@JsonIgnore
    public OrganizationsEntity getTeam() {
        return team ;
    }
	
	@Builder
	public MemberEntity(String name, OrganizationsEntity team, boolean manager) {
		
		this.name = name;
		this.team = team;
		this.manager = manager;
	}
	
}
