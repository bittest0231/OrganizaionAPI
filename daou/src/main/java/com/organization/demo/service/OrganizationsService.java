package com.organization.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.organization.demo.entity.MemberEntity;
import com.organization.demo.entity.OnlyOrganizationResult;
import com.organization.demo.entity.OnlyOrganizationWithParentResult;
import com.organization.demo.entity.OrganizationResult;
import com.organization.demo.entity.OrganizationWithParentResult;
import com.organization.demo.entity.OrganizationsEntity;
import com.organization.demo.model.DeptModel;
import com.organization.demo.repository.MemberRepository;
import com.organization.demo.repository.OrganizationsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationsService {

	private final OrganizationsRepository OrgRepo;
	
	private final MemberRepository MemRepo;
	
	// 전체 부서, 멤버 모두 검색
	public OrganizationResult getOrganizations(){
		
		final List<OrganizationsEntity> list = OrgRepo.findByCode("100");
		
		return list.stream().map(OrganizationResult::new).collect(Collectors.toList()).get(0);
	}
	
	// 전체 부서만 검색
	public OnlyOrganizationResult getOnlyOrganizations(){
		
		final List<OrganizationsEntity> list = OrgRepo.findByCode("100");
		
		return list.stream().map(OnlyOrganizationResult::new).collect(Collectors.toList()).get(0);
	}
		
	// 특정 부서 기준으로 부서, 부서원 검색 
	public OrganizationResult getOrganizations(String deptCode){
		
		final List<OrganizationsEntity> list = OrgRepo.findByCode(deptCode);
		
		return list.stream().map(OrganizationResult::new).collect(Collectors.toList()).get(0);
	}
	
	// 특정 부서 기준으로 부서만 검색
	public OnlyOrganizationResult getOnlyOrganizations(String deptCode){
		
		final List<OrganizationsEntity> list = OrgRepo.findByCode(deptCode);
		
		return list.stream().map(OnlyOrganizationResult::new).collect(Collectors.toList()).get(0);
	}
	
	/** 키워드 관련 검색**/
	
	// 특정 키워드로 검색 부서만
	public List<OnlyOrganizationWithParentResult> getOnlyOrgFromKeyword(String searchType, String keyword){
		
		final List<OrganizationsEntity> list = OrgRepo.findByNameContains(keyword);
		
		return list.stream().map(OnlyOrganizationWithParentResult::new).collect(Collectors.toList());
	}
	
	// 특정 키워드로 부서원 검색 부서포함
	public List<OrganizationWithParentResult> getOrgFromKeyword(String searchType, String keyword){
		
		final List<MemberEntity> list = MemRepo.findByNameContains(keyword);
		
		return list.stream().map(OrganizationWithParentResult::new).collect(Collectors.toList());
	}
	
	// 부서 추가
	public OrganizationsEntity insertDept(DeptModel model){
		OrganizationsEntity saver = null;
		
		final OrganizationsEntity parentEntity = OrgRepo.findById(model.getParentId());
		
		if(parentEntity == null) {
			return null;
		}
		saver = OrgRepo.save(OrganizationsEntity.builder().code(model.getCode()).name(model.getName()).type(model.getType()).parent(parentEntity).build());
		
		return saver;
	}
	
	
	
	
}
