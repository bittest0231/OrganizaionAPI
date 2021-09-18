package com.organization.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.organization.demo.entity.MemberEntity;
import com.organization.demo.entity.OrganizationsEntity;
import com.organization.demo.exception.InvalidDataException;
import com.organization.demo.model.MemberModel;
import com.organization.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final OrganizationsService orgService;
	
	private final MemberRepository MemRepo;
	
	
	public MemberEntity getMemberOne(Long id) throws Exception {
		
		return MemRepo.findById(id)
				.orElseThrow(()-> 
				new InvalidDataException("일치하는 부서원이 존재하지 않습니다.")
			);
	}
	
	// 부서원 추가
	public MemberEntity createMember(MemberModel model) throws Exception {

		if( model.getName() == null || "".equals(model.getName()) ) 
		{
			throw new InvalidDataException("요청값이 적절하지 않습니다.");
		}
		
		List<OrganizationsEntity> deptList = null;
		
		// 부서코드가 1개 이상 담겨져 있을 때
		if(model.getTeam().size()>0) {

			try {
				deptList = orgService.getDeptMany(model.getTeam());
				
			} catch (Exception e) {
				throw e;
			}
			// 받아온 부서코드의 사이즈가 다를경우
			if(deptList.size() < 1 && deptList.size() != model.getTeam().size() ) {
				throw new InvalidDataException("존재하지 않는 부서코드가 포함되어 있습니다.");
			}
		}
		
		MemberEntity result = null;
		//가져온 파라미터들 바탕으로 MemberEntity를 생성
		try {
			result = MemRepo.save(
						MemberEntity
						.builder()
						.name(model.getName())
						.manager(model.isManager())
						.team(deptList)
						.build()
					);
		}catch(Exception e) {
			throw e;
		}
		
		return result;
	}
	
	// 부서원 수정
	public MemberEntity updateMember(Long memberId, MemberModel model ) throws Exception{
		
		MemberEntity entity = null;
		
		if( model.getName() == null || "".equals(model.getName()) 
			|| model.getTeam() == null ) 
		{
			throw new InvalidDataException("요청값이 적절하지 않습니다.");
		}
		
		try {
			entity = getMemberOne(memberId);
			
		} catch(InvalidDataException ide){
			throw ide;
			
		}catch (Exception e) {
			throw e;
		}
		
		
		List<OrganizationsEntity> deptList = null;
		
		// 각각의 정보가 다른경우만 set 해준다
		if( !entity.getName().equals(model.getName()) ) {
			entity.setName(model.getName());
		}
		try {
			
			deptList = orgService.getDeptMany(model.getTeam());
			
		} catch (Exception e) {
			throw e;
		}
		// 포함될 부서 체크
		if(deptList.size() != model.getTeam().size() ) {
			throw new InvalidDataException("존재하지 않는 부서코드가 포함되어 있습니다.");
		}
		
		entity.setTeam(deptList);
		entity.setManager(model.isManager());
		
		return MemRepo.save(entity);
	}
	
	// 부서원 삭제
	@Transactional
	public void deleteMember(Long id) throws Exception {
		
		try {
			getMemberOne(id);
			
		}catch (InvalidDataException ide) {
			throw ide; 
			
		}catch (Exception e) {
			throw e;
		}
		
		MemRepo.deleteById(id);
	}
	
	
}
