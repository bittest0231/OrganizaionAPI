package com.organization.demo.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.organization.demo.entity.MemberEntity;
import com.organization.demo.entity.OrganizationsEntity;
import com.organization.demo.exception.InvalidDataException;
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
	
	@DisplayName("[부서원][추가]")
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
	
	@DisplayName("[부서원][추가][요청값으로 인한 오류]")
	@Test
	void createMemberFailTest() throws Exception {

		
		// 이름 공백으로 넘어온 경우
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			
			List<Long> team = new ArrayList<>();
			team.add(1L);
			
			memService.createMember(MemberModel
					.builder()
					.name("")
					.manager(false)
					.team(team)
					.build());
		});
		
		String message = exception.getMessage();
		assertEquals("요청값이 적절하지 않습니다.", message);
		
		// 이름 파라미터가 애초에 넘어오지 않은 경우
		InvalidDataException exception2 = assertThrows(InvalidDataException.class, ()->{
			
			List<Long> team = new ArrayList<>();
			team.add(1L);
			
			memService.createMember(MemberModel
					.builder()
					.manager(false)
					.team(team)
					.build());
		});
		
		message = exception2.getMessage();
		assertEquals("요청값이 적절하지 않습니다.", message);
		
		// 팀 객체가 애초에 넘어오지 않은 경우
		InvalidDataException exception3 = assertThrows(InvalidDataException.class, ()->{
			
			memService.createMember(MemberModel
					.builder()
					.name("이름")
					.manager(false)
					.build());
		});
		
		message = exception3.getMessage();
		assertEquals("요청값이 적절하지 않습니다.", message);
	}
	
	@DisplayName("[부서원][추가][존재하지 않는 부서로 인한 오류]")
	@Test
	void createMemberFailTest2() throws Exception {
		
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			
			List<Long> team = new ArrayList<>();
			team.add(0L);
			
			memService.createMember(MemberModel
					.builder()
					.name("테스트")
					.manager(false)
					.team(team)
					.build());
		});
		
		String message = exception.getMessage();
		assertEquals("존재하지 않는 부서코드가 포함되어 있습니다.", message);
	}
	
	@DisplayName("[부서원][수정]")
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
	
	@DisplayName("[부서원][수정][요청값 오류로 인한 오류]")
	@Test
	void updateMemberFailTest() {
		
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			
			List<Long> team = new ArrayList<>();
			team.add(1L);
			
			memService.updateMember(1L,
					MemberModel
					.builder()
					.name("")		// 이름 공백
					.manager(false)
					.team(team)
					.build());
		});
		
		String message = exception.getMessage();
		assertEquals("요청값이 적절하지 않습니다.", message);
		
		InvalidDataException exception2 = assertThrows(InvalidDataException.class, ()->{
			
			List<Long> team = new ArrayList<>();
			team.add(1L);
			
			memService.updateMember(1L,
					MemberModel
					.builder()
					// 이름 파라미터 미존재
					.manager(false)
					.team(team)
					.build());
		});
		
		message = exception2.getMessage();
		assertEquals("요청값이 적절하지 않습니다.", message);
		
		InvalidDataException exception3 = assertThrows(InvalidDataException.class, ()->{
			
			List<Long> team = new ArrayList<>();
			team.add(1L);
			
			memService.updateMember(1L,
					MemberModel
					.builder()
					.name("이름")
					.manager(false)
					// 부서객체 미존재
					.build());
		});
		
		message = exception3.getMessage();
		assertEquals("요청값이 적절하지 않습니다.", message);
	}
	@DisplayName("[부서원][수정][존재하지 않는 부서원으로 인한 오류]")
	@Test
	void updateMemberFailTest2() {
		
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			
			List<Long> team = new ArrayList<>();
			team.add(1L);
			
			memService.updateMember(
					0L,		// 존재하지 않는 부서원
					
					MemberModel
					.builder()
					.name("존재하지않는부서원")
					.manager(false)
					.team(team)
					.build());
		});
		
		String message = exception.getMessage();
		assertEquals("일치하는 부서원이 존재하지 않습니다.", message);
	}
	@DisplayName("[부서원][수정][존재하지 않는 부서로 인한 오류]")
	@Test
	void updateMemberFailTest3() {

		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			
			List<Long> team = new ArrayList<>();
			team.add(0L);	// 존재하지 않는 부서
			
			memService.updateMember(1L,
					MemberModel
					.builder()
					.name("존재하지않는부서")
					.manager(false)
					.team(team)
					.build());
		});
		
		String message = exception.getMessage();
		assertEquals("존재하지 않는 부서코드가 포함되어 있습니다.", message);
	}
	
	
	@DisplayName("[부서원][삭제]")
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
	
	@DisplayName("[부서원][삭제][존재하지 않는 부서원으로 인한 오류]")
	@Test
	void deleteMemberFailTest() {
		
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			memService.deleteMember( 0L );
		});
		
		String message = exception.getMessage();
		assertEquals("일치하는 부서원이 존재하지 않습니다.", message);
	}
	
	@DisplayName("[부서][추가]")
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
	
	@DisplayName("[부서][추가][요청값 오류]")
	@Test
	void createDeptFail() {
		
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			orgService.createDept(DeptModel.builder()
					.code("")		// 코드 공백
					.name("테스트부서")
					.parentId(3L)
					.type("Division")
					.build());
		});
		assertEquals("요청값이 적절하지 않습니다.", exception.getMessage());
		
		InvalidDataException exception2 = assertThrows(InvalidDataException.class, ()->{
			orgService.createDept(DeptModel.builder()
									// 코드 공백
					.name("테스트부서")
					.parentId(3L)
					.type("Division")
					.build());
		});
		assertEquals("요청값이 적절하지 않습니다.", exception2.getMessage());
		
		InvalidDataException exception3 = assertThrows(InvalidDataException.class, ()->{
			orgService.createDept(DeptModel.builder()
					.code("000")
					.name("")		// 이름 공백
					.parentId(3L)
					.type("Division")
					.build());
		});
		assertEquals("요청값이 적절하지 않습니다.", exception3.getMessage());
		
		InvalidDataException exception4 = assertThrows(InvalidDataException.class, ()->{
			orgService.createDept(DeptModel.builder()
					.code("000")
									// 이름 공백
					.parentId(3L)
					.type("Division")
					.build());
		});
		assertEquals("요청값이 적절하지 않습니다.", exception4.getMessage());
		
		InvalidDataException exception5 = assertThrows(InvalidDataException.class, ()->{
			orgService.createDept(DeptModel.builder()
					.code("000")
					.name("이름")
					.parentId(null)			// 부모코드 공백
					.type("Division")
					.build());
		});
		assertEquals("요청값이 적절하지 않습니다.", exception5.getMessage());
		
		InvalidDataException exception5_ = assertThrows(InvalidDataException.class, ()->{
			orgService.createDept(DeptModel.builder()
					.code("000")
					.name("이름")
										// 부모코드 공백
					.type("Division")
					.build());
		});
		assertEquals("요청값이 적절하지 않습니다.", exception5_.getMessage());
		
		InvalidDataException exception6 = assertThrows(InvalidDataException.class, ()->{
			orgService.createDept(DeptModel.builder()
					.code("000")
					.name("이름")
					.parentId(3L)
					.type("")		// 타입 공백
					.build());
		});
		assertEquals("요청값이 적절하지 않습니다.", exception6.getMessage());
		
		InvalidDataException exception7 = assertThrows(InvalidDataException.class, ()->{
			orgService.createDept(DeptModel.builder()
					.code("000")
					.name("이름")
					.parentId(3L)
									// 타입 공백
					.build());
		});
		assertEquals("요청값이 적절하지 않습니다.", exception7.getMessage());
	}
	
	@DisplayName("[부서][추가][부모코드 오류]")
	@Test
	void createDeptFail2() {
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			orgService.createDept(DeptModel.builder()
					.code("000")
					.name("이름")
					.parentId(0L)
					.type("Division")
					.build());
		});
		assertEquals("일치하는 부모코드가 없습니다.", exception.getMessage());
	}
	
	
	@DisplayName("[부서][수정]")
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
	
	@DisplayName("[부서][수정][요청값 오류]")
	@Test
	void updateDeptFail() {
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			orgService.updateDept(
					1L
					,DeptModel.builder()
					.code("")
					.name("이름")
					.parentId(0L)
					.type("Division")
					.build());
		});
		assertEquals("요청값이 적절하지 않습니다.", exception.getMessage());
		InvalidDataException exception2 = assertThrows(InvalidDataException.class, ()->{
			orgService.updateDept(
					1L
					,DeptModel.builder()
					.code("000")
					.name("")
					.parentId(0L)
					.type("Division")
					.build());
		});
		assertEquals("요청값이 적절하지 않습니다.", exception2.getMessage());
		InvalidDataException exception3 = assertThrows(InvalidDataException.class, ()->{
			orgService.updateDept(
					1L
					,DeptModel.builder()
					.code("000")
					.name("이름")
					.parentId(0L)
					.type("")
					.build());
		});
		assertEquals("요청값이 적절하지 않습니다.", exception3.getMessage());
	}
	
	@DisplayName("[부서][수정][존재하지 않는 부서 오류]")
	@Test
	void updateDeptFail2() {
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			orgService.updateDept(
					0L
					,DeptModel.builder()
					.code("000")
					.name("이름")
					.parentId(2L)
					.type("Division")
					.build());
		});
		assertEquals("일치하는 부서가 존재하지 않습니다.", exception.getMessage());
	}
	
	@DisplayName("[부서][수정][존재하지 않는 부모코드 포함 오류]")
	@Test
	void updateDeptFail3() {
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			orgService.updateDept(
					2L
					,DeptModel.builder()
					.code("000")
					.name("이름")
					.parentId(0L)
					.type("Division")
					.build());
		});
		assertEquals("일치하는 부모코드가 없습니다.", exception.getMessage());
	}
	
	@DisplayName("[부서][삭제]")
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
	
	@DisplayName("[부서][삭제][존재하지 않는 부서 오류]")
	@Test
	void deleteDeptFail() {
		
		InvalidDataException exception = assertThrows(InvalidDataException.class, ()->{
			orgService.deleteDept(0L);					
		});
		assertEquals("일치하는 부서가 존재하지 않습니다.", exception.getMessage());
	}
}
