package com.organization.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.organization.demo.entity.MemberEntity;
import com.organization.demo.entity.OnlyOrganizationResult;
import com.organization.demo.entity.OnlyOrganizationWithParentResult;
import com.organization.demo.entity.OrganizationResult;
import com.organization.demo.entity.OrganizationWithParentResult;
import com.organization.demo.entity.OrganizationsEntity;
import com.organization.demo.exception.InvalidDataException;
import com.organization.demo.model.DeptModel;
import com.organization.demo.repository.MemberRepository;
import com.organization.demo.repository.OrganizationsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationsService {
	
	@Autowired
	private final OrganizationsRepository OrgRepo;
	
	@Autowired
	private final MemberRepository MemRepo;

	/**
	 * 단일 부서 조회
	 * @author 박세진
	 * @param id 가져오고자 하는 객체의 id 값
	 * @return OrganizationsEntity id 값으로 조회한 부서관련 객체
	 * */	
	public OrganizationsEntity getDeptOne(Long id) throws Exception {
		
		return OrgRepo.findById(id)
				.orElseThrow(()-> 
				new InvalidDataException("일치하는 부서가 존재하지 않습니다.")
			);
	}
	
	/**
	 * 다수 부서 조회
	 * @author 박세진
	 * @param idList 가져오고자 하는 객체의 id들이 담긴 List 객체
	 * @return List<OrganizationsEntity> 각각의 id 값으로 조회한 부서관련 객체가 담긴 List 반환
	 * */	
	public List<OrganizationsEntity> getDeptMany(List<Long> idList) throws Exception {
		
		List<OrganizationsEntity> result = OrgRepo.findByIdIn(idList);
		
		return result;
	}
	
	/**
	 * 부서 타입이 Company인 객체를 조회해 Id값 반환
	 * @author 박세진
	 * @param 
	 * @return long Type이 Company인 객체의 id 값
	 * */
	public long getCompanyId() throws Exception {
		return OrgRepo.findByType("Company")
				.orElseThrow(()-> new InvalidDataException("최상위 Company 부서가 존재하지 않습니다."))
				.getId();
	}
	
	/**
	 * 전체 부서, 멤버 모두 검색
	 * 
	 * @author 박세진
	 * @param 
	 * @return OrganizationResult 부서+해당 부서에 포함된 부서원 보여줄 객체만 따로 담아서 return
	 * */
	@Transactional
	public OrganizationResult getOrganizations() throws Exception{
		
		// 부서 type이 Comany인 객체의 Id값 조회
		long id = getCompanyId();
		
		return new OrganizationResult(
				OrgRepo
				.findById(id)				// Company 객체 id로 조회
				.orElseThrow(()-> new InvalidDataException("일치하는 부서가 존재하지 않습니다.")
			)
		);
	}
	
	/**
	 * 전체 부서만 검색
	 * @author 박세진
	 * @param 
	 * @return OnlyOrganizationResult 부서관련 보여줄 객체만 따로 담아서 return
	 * */
	@Transactional
	public OnlyOrganizationResult getOnlyOrganizations() throws Exception{
		
		// 부서 type이 Comany인 객체의 Id값 조회
		long id = getCompanyId();
		
		return new OnlyOrganizationResult(
				OrgRepo
				.findById(id)
				.orElseThrow(()-> new InvalidDataException("일치하는 부서가 존재하지 않습니다.")
			)
		);
	}

	/**
	 * 특정 부서 기준으로 부서, 부서원 검색
	 * @author 박세진
	 * @param deptCode 반환할 부서 객체의 코드값
	 * @return OrganizationResult 부서+해당 부서에 포함된 부서원 보여줄 객체만 따로 담아서 return
	 * */
	@Transactional
	public OrganizationResult getOrganizations(String deptCode) throws Exception{
		
		// deptCode가 공백값인 경우
		if("".equals(deptCode))	
			throw new InvalidDataException("요청값이 적절하지 않습니다.");
		
		return new OrganizationResult(
				OrgRepo
				.findByCode(deptCode)
				.orElseThrow(()-> new InvalidDataException("일치하는 부서가 존재하지 않습니다.")
			)
		);
	}
	
	/**
	 * 특정 부서 기준으로 부서만 검색
	 * @author 박세진
	 * @param deptCode 반환할 부서 객체의 코드값
	 * @return OnlyOrganizationResult 부서 보여줄 객체만 따로 담아서 return
	 * */	
	@Transactional
	public OnlyOrganizationResult getOnlyOrganizations(String deptCode) throws Exception{
		
		// deptCode가 공백값인 경우
		if("".equals(deptCode))
			throw new InvalidDataException("요청값이 적절하지 않습니다.");
		
		return new OnlyOrganizationResult(
				OrgRepo
				.findByCode(deptCode)
				.orElseThrow(()-> new InvalidDataException("일치하는 부서가 존재하지 않습니다.")
			)
		);
	}
	
	/**
	 * 특정 키워드로 검색
	 * @author 박세진
	 * @param searchType 부서 키워드 검색(dept)과 부서원 키워드 검색(member) 구분값
	 * @param keyword 검색할 키워드
	 * @return List<Object> 부서 키워드 검색과 부서원 키워드 검색의 서로 다른 객체를 검색된 개수만큼 list로 담아 반환
	 * */	
	public List<Object> getOrgFromKeyword(String searchType, String keyword) throws Exception{
		
		if("dept".equals(searchType.toLowerCase())) {
			// 부서 키워드로 검색
			final List<OrganizationsEntity> list = OrgRepo.findByNameContains(keyword);
			
			return list.stream().map(OnlyOrganizationWithParentResult::new).collect(Collectors.toList());
			
		}else if("member".equals(searchType.toLowerCase())) {
			// 부서원 키워드로 검색
			final List<MemberEntity> list = MemRepo.findByNameContains(keyword);
			
			return list.stream().map(OrganizationWithParentResult::new).collect(Collectors.toList());
			
		}else {
			// searchType 코드값이 2가지의 경우에 포함되지 않는 경우
			throw new InvalidDataException("searchType 값이 적절하지 않습니다.");
		}
		
	}
	
	/**
	 * 부서 추가
	 * @author 박세진
	 * @param model 추가에 필요로 하는 정보가 담긴 DeptModel객체
	 * @return OrganizationsEntity 추가에 성공한 결과값 반환
	 * */	
	@Transactional
	public OrganizationsEntity createDept(DeptModel model) throws Exception {
		
		// 입력에 필요로 하는 값이 null 이거나 빈값인 경우 체크
		if(model.getCode() == null || "".equals(model.getCode())  
			|| model.getName() == null || "".equals(model.getName())
			|| model.getType() == null || "".equals(model.getType()) 
			|| model.getParentId() == null) 
		{
			throw new InvalidDataException("요청값이 적절하지 않습니다.");
		}
		
		OrganizationsEntity parentEntity = null;
		try {
			parentEntity = getDeptOne(model.getParentId());
			
		}catch(InvalidDataException ide) {
			throw new InvalidDataException("일치하는 부모코드가 없습니다.");
			
		}catch(Exception e) {
			throw e;
		}
		OrganizationsEntity result = OrgRepo.save(
				OrganizationsEntity
				.builder()
				.code(model.getCode())
				.name(model.getName())
				.type(model.getType())
				.parent(parentEntity)
				.build()
			); 
		return result; 
	}
	
	/**
	 * 부서 수정
	 * @author 박세진
	 * @param id 수정 대상이 될 부서 객체의 id 값
	 * @param model 수정에 필요로 하는 정보가 담긴 DeptModel객체
	 * @return OrganizationsEntity 수정에 성공한 결과값 반환
	 * */
	public OrganizationsEntity updateDept(Long id, DeptModel model) throws Exception  {
		
		// 입력에 필요로 하는 값이 공백값인 경우 체크
		if("".equals(model.getCode()) || "".equals(model.getName()) || "".equals(model.getType()) ) 
		{
			throw new InvalidDataException("요청값이 적절하지 않습니다.");
		}
		
		OrganizationsEntity entity = null;
		
		try {
			entity = getDeptOne(id);
			
		} catch(InvalidDataException ide){
			throw ide;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		// 각각의 정보가 다른경우만 set 해준다
		if( !entity.getName().equals(model.getName()) ) {
			entity.setName(model.getName());
		}
		if( !entity.getCode().equals(model.getCode()) ) {
			entity.setCode(model.getCode());
		}
		if( !entity.getType().equals(model.getType()) ) {
			entity.setType(model.getType());
		}
		if( entity.getParent().getId() != model.getParentId()) {
			
			OrganizationsEntity parentEntity = null;
			try {
				parentEntity = getDeptOne(model.getParentId());
				
			} catch(InvalidDataException ide) {
				throw new InvalidDataException("일치하는 부모코드가 없습니다.");
				
			}catch(Exception e) {
				throw e;
			}
			
			entity.setParent(parentEntity);
		}
		
		return OrgRepo.save(entity);
	}
	
	/**
	 * 부서 삭제
	 * @author 박세진
	 * @param id 삭제 대상이 될 부서 객체의 id 값
	 * @return 
	 * */
	@Transactional
	public void deleteDept(Long id) throws Exception  {
		
		OrganizationsEntity entity = null;
		
		try {
			entity = getDeptOne(id);
			
		} catch(InvalidDataException ide) {
			throw ide;
			
		}catch(Exception e) {
			throw e;
		}
		
		if(entity.getMembers().size() > 0) {
			throw new InvalidDataException("부서에 속한 부서원이 존재합니다.");
		}
		if(entity.getChildren().size() > 0) {
			throw new InvalidDataException("하위 부서가 존재합니다.");
		}
		
		OrgRepo.deleteById(id);
	}
	
	
	
	
}
