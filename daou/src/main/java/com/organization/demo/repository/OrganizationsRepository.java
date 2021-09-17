package com.organization.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organization.demo.entity.OrganizationsEntity;

@Repository
public interface OrganizationsRepository extends JpaRepository<OrganizationsEntity, Long>{
	
//	OrganizationsEntity findById(Long id);
	
	List<OrganizationsEntity> findByIdIn(List<Long> id);
	
	List<OrganizationsEntity> findByCode(String code);
	
	List<OrganizationsEntity> findByNameContains(String name);

	void deleteById(Long id);
	
	
}
