package com.organization.demo;

import java.util.ArrayList;
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
		OrganizationsEntity company = OrganizationsEntity.builder().name("ABC회사").code("S100").type("Company").build(); 
	
		OrganizationsEntity BS = OrganizationsEntity.builder().name("경영지원본부").code("A110").type("Division").parent(company).build();
		
		OrganizationsEntity BS1 = OrganizationsEntity.builder().name("인사팀").code("B111").type("Department").parent(BS).build();
		OrganizationsEntity BS2 = OrganizationsEntity.builder().name("총무팀").code("B112").type("Department").parent(BS).build();
		OrganizationsEntity BS3 = OrganizationsEntity.builder().name("법무팀").code("B113").type("Department").parent(BS).build();
		
		OrganizationsEntity swDev = OrganizationsEntity.builder().name("SW개발본부").code("A120").type("Division").parent(company).build(); 
		
		OrganizationsEntity flatformDev = OrganizationsEntity.builder().name("플랫폼개발부").code("B121").type("Department").parent(swDev).build(); 

		OrganizationsEntity flatformDev1 = OrganizationsEntity.builder().name("비즈플랫폼팀").code("C1211").type("Department").parent(flatformDev).build(); 
		OrganizationsEntity flatformDev2 = OrganizationsEntity.builder().name("비즈서비스팀").code("C1212").type("Department").parent(flatformDev).build(); 
		OrganizationsEntity flatformDev3 = OrganizationsEntity.builder().name("그룹웨어개발팀").code("C1213").type("Department").parent(flatformDev).build(); 
		
		OrganizationsEntity bizServiceDev = OrganizationsEntity.builder().name("비즈서비스개발부").code("B122").type("Department").parent(swDev).build(); 

		OrganizationsEntity bizServiceDev1 = OrganizationsEntity.builder().name("플랫폼서비스팀").code("C1221").type("Department").parent(bizServiceDev).build(); 
		OrganizationsEntity bizServiceDev2 = OrganizationsEntity.builder().name("모바일개발팀").code("C1222").type("Department").parent(bizServiceDev).build(); 
		
		List<OrganizationsEntity> list = Arrays.asList(
			company,BS,BS1,BS2,BS3
			,swDev,flatformDev,flatformDev1,flatformDev2,flatformDev3,bizServiceDev,bizServiceDev1,bizServiceDev2
		);
		repo.saveAll(list);
		
		List<OrganizationsEntity> addTeam = new ArrayList<>();
		addTeam.add(company);
		memRepo.save(MemberEntity.builder().name("사장1").team(addTeam).manager(true).build());
		addTeam.clear();
		
		addTeam.add(BS);
		memRepo.save(MemberEntity.builder().name("경영1").team(addTeam).manager(true).build());
		addTeam.clear();

		addTeam.add(BS1);
		memRepo.save(MemberEntity.builder().name("인사1").team(addTeam).manager(false).build());
		memRepo.save(MemberEntity.builder().name("인사2").team(addTeam).manager(false).build());
		memRepo.save(MemberEntity.builder().name("인사3").team(addTeam).manager(false).build());
		addTeam.clear();
		
		addTeam.add(BS2);
		memRepo.save(MemberEntity.builder().name("총무1").team(addTeam).manager(false).build());
		memRepo.save(MemberEntity.builder().name("총무2").team(addTeam).manager(false).build());
		addTeam.clear();

		addTeam.add(BS3);
		memRepo.save(MemberEntity.builder().name("법무1").team(addTeam).manager(false).build());
		memRepo.save(MemberEntity.builder().name("법무2").team(addTeam).manager(false).build());
		addTeam.clear();
		
		addTeam.add(swDev);
		memRepo.save(MemberEntity.builder().name("SW1").team(addTeam).manager(true).build());
		addTeam.clear();
		
		addTeam.add(flatformDev);
		addTeam.add(flatformDev1);
		memRepo.save(MemberEntity.builder().name("플랫폼1").team(addTeam).manager(true).build());
		addTeam.clear();
		
		addTeam.add(flatformDev1);
		memRepo.save(MemberEntity.builder().name("개발1").team(addTeam).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발2").team(addTeam).manager(false).build());
		addTeam.clear();
		
		addTeam.add(flatformDev2);
		memRepo.save(MemberEntity.builder().name("개발3").team(addTeam).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발4").team(addTeam).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발5").team(addTeam).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발6").team(addTeam).manager(false).build());
		addTeam.clear();
		
		addTeam.add(bizServiceDev);
		memRepo.save(MemberEntity.builder().name("서비스1").team(addTeam).manager(true).build());
		addTeam.clear();
		
		addTeam.add(bizServiceDev1);
		memRepo.save(MemberEntity.builder().name("개발7").team(addTeam).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발8").team(addTeam).manager(false).build());
		addTeam.clear();
		
		addTeam.add(bizServiceDev2);
		memRepo.save(MemberEntity.builder().name("개발9").team(addTeam).manager(false).build());
		memRepo.save(MemberEntity.builder().name("개발10").team(addTeam).manager(false).build());
		addTeam.clear();
		
	}
	
}
