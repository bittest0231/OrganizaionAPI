package com.organization.demo.repositoryTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.organization.demo.entity.OrganizationsEntity;
import com.organization.demo.repository.OrganizationsRepository;

@SpringBootTest
public class repoTest {

	@Autowired
	OrganizationsRepository repo;
	
	@Test
	void repoTest() {
		repo.save(OrganizationsEntity.builder().name("이름").code("100").type("company").build());
	}
	
	@Test
	void getOrgFromKeywordTest() {
		List<OrganizationsEntity> helloList = repo.findByNameContains("모바일");
	}
	
	
}
