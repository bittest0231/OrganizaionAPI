package com.organization.demo.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.organization.demo.entity.OnlyOrganizationResult;
import com.organization.demo.entity.OnlyOrganizationWithParentResult;
import com.organization.demo.entity.OrganizationResult;
import com.organization.demo.entity.OrganizationWithParentResult;
import com.organization.demo.entity.OrganizationsEntity;
import com.organization.demo.exception.InvalidDataException;
import com.organization.demo.repository.MemberRepository;
import com.organization.demo.repository.OrganizationsRepository;
import com.organization.demo.service.MemberService;
import com.organization.demo.service.OrganizationsService;

@SpringBootTest
public class SelectTest {

	@Autowired
	OrganizationsRepository orgRepo;
	
	@Autowired
	MemberRepository memRepo;
	
	@Autowired
	MemberService memService;
	
	@Autowired
	OrganizationsService orgService;
	
	@DisplayName("[부서][검색][회사(부서+부서원)]")
	@Test
	@Transactional
	void selectAllOrganizaions() {

		OrganizationResult test = null;
		OrganizationsEntity company = null;
		try {
			company = orgRepo.findByType("Company").get();
			test = orgService.getOrganizations();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(company.getId(),test.getId());
		assertEquals(company.getCode(),test.getCode());
		assertEquals(company.getName(),test.getName());
		assertEquals(company.getMembers().size(),test.getMember().size());
		assertEquals(company.getChildren().size(),test.getChildren().size());
	}
	
	@DisplayName("[부서][검색][회사(부서)]")
	@Test
	@Transactional
	void selectAllOrganizaionsOnlyDept() {
		
		OnlyOrganizationResult test = null;
		OrganizationsEntity company = null;
		try {
			company = orgRepo.findByType("Company").get();
			test = orgService.getOnlyOrganizations();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(company.getId(),test.getId());
		assertEquals(company.getCode(),test.getCode());
		assertEquals(company.getName(),test.getName());
		assertEquals(company.getChildren().size(),test.getChildren().size());
	}
	
	@DisplayName("[부서][검색][부서코드 (부서+부서원)]")
	@Test
	void selectOrganizaionsDeptCode() {
		
		// 테스트 데이터 
		//OrganizationsEntity.builder().name("플랫폼개발부").code("B121").type("Department").parent(swDev).build();
		
		String deptCode = "B121";
		
		OrganizationResult test = null;
		try {
			test = orgService.getOrganizations(deptCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(test.getCode(),"B121");
		assertEquals(test.getName(),"플랫폼개발부");
		assertEquals(test.getType(),"Department");
	}
	
	@DisplayName("[부서][검색][부서코드(부서+부서원) 가 존재하지 않는 오류 ]")
	@Test
	void selectOrganizaionsDeptCodeFail() {
		
		// 테스트 데이터 
		//OrganizationsEntity.builder().name("플랫폼개발부").code("B121").type("Department").parent(swDev).build();
		
		String deptCode = "0";
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			orgService.getOrganizations(deptCode);
		});
		String message = exception.getMessage();
		assertEquals("일치하는 부서가 존재하지 않습니다.", message);
	}
	
	@DisplayName("[부서][검색][부서코드(부서+부서원) 가 공백인 오류 ]")
	@Test
	void selectOrganizaionsDeptCodeFail2() {
		
		// 테스트 데이터 
		//OrganizationsEntity.builder().name("플랫폼개발부").code("B121").type("Department").parent(swDev).build();
		
		String deptCode = "";
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			orgService.getOrganizations(deptCode);
		});
		String message = exception.getMessage();
		assertEquals("요청값이 적절하지 않습니다.", message);
	}
	
	@DisplayName("[부서][검색][부서코드 (부서)]")
	@Test
	void selectOrganizaionsOnlyDeptCode() {
		
		// 테스트 데이터 
		//OrganizationsEntity.builder().name("플랫폼개발부").code("B121").type("Department").parent(swDev).build();
		
		String deptCode = "B121";
		
		OnlyOrganizationResult test = null;
		try {
			test = orgService.getOnlyOrganizations(deptCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(test.getCode(),"B121");
		assertEquals(test.getName(),"플랫폼개발부");
		assertEquals(test.getType(),"Department");
	}
	
	@DisplayName("[부서][검색][부서코드(부서) 가 존재하지 않는 오류 ]")
	@Test
	void selectOrganizaionsOnlyDeptCodeFail() {
		
		// 테스트 데이터 
		//OrganizationsEntity.builder().name("플랫폼개발부").code("B121").type("Department").parent(swDev).build();
		
		String deptCode = "0";
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			orgService.getOrganizations(deptCode);
		});
		String message = exception.getMessage();
		assertEquals("일치하는 부서가 존재하지 않습니다.", message);
	}
	
	@DisplayName("[부서][검색][부서코드(부서) 가 공백인 오류 ]")
	@Test
	void selectOrganizaionsOnlyDeptCodeFail2() {
		
		// 테스트 데이터 
		//OrganizationsEntity.builder().name("플랫폼개발부").code("B121").type("Department").parent(swDev).build();
		
		String deptCode = "";
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			orgService.getOrganizations(deptCode);
		});
		String message = exception.getMessage();
		assertEquals("요청값이 적절하지 않습니다.", message);
	}
	
	@DisplayName("[부서][검색][키워드(부서)]")
	@Test
	void selectManyDeptWithKeyword() {
		
		String type = "dept";
		String word = "플랫폼";
		List<Object> list = null;
		try {
			list = orgService.getOrgFromKeyword(type, word);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(list.size(), 3);
		for(int i=0; i<list.size(); i++) {
			OnlyOrganizationWithParentResult entity = (OnlyOrganizationWithParentResult) list.get(i);
			assertNotEquals(entity.getName().indexOf(word), -1);
			assertEquals(entity.getName().indexOf("아무단어"), -1);
		}
	}
	@DisplayName("[부서][검색][키워드(부서원)]")
	@Test
	void selectManyDeptWithKeyword2() {
		
		String type = "MEMBER";
		String word = "플랫폼";
		List<Object> list = null;
		try {
			list = orgService.getOrgFromKeyword(type, word);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(list.size(), 1);
		for(int i=0; i<list.size(); i++) {
			OrganizationWithParentResult entity = (OrganizationWithParentResult) list.get(i);
			assertNotEquals(entity.getName().indexOf(word), -1);
			assertEquals(entity.getName().indexOf("아무단어"), -1);
		}
	}
	@DisplayName("[부서][검색][키워드 searchType 불일치 오류]")
	@Test
	void selectManyDeptWithKeywordFail() {
		String type = "mem";
		String word = "플랫폼";
		
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			orgService.getOrgFromKeyword(type, word);
		});
		
		assertEquals("searchType 값이 적절하지 않습니다.", exception.getMessage());
	}
	
}
