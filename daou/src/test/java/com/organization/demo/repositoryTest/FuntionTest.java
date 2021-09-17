package com.organization.demo.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.organization.demo.entity.MemberEntity;
import com.organization.demo.model.MemberModel;
import com.organization.demo.repository.MemberRepository;
import com.organization.demo.repository.OrganizationsRepository;
import com.organization.demo.service.MemberService;

@SpringBootTest
public class FuntionTest {

	@Autowired
	OrganizationsRepository repo;
	
	@Autowired
	MemberRepository memRepo;
	
	@Autowired
	MemberService memService;
	
	@DisplayName("부서원 추가 메소드 테스트")
	@Test
	void createMemberTest() {
		
		
		List<Long> team = new ArrayList<>();
		
		team.add((long)1);
		MemberEntity toSave = null;
		try {
			toSave = memService.createMember(MemberModel
					.builder()
					.name("테스트 이름")
					.manager(false)
					.team(team)
					.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MemberEntity fromSave = memRepo.findById(toSave.getId()).get();
		
		
		assertEquals( toSave.getId(), fromSave.getId() );
		assertEquals( toSave.getName(), fromSave.getName() );
		assertEquals( fromSave.getName(), "테스트 이름" );
		assertEquals( toSave.isManager(), fromSave.isManager() );
		assertEquals( toSave.getTeam().get(0).getCode(), fromSave.getTeam().get(0).getCode() );
		assertEquals( toSave.getTeam().get(0).getId(), (long)1);
	}
	
	@DisplayName("부서원 수정 메소드 테스트")
	@Test
	void updateMemberTest() {
		MemberEntity toUpdate = null;
		
		MemberEntity nothing = memRepo.findById((long)1).get();
		
		assertEquals( nothing.getTeam().get(0).getId(), (long)1 );
		
		List<Long> team = new ArrayList<>();
		team.add((long)2);
		
		try {
			toUpdate = memService.updateMember((long)1,MemberModel
					.builder()
					.name("이름수정")
					.manager(false)
					.team(team)
					.build() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MemberEntity fromUpdate = memRepo.findById((long)1).get();
		
		assertEquals( toUpdate.getId(), fromUpdate.getId() );
		assertEquals( fromUpdate.getName(), "이름수정" );
		assertEquals( toUpdate.isManager(), fromUpdate.isManager() );
		assertEquals( toUpdate.getTeam().get(0).getCode(), fromUpdate.getTeam().get(0).getCode() );
		assertEquals( toUpdate.getTeam().get(0).getId(), (long)2);
	}
	
	@DisplayName("부서원 삭제 메소드 테스트")
	@Test
	void deleteMemberTest() {
		
		assertEquals(false, memRepo.findById((long)2).isEmpty());
		
		try {
			memService.deleteMember( (long)2 );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    assertEquals(true, memRepo.findById((long)2).isEmpty());

	}
	
}
