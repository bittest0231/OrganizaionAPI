package com.organization.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class OrganizationsEntity {
	
	

	@Id
	@Column(name = "CODE_ID")
	private String code;
	/*
	* Company: 회사
	* Division: 본부
	* Department: 부, 팀
	* Member: 부서원
	규모: Company > Division > Department
	*/
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "NAME")
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY , cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "PAR_CODE_ID")
	private OrganizationsEntity parent;
	
	
	@OneToMany(targetEntity = MemberEntity.class, mappedBy= "team", fetch=FetchType.EAGER)
	private List<MemberResult> members ;
	
	
	@OneToMany(fetch=FetchType.LAZY , mappedBy= "parent")
	private List<OrganizationsEntity> children = new ArrayList<>();

	@JsonIgnore
    public List<OrganizationsEntity> getChildren() {
        return children ;
    }
	@JsonIgnore
	public List<MemberResult> getMembers() {
		return members ;
	}
	
	@Builder
	public OrganizationsEntity(String type, String name, String code, OrganizationsEntity parent) {
		this.type = type;
		this.name = name;
		this.code = code;
		this.parent = parent;
	}
	
}
