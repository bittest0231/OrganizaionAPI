package com.organization.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organization.demo.entity.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Integer>{
	
	
	

	
}
