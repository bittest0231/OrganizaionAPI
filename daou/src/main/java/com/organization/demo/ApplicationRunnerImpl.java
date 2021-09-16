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
		
		// �׽�Ʈ ������ �Է�
		OrganizationsEntity company = OrganizationsEntity.builder().name("ABCȸ��").code("100").type("Company").build(); 
	
		OrganizationsEntity BS = OrganizationsEntity.builder().name("�濵��������").code("110").type("Division").parent(company).build();
		
		OrganizationsEntity BS1 = OrganizationsEntity.builder().name("�λ���").code("111").type("Department").parent(BS).build();
		OrganizationsEntity BS2 = OrganizationsEntity.builder().name("�ѹ���").code("112").type("Department").parent(BS).build();
		OrganizationsEntity BS3 = OrganizationsEntity.builder().name("������").code("113").type("Department").parent(BS).build();
		
		OrganizationsEntity swDev = OrganizationsEntity.builder().name("SW���ߺ���").code("120").type("Division").parent(company).build(); 
		
		OrganizationsEntity flatformDev = OrganizationsEntity.builder().name("�÷������ߺ�").code("D121").type("Department").parent(swDev).build(); 

		OrganizationsEntity flatformDev1 = OrganizationsEntity.builder().name("�����÷�����").code("D1211").type("Department").parent(flatformDev).build(); 
		OrganizationsEntity flatformDev2 = OrganizationsEntity.builder().name("�������").code("D1212").type("Department").parent(flatformDev).build(); 
		OrganizationsEntity flatformDev3 = OrganizationsEntity.builder().name("�׷�������").code("D1213").type("Department").parent(flatformDev).build(); 
		
		OrganizationsEntity bizServiceDev = OrganizationsEntity.builder().name("����񽺰��ߺ�").code("D122").type("Department").parent(swDev).build(); 

		OrganizationsEntity bizServiceDev1 = OrganizationsEntity.builder().name("�÷���������").code("D1221").type("Department").parent(bizServiceDev).build(); 
		OrganizationsEntity bizServiceDev2 = OrganizationsEntity.builder().name("����ϰ�����").code("D1222").type("Department").parent(bizServiceDev).build(); 
		
		List<OrganizationsEntity> list = Arrays.asList(
			company,BS,BS1,BS2,BS3
			,swDev,flatformDev,flatformDev1,flatformDev2,flatformDev3,bizServiceDev,bizServiceDev1,bizServiceDev2
		);
		repo.saveAll(list);
		
		
		memRepo.save(MemberEntity.builder().name("����1").team(company).manager(true).build());
		
		memRepo.save(MemberEntity.builder().name("�濵1").team(BS).manager(true).build());
		
		memRepo.save(MemberEntity.builder().name("�λ�1").team(BS1).manager(false).build());
		memRepo.save(MemberEntity.builder().name("�λ�2").team(BS1).manager(false).build());
		memRepo.save(MemberEntity.builder().name("�λ�3").team(BS1).manager(false).build());
		
		memRepo.save(MemberEntity.builder().name("�ѹ�1").team(BS2).manager(false).build());
		memRepo.save(MemberEntity.builder().name("�ѹ�2").team(BS2).manager(false).build());
		
		memRepo.save(MemberEntity.builder().name("����1").team(BS3).manager(false).build());
		memRepo.save(MemberEntity.builder().name("����2").team(BS3).manager(false).build());
		
		
		memRepo.save(MemberEntity.builder().name("SW1").team(swDev).manager(true).build());
		
		memRepo.save(MemberEntity.builder().name("�÷���1").team(flatformDev).manager(true).build());
		memRepo.save(MemberEntity.builder().name("�÷���1").team(flatformDev1).manager(true).build());
		
		memRepo.save(MemberEntity.builder().name("����1").team(flatformDev1).manager(false).build());
		memRepo.save(MemberEntity.builder().name("����2").team(flatformDev1).manager(false).build());
		
		memRepo.save(MemberEntity.builder().name("����3").team(flatformDev2).manager(false).build());
		memRepo.save(MemberEntity.builder().name("����4").team(flatformDev2).manager(false).build());
		memRepo.save(MemberEntity.builder().name("����5").team(flatformDev3).manager(false).build());
		memRepo.save(MemberEntity.builder().name("����6").team(flatformDev3).manager(false).build());
		
		memRepo.save(MemberEntity.builder().name("����1").team(bizServiceDev).manager(true).build());
		memRepo.save(MemberEntity.builder().name("����7").team(bizServiceDev1).manager(false).build());
		memRepo.save(MemberEntity.builder().name("����8").team(bizServiceDev1).manager(false).build());
		memRepo.save(MemberEntity.builder().name("����9").team(bizServiceDev2).manager(false).build());
		memRepo.save(MemberEntity.builder().name("����10").team(bizServiceDev2).manager(false).build());
		
		
		
		
		
		
		
	}
	
}
