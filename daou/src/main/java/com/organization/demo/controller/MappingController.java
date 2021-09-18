package com.organization.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.organization.demo.entity.MemberEntity;
import com.organization.demo.entity.OrganizationsEntity;
import com.organization.demo.exception.InvalidDataException;
import com.organization.demo.model.DeptModel;
import com.organization.demo.model.ErrorBody400;
import com.organization.demo.model.ErrorBody500;
import com.organization.demo.model.MemberModel;
import com.organization.demo.service.MemberService;
import com.organization.demo.service.OrganizationsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/org")
@RequiredArgsConstructor
@RestController
public class MappingController {

	private final OrganizationsService orgService;
	
	private final MemberService memService;
	
	/**
	 * 조직도 조회
	 * 
	 * @author 박세진
	 * @param deptCode 부서코드
	 * @param deptOnly 부서정보만 출력 여부
	 * @param searchType 검색 대상 : "dept" || "member"
	 * @param searchKeyword - 검색 키워드 
	 * @return ResponseEntity http 응답객체에 결과값을 담아서 return 
	 * */
	@GetMapping("/organizations")
	public ResponseEntity<Object> selectOrganizations(
			@RequestParam(value="deptCode", required = false) String deptCode
			,@RequestParam(value="deptOnly" , required = false) boolean deptOnly
			,@RequestParam(value="searchType" , required = false) String searchType
			,@RequestParam(value="searchKeyword" , required = false) String searchKeyword
	){
		try {
			if(searchType != null && !"".equals(searchType)) {
				
				if(searchKeyword != null && !"".equals(searchKeyword)) {
					
					// 키워드 검색으로 진행되는 경우
					return ResponseEntity.ok(orgService.getOrgFromKeyword(searchType, searchKeyword));
					
				}else {
					// 키워드 비어있음
					return ResponseEntity.badRequest().body(ErrorBody400.builder().message("검색 키워드가 비어있습니다.").build());
				}
			}else {}
		}catch (InvalidDataException ide) {
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message(ide.getMessage()).build());
			
		}catch (Exception e) {
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().build());
		}
		/*
		 * 요구사항에 따라 searchType 과 deptCode는 공존할 수 없음
		 * */
		try {
			if(deptCode != null && !"".equals(deptCode)) {
				// 기준 부서코드로 검색
				if(deptOnly) {
					// 부서만 검색
					return ResponseEntity.ok(orgService.getOnlyOrganizations(deptCode));
				}else{
					// 부서, 부서원 검색
					return ResponseEntity.ok(orgService.getOrganizations(deptCode));
				}
			}else {/*기준 부서코드 비어있음*/}
		}catch (InvalidDataException ide) {
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message(ide.getMessage()).build());
			
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().build());
		}
		/* 
		 * deptOnly true  부서만
		 * deptOnly false  부서원 포함
		 */
		// searchType 사용하지 않고, deptCode 사용하지 않을 경우에 
		try {
			if(deptOnly) {
				// deptOnly 파라미터만 사용 될 때
				return ResponseEntity.ok(orgService.getOnlyOrganizations());
			}else {
				// 모든 파라미터가 사용되지 않을 때
				return ResponseEntity.ok(orgService.getOrganizations());
			}
		}catch (InvalidDataException ide) {
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message(ide.getMessage()).build());
			
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().build());
		}
	}
	
	/**
	 * 부서 추가
	 * 
	 * @author 박세진
	 * @param DeptModel 부서관련 정보 객체
	 * 			- code 부서 코드
	 * 			- name 부서 명칭
	 * 			- type 부서 타입
	 * 			- parentId 해당 부서의 부모 부서 : 부모부서의 하위로 위치시킴
	 * 
	 * Param Example
	 * {
  		"code" : "140",		필수
  		"name":"테스트팀", 	필수
  		"type":"Division",	필수
  		"parentId":1		필수
		}
	 *  
	 * @return ResponseEntity http 응답객체에 결과값을 담아서 return
	 * */
	@PostMapping("/dept")
	public ResponseEntity<Object> createDept(@RequestBody DeptModel model) {
		
		OrganizationsEntity entity = null;
		
		try {
			entity = orgService.createDept(model);
			
		}catch(InvalidDataException ide) {
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message(ide.getMessage()).build());
			
		}catch(Exception e) {
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().build());	
		}
		
		return ResponseEntity.ok(entity);
	}
	
	/**
	 * 부서 수정
	 * 
	 * @author 박세진
	 * @param DeptModel 부서관련 정보 객체
	 * 			- code 부서 코드
	 * 			- name 부서 명칭
	 * 			- type 부서 타입
	 * 			- parentId 해당 부서의 부모 부서 : 부모부서의 하위로 위치시킴
	 * 
	 * Param Example
	 * {
  		"code" : "140",		
  		"name":"테스트팀", 	
  		"type":"Division",	
  		"parentId":1		
		}
	 *  
	 * @return ResponseEntity http 응답객체에 결과값을 담아서 return 
	 * */
	@PutMapping("/dept/{deptId}")
	public ResponseEntity<Object> updateDept(@PathVariable Long deptId, @RequestBody DeptModel model) {
		
		OrganizationsEntity entity = null;
		
		try {
			entity = orgService.updateDept(deptId, model);
			
		} catch (InvalidDataException ide) {
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message(ide.getMessage()).build());
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().build());
		}
		
		return ResponseEntity.ok(entity);
	}
	
	/**
	 * 부서 삭제
	 * 
	 * @author 박세진
	 * @param deptId 부서 고유 id
	 * @return void
	 * */
	@DeleteMapping("/dept/{deptId}")
	public ResponseEntity<Object> deleteDept(@PathVariable Long deptId) {
		
		try {
			orgService.deleteDept(deptId);
			
		} catch (InvalidDataException ide) {
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message(ide.getMessage()).build());
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().build());	
		}
		
		return null;
	}
	
	/**
	 * 부서원 추가
	 * 
	 * @author 박세진
	 * @param MemberModel 부서원관련 정보 객체
	 * 			- name 부서원 명칭
	 * 			- manager 관리자 여부 관리자:true/아닌경우:false
	 * 			- team 해당 부서원이 속하는 부서의 고유 id list 형식
	 * Param Example
	 * {
  		"name":"테스트인원",	필수
  		"manager": true, 
  		"team":[1,2]		필수
		}
	 *  
	 * @return ResponseEntity http 응답객체에 결과값을 담아서 return
	 * */
	@PostMapping("/member")
	public ResponseEntity<Object> createMember(@RequestBody MemberModel model) {
		
		MemberEntity result = null;
		
		try {
			
			result = memService.createMember(model);
			
		} catch (InvalidDataException ide) {
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message(ide.getMessage()).build());
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().build());
		}
		
		return ResponseEntity.ok(result);
	}
	/**
	 * 부서원 수정
	 * 
	 * @author 박세진
	 * @param MemberModel 부서원관련 정보 객체
	 * 			- name 부서원 명칭
	 * 			- manager 관리자 여부 관리자:true/아닌경우:false
	 * 			- team 해당 부서원이 속하는 부서의 고유 id list 형식
	 * Param Example
	 * {
  		"name":"테스트인원",	
  		"manager": true, 
  		"team":[]			필수: 아무런 부서에 포함되지 않는다면 [] 비어있는 list로 받아야 한다.
		}
	 *  
	 * @return ResponseEntity http 응답객체에 결과값을 담아서 return
	 * */
	@PutMapping("/member/{memberId}")
	public ResponseEntity<Object> updateMemeber(@PathVariable Long memberId, @RequestBody MemberModel model) {
		
		MemberEntity result = null;
		
		try {
			result = memService.updateMember(memberId, model);
			
		} catch (InvalidDataException ide) {
			
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message(ide.getMessage()).build());
		} catch (Exception e) {
			
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().build());
		}
		
		return ResponseEntity.ok(result);
	}
	/**
	 * 부서원 삭제
	 * 
	 * @author 박세진
	 * @param memberId 부서 고유 id
	 * @return void
	 * */
	@DeleteMapping("/member/{memberId}")
	public ResponseEntity<Object> deleteMember(@PathVariable Long memberId) {
		
		try {
			memService.deleteMember(memberId);
			
		} catch(InvalidDataException ide) {
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message(ide.getMessage()).build());
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().build());
		}
		
		return null;
	}
	

	
}
