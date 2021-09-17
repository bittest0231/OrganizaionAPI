package com.organization.demo.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.organization.demo.entity.MemberEntity;
import com.organization.demo.entity.OrganizationsEntity;
import com.organization.demo.model.DeptModel;
import com.organization.demo.model.MemberModel;
import com.organization.demo.repository.MemberRepository;
import com.organization.demo.repository.OrganizationsRepository;
import com.organization.demo.service.MemberService;
import com.organization.demo.service.OrganizationsService;

@SpringBootTest
public class FuntionTest {

	@Autowired
	OrganizationsRepository orgRepo;
	
	@Autowired
	MemberRepository memRepo;
	
	@Autowired
	MemberService memService;
	
	@Autowired
	OrganizationsService orgService;
	
	@DisplayName("부서원 추가 메소드 테스트")
	@Test
	void createMemberTest() {
		
		List<Long> team = new ArrayList<>();
		
		team.add(1L);
		MemberEntity toSave = null;
		try {
			toSave = memService.createMember(MemberModel
					.builder()
					.name("테스트 이름")
					.manager(false)
					.team(team)
					.build());
			
			MemberEntity fromSave = memRepo.findById(toSave.getId()).get();
			
			assertEquals( toSave.getId(), fromSave.getId() );
			assertEquals( toSave.getName(), fromSave.getName() );
			assertEquals( fromSave.getName(), "테스트 이름" );
			assertEquals( toSave.isManager(), fromSave.isManager() );
			assertEquals( toSave.getTeam().get(0).getCode(), fromSave.getTeam().get(0).getCode() );
			assertEquals( toSave.getTeam().get(0).getId(), 1L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@DisplayName("부서원 수정 메소드 테스트")
	@Test
	void updateMemberTest() {
		MemberEntity toUpdate = null;
		
		MemberEntity nothing = memRepo.findById( 1L ).get();
		
		assertEquals( nothing.getTeam().get(0).getId(), 1L );
		
		List<Long> team = new ArrayList<>();
		team.add(2L);
		
		try {
			toUpdate = memService.updateMember(1L,MemberModel
					.builder()
					.name("이름수정")
					.manager(false)
					.team(team)
					.build() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MemberEntity fromUpdate = memRepo.findById(1L).get();
		
		assertEquals( toUpdate.getId(), fromUpdate.getId() );
		assertEquals( fromUpdate.getName(), "이름수정" );
		assertEquals( toUpdate.isManager(), fromUpdate.isManager() );
		assertEquals( toUpdate.getTeam().get(0).getCode(), fromUpdate.getTeam().get(0).getCode() );
		assertEquals( toUpdate.getTeam().get(0).getId(), 2L);
	}
	
	@DisplayName("부서원 삭제 메소드 테스트")
	@Test
	void deleteMemberTest() {
		
		assertEquals(false, memRepo.findById(2L).isEmpty());
		
		try {
			memService.deleteMember( 2L );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    assertEquals(true, memRepo.findById(2L).isEmpty());
	}
	
	@DisplayName("부서 추가 메소드 테스트")
	@Test
	void createDept() {
		
		OrganizationsEntity toSave = null;
		try {
			toSave = orgService.createDept(DeptModel.builder()
					.code("333")
					.name("테스트부서")
					.parentId(3L)
					.type("Division")
					.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals( toSave.getName(), "테스트부서" );
		assertEquals( toSave.getType(), "Division" );
		assertEquals( toSave.getCode(), "333" );
		assertEquals( toSave.getParent().getId(), 3L);
	}
	
	@DisplayName("부서 수정 메소드 테스트")
	@Test
	void updateDept() {
		
		OrganizationsEntity toUpdate = null;
		
		try {
			toUpdate = orgService.updateDept(3L, DeptModel.builder()
						.code("333")
						.name("테스트부서")
						.parentId(2L)
						.type("Division")
						.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals( toUpdate.getName(), "테스트부서" );
		assertEquals( toUpdate.getType(), "Division" );
		assertEquals( toUpdate.getCode(), "333" );
		assertEquals( toUpdate.getParent().getId(), 2L);
	}
	
	@DisplayName("부서 삭제 메소드 테스트")
	@Test
	void deleteDept() {
		
		OrganizationsEntity toSave = null;
		try {
			toSave = orgService.createDept(DeptModel.builder()
					.code("000")
					.name("삭제될부서")
					.parentId(1L)
					.type("Division")
					.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(false, orgRepo.findById(toSave.getId()).isEmpty());
		
		try {
			orgService.deleteDept( toSave.getId() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    assertEquals(true, orgRepo.findById(toSave.getId()).isEmpty());
	}
	
}
