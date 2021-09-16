package com.organization.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.organization.demo.entity.OnlyOrganizationResult;
import com.organization.demo.entity.OrganizationResult;
import com.organization.demo.entity.OrganizationsEntity;
import com.organization.demo.repository.OrganizationsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationsService {

	private final OrganizationsRepository OrgRepo;
	
	// 부서, 멤버 모두 검색
	public List<OrganizationResult> getOrganizations(){
		
		final List<OrganizationsEntity> helloList = OrgRepo.findByCode("100");
		
		return helloList.stream().map(OrganizationResult::new).collect(Collectors.toList());
	}
	
	// 특정 부서 코드를 받을 시
	public List<OrganizationResult> getOrganizations(String deptCode){
		
		final List<OrganizationsEntity> helloList = OrgRepo.findByCode(deptCode);
		
		return helloList.stream().map(OrganizationResult::new).collect(Collectors.toList());
	}
	
	// 부서만 검색
	public List<OnlyOrganizationResult> getOnlyOrganizations(){
		
		final List<OrganizationsEntity> helloList = OrgRepo.findByCode("100");
		
		return helloList.stream().map(OnlyOrganizationResult::new).collect(Collectors.toList());
	}
	
	
}
