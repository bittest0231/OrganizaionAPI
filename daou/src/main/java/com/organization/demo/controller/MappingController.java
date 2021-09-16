package com.organization.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.organization.demo.service.OrganizationsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MappingController {

	
	
	private final OrganizationsService orgService;
	
	@RequestMapping("/org/organizations")
	public List<?> selectOrganizations(
			@RequestParam(value="deptCode", required = false) String deptCode
			,@RequestParam(value="deptOnly" , required = false) boolean deptOnly
			,@RequestParam(value="searchType" , required = false) String searchType
			,@RequestParam(value="searchKeyword" , required = false) String searchKeyword
	){

		System.out.println(deptCode);
		System.out.println(deptOnly);
		System.out.println(searchType);
		System.out.println(searchKeyword);
		
		
		/* 
		 * deptOnly true  부서만
		 * deptOnly false  부서원 포함
		 */
		if(deptOnly) {
			return orgService.getOnlyOrganizations();
		}

		
		if(deptCode != null && !"".equals(deptCode)) {
			System.out.println("deptCode is not null");
			return orgService.getOrganizations(deptCode);
		}else {
			
			System.out.println("deptCode is null");
		}
		
		return orgService.getOrganizations();
		
	}
	

	
}
