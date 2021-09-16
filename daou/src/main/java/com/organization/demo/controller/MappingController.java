package com.organization.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.organization.demo.entity.OrganizationsEntity;
import com.organization.demo.model.DeptModel;
import com.organization.demo.model.ErrorBody400;
import com.organization.demo.model.ErrorBody500;
import com.organization.demo.service.OrganizationsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/org")
@RequiredArgsConstructor
@RestController
public class MappingController {

	private final OrganizationsService orgService;
	
	// GET 조직도 조회 API
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
					// 부서 키워드로 검색
					return orgService.getOnlyOrgFromKeyword(searchType, searchKeyword);
				}else{
					// 부서원 키워드로 검색
					return orgService.getOrgFromKeyword(searchType, searchKeyword);
				}
			}else {
				// 키워드 비어있음
				System.out.println("searchKeyword is null");
				
				//TODO errorCode return
			}
		}else {
			// searchType 사용하지 않음 or 비어있음
			System.out.println("searchType is null");
		}
		
		/*
		 * 요구사항에 따라 searchType 과 deptCode는 공존할 수 없음
		 * */
		
		if(deptCode != null && !"".equals(deptCode)) {
			// 기준 부서코드로 검색
			if(deptOnly) {
				// 부서만 검색
				return orgService.getOnlyOrganizations(deptCode);
			}else{
				// 부서, 부서원 검색
				return orgService.getOrganizations(deptCode);
			}
		}else {
			// 기준 부서코드 비어있음
			System.out.println("deptCode is null");
		}
		
		/* 
		 * deptOnly true  부서만
		 * deptOnly false  부서원 포함
		 */
		// searchType 사용하지 않고, deptCode 사용하지 않을 경우에 
		if(deptOnly) {
			// deptOnly 파라미터만 사용 될 때
			return orgService.getOnlyOrganizations();
		}else {
			// 모든 파라미터가 사용되지 않을 때
			return orgService.getOrganizations();
		}
	}
	
	
	// 부서 추가
	/**
	 *
	 * {
  		"code" : "140", "name":"테스트팀", "type":"Division","parentId":1
		}
	 * 
	 * */
	@PostMapping("/dept")
	public ResponseEntity<?> insertDept(@RequestBody DeptModel model) {

		// 입력에 필요로 하는 값이 null 이거나 빈값인 경우 체크
		if(model.getCode() == null || "".equals(model.getCode())  
			|| model.getName() == null || "".equals(model.getName())
			|| model.getType() == null || "".equals(model.getType()) ) 
		{
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message("요청값이 적절하지 않습니다.").build());	
		}
		
		OrganizationsEntity entity = null;
		
		try {
			entity = orgService.insertDept(model);
		}catch(Exception e) {
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().message("내부 서버 오류가 발생했습니다.").build());	
		}
		
		// 부모코드가 존재하지 않는 경우
		if(entity==null) {
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message("일치하는 부모코드가 없습니다.").build());	
		} 
		
		return ResponseEntity.ok(entity);
	}
	//@PutMapping("/dept/{deptId}")
	//@DeleteMapping("/dept/{deptId}")
	
	//TODO 부서원 관리 API
	//@PostMapping("/member")
	//@PutMapping("/member/{memberId}")
	//@DeleteMapping("/member/{memberId}")
	
	

	
}
