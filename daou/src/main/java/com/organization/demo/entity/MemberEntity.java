package com.organization.demo.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MemberEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String type = "Member";
	
	private boolean manager;
	
//	@ManyToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="TEAM_CODE")
//	private OrganizationsEntity team;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "MEMBER_TEAM"
			,joinColumns = @JoinColumn(name="MEMBER_ENTITY_ID")
			,inverseJoinColumns = @JoinColumn(name = "TEMA_ID")
	)
	private List<OrganizationsEntity> team;
	
	@JsonIgnore
    public List<OrganizationsEntity> getTeam() {
        return team ;
    }
	
	@Builder
	public MemberEntity(String name, List<OrganizationsEntity> team, boolean manager) {
		
		this.name = name;
		this.team = team;
		this.manager = manager;
	}
	
}
