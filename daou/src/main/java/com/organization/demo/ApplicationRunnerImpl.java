package com.organization.demo;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.organization.demo.entity.MemberEntity;
import com.organization.demo.entity.OrganizationsEntity;
import com.organization.demo.repository.MemberRepository;
import com.organization.demo.repository.OrganizationsRepository;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner{

	
	@Autowired 
	OrganizationsRepository repo;
	
	@Autowired 
	MemberRepository memRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		// 테스트 데이터 입력
		OrganizationsEntity company = OrganizationsEntity.builder().name("ABC회사").code("100").type("Company").build(); 
	
		OrganizationsEntity BS = OrganizationsEntity.builder().name("경영지원본부").code("110").type("Division").parent(company).build();
		
		OrganizationsEntity BS1 = OrganizationsEntity.builder().name("인사팀").code("111").type("Department").parent(BS).build();
		OrganizationsEntity BS2 = OrganizationsEntity.builder().name("총무팀").code("112").type("Department").parent(BS).build();
		OrganizationsEntity BS3 = OrganizationsEntity.builder().name("법무팀").code("113").type("Department").parent(BS).build();
		
		OrganizationsEntity swDev = OrganizationsEntity.builder().name("SW개발본부").code("120").type("Division").parent(company).build(); 
		
		OrganizationsEntity flatformDev = OrganizationsEntity.builder().name("플랫폼개발부").code("D121").type("Department").parent(swDev).build(); 

		OrganizationsEntity flatformDev1 = OrganizationsEntity.builder().name("비즈플랫폼팀").code("D1211").type("Department").parent(flatformDev).build(); 
		OrganizationsEntity flatformDev2 = OrganizationsEntity.builder().name("비즈서비스팀").code("D1212").type("Department").parent(flatformDev).build(); 
		OrganizationsEntity flatformDev3 = OrganizationsEntity.builder().name("그룹웨어개발팀").code("D1213").type("Department").parent(flatformDev).build(); 
		
		OrganizationsEntity bizServiceDev = OrganizationsEntity.builder().name("비즈서비스개발부").code("D122").type("Department").parent(swDev).build(); 

		OrganizationsEntity bizServiceDev1 = OrganizationsEntity.builder().name("플랫폼서비스팀").code("D1221").type("Department").parent(bizServiceDev).build(); 
		OrganizationsEntity bizServiceDev2 = OrganizationsEntity.builder().name("모바일개발팀").code("D1222").type("Department").parent(bizServiceDev).build(); 
		
		List<OrganizationsEntity> list = Arrays.asList(
			company,BS,BS1,BS2,BS3
			,swDev,flatformDev,flatformDev1,flatformDev2,flatformDev3,bizServiceDev,bizServiceDev1,bizServiceDev2
		);
		repo.saveAll(list);
		
		
		memRepo.save(MemberEntity.builder().name("사장1").team(company).manager(true).build());
		
		memRepo.save(MemberEntity.builder().name("경영1").team(BS).manager(true).build());
		
		memRepo.save(MemberEntity.builder().name("인사1").team(BS1).manager(false).build());
		memRepo.save(MemberEntity.builder().name("인사2").team(BS1).manager(false).build());
		memRepo.save(MemberEntity.builder().name("인사3").team(BS1).manager(false).build());
		
		memRepo.save(MemberEntity.builder().name("총무1").team(BS2).manager(false).build());
		memRepo.save(MemberEntity.builder().name("총무2").team(BS2).manager(false).build());
		
		memRepo.save(MemberEntity.builder().name("법무1").team(BS3).manager(false).build());
		memRepo.save(MemberEntity.builder().name("법무2").team(BS3).manager(false).build());
		
		
		memRepo.save(MemberEntity.builder().name("SW1").team(swDev).manager(true).build());
		
		memRepo.save(MemberEntity.builder().name("플랫폼1").team(flatformDev).manager(true).build());
		memRepo.save(MemberEntity.builder().name("플랫폼1").team(flatformDev1).manager(true).build());
		
		memRepo.save(MemberEntity.builder().name("개발1").team(flatformDev1).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발2").team(flatformDev1).manager(false).build());
		
		memRepo.save(MemberEntity.builder().name("개발3").team(flatformDev2).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발4").team(flatformDev2).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발5").team(flatformDev3).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발6").team(flatformDev3).manager(false).build());
		
		memRepo.save(MemberEntity.builder().name("서비스1").team(bizServiceDev).manager(true).build());
		memRepo.save(MemberEntity.builder().name("개발7").team(bizServiceDev1).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발8").team(bizServiceDev1).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발9").team(bizServiceDev2).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발10").team(bizServiceDev2).manager(false).build());
		
		
		
		
		
		
		
	}
	
}
