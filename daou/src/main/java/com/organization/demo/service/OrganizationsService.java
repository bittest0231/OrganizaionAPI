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
import com.organization.demo.repository.MemberRepository;
import com.organization.demo.repository.OrganizationsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationsService {

	private final OrganizationsRepository OrgRepo;
	
	private final MemberRepository MemRepo;
	
	// ��ü �μ�, ��� ��� �˻�
	public OrganizationResult getOrganizations(){
		
		final List<OrganizationsEntity> list = OrgRepo.findByCode("100");
		
		return list.stream().map(OrganizationResult::new).collect(Collectors.toList()).get(0);
	}
	
	// ��ü �μ��� �˻�
	public OnlyOrganizationResult getOnlyOrganizations(){
		
		final List<OrganizationsEntity> list = OrgRepo.findByCode("100");
		
		return list.stream().map(OnlyOrganizationResult::new).collect(Collectors.toList()).get(0);
	}
		
	// Ư�� �μ� �������� �μ�, �μ��� �˻� 
	public OrganizationResult getOrganizations(String deptCode){
		
		final List<OrganizationsEntity> list = OrgRepo.findByCode(deptCode);
		
		return list.stream().map(OrganizationResult::new).collect(Collectors.toList()).get(0);
	}
	
	// Ư�� �μ� �������� �μ��� �˻�
	public OnlyOrganizationResult getOnlyOrganizations(String deptCode){
		
		final List<OrganizationsEntity> list = OrgRepo.findByCode(deptCode);
		
		return list.stream().map(OnlyOrganizationResult::new).collect(Collectors.toList()).get(0);
	}
	
	/** Ű���� ���� �˻�**/
	
	// Ư�� Ű����� �˻� �μ���
	public List<OnlyOrganizationWithParentResult> getOnlyOrgFromKeyword(String searchType, String keyword){
		
		final List<OrganizationsEntity> list = OrgRepo.findByNameContains(keyword);
		
		return list.stream().map(OnlyOrganizationWithParentResult::new).collect(Collectors.toList());
	}
	
	// Ư�� Ű����� �μ��� �˻� �μ�����
	public List<OrganizationWithParentResult> getOrgFromKeyword(String searchType, String keyword){
		
		final List<MemberEntity> list = MemRepo.findByNameContains(keyword);
		
		return list.stream().map(OrganizationWithParentResult::new).collect(Collectors.toList());
	}
	
	
	
	
	
}
