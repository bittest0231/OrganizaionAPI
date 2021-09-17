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
					try {
						return orgService.getOnlyOrgFromKeyword(searchType, searchKeyword);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					// 부서원 키워드로 검색
					try {
						return orgService.getOrgFromKeyword(searchType, searchKeyword);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
				try {
					return orgService.getOnlyOrganizations(deptCode);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				// 부서, 부서원 검색
				try {
					return orgService.getOrganizations(deptCode);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			try {
				return orgService.getOnlyOrganizations();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			// 모든 파라미터가 사용되지 않을 때
			try {
				return orgService.getOrganizations();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// TODO
		return ResponseEntity.ok(null);
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
	public ResponseEntity<?> createDept(@RequestBody DeptModel model) {
		
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
	
	// 부서정보 업데이트
	@PutMapping("/dept/{deptId}")
	public ResponseEntity<?> updateDept(@PathVariable Long deptId, @RequestBody DeptModel model) {
		
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
	
	// 부서 삭제
	@DeleteMapping("/dept/{deptId}")
	public ResponseEntity<?> deleteDept(@PathVariable Long deptId) {
		
		try {
			orgService.deleteDept(deptId);
			
		} catch (InvalidDataException ide) {
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message(ide.getMessage()).build());
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().build());	
		}
		
		return null;
	}
	
	// 부서원 추가
	@PostMapping("/member")
	public ResponseEntity<?> createMember(@RequestBody MemberModel model) {
		
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
	
	// 부서원 수정
	@PutMapping("/member/{memberId}")
	public ResponseEntity<?> updateMemeber(@PathVariable Long memberId, @RequestBody MemberModel model) {
		
		MemberEntity result = null;
		
		try {
			result = memService.updateMember(memberId, model);
			
		} catch (InvalidDataException ide) {
			
			return ResponseEntity.badRequest().body(ErrorBody400.builder().message(ide.getMessage()).build());
		} catch (Exception e) {
			
//			e.printStackTrace();
			return ResponseEntity.internalServerError().body(ErrorBody500.builder().build());
		}
		
		return ResponseEntity.ok(result);
	}
	
	// 부서원 삭제
	@DeleteMapping("/member/{memberId}")
	public ResponseEntity<?> deleteMember(@PathVariable Long memberId) {
		
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
