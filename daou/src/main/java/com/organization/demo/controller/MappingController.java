package com.organization.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.organization.demo.service.OrganizationsService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/org")
@RequiredArgsConstructor
@RestController
public class MappingController {

	private final OrganizationsService orgService;
	
	// GET ������ ��ȸ API
	@GetMapping("/organizations")
//	public List<?> selectOrganizations(
	public Object selectOrganizations(
			@RequestParam(value="deptCode", required = false) String deptCode
			,@RequestParam(value="deptOnly" , required = false) boolean deptOnly
			,@RequestParam(value="searchType" , required = false) String searchType
			,@RequestParam(value="searchKeyword" , required = false) String searchKeyword
	){

		if(searchType != null && !"".equals(searchType)) {
			
			if(searchKeyword != null && !"".equals(searchKeyword)) {
				
				if("dept".equals(searchType)) {
					// �μ� Ű����� �˻�
					return orgService.getOnlyOrgFromKeyword(searchType, searchKeyword);
				}else{
					// �μ��� Ű����� �˻�
					return orgService.getOrgFromKeyword(searchType, searchKeyword);
				}
			}else {
				// Ű���� �������
				System.out.println("searchKeyword is null");
				
				//TODO errorCode return
			}
		}else {
			// searchType ������� ���� or �������
			System.out.println("searchType is null");
		}
		
		/*
		 * �䱸���׿� ���� searchType �� deptCode�� ������ �� ����
		 * */
		
		if(deptCode != null && !"".equals(deptCode)) {
			// ���� �μ��ڵ�� �˻�
			if(deptOnly) {
				// �μ��� �˻�
				return orgService.getOnlyOrganizations(deptCode);
			}else{
				// �μ�, �μ��� �˻�
				return orgService.getOrganizations(deptCode);
			}
		}else {
			// ���� �μ��ڵ� �������
			System.out.println("deptCode is null");
		}
		
		/* 
		 * deptOnly true  �μ���
		 * deptOnly false  �μ��� ����
		 */
		// searchType ������� �ʰ�, deptCode ������� ���� ��쿡 
		if(deptOnly) {
			// deptOnly �Ķ���͸� ��� �� ��
			return orgService.getOnlyOrganizations();
		}else {
			// ��� �Ķ���Ͱ� ������ ���� ��
			return orgService.getOrganizations();
		}
	}
	
	
	//TODO �μ����� API
	//@PostMapping("/dept")
	//@PutMapping("/dept/{deptId}")
	//@DeleteMapping("/dept/{deptId}")
	
	//TODO �μ��� ���� API
	//@PostMapping("/member")
	//@PutMapping("/member/{memberId}")
	//@DeleteMapping("/member/{memberId}")
	
	

	
}
