package com.leminhtien.demoSpringJpaDynamic.repository.impl;

import com.leminhtien.demoSpringJpaDynamic.dto.request.StudentSearchDTO;
import com.leminhtien.demoSpringJpaDynamic.entity.StudentEntity;
import com.leminhtien.demoSpringJpaDynamic.repository.CustomStudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomStudentRepositoryImpl implements CustomStudentRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Map<String, Object> getPage(StudentSearchDTO request){

		if (request.getLimit() <= 0 ){
			request.setLimit(10);
		}
		if (request.getSort() == null){
			request.setSort("dateAdmission");
		}
		if(request.getDirection() == null){
			request.setDirection("DESC");
		}else{
			if (!request.getDirection().equalsIgnoreCase("ASC") && !request.getDirection().equalsIgnoreCase("DESC")){
				request.setDirection("DESC");
			}
		}
		Sort sort = Sort.by(Sort.Direction.valueOf(request.getDirection()),request.getSort());
		Pageable pageable = PageRequest.of(request.getPage(),request.getLimit(),sort);

		Map<String, Object> parameter = new HashMap<String, Object>();
		StringBuilder builderQuery = new StringBuilder(" FROM StudentEntity s WHERE 1=1 ");
		if( request.getAddress() !=null&&!request.getAddress().isBlank()) {
			builderQuery.append("AND LOWER(s.address) LIKE LOWER(CONCAT('%',:address,'%')) ");
			parameter.put("address",request.getAddress());
		}
		if(request.getName()!=null&&!request.getName().isBlank()) {
			builderQuery.append("AND LOWER(s.name) LIKE LOWER(CONCAT('%',:name,'%')) ");
			parameter.put("name", request.getName());
		}
		if(request.getFullName()!=null&&!request.getFullName().isBlank()) {
			builderQuery.append("AND LOWER(s.fullName) LIKE LOWER(CONCAT('%',:fullName,'%')) ");
			parameter.put("fullName", request.getFullName());
		}
		if(request.getProvince()!=null&&!request.getProvince().isBlank()) {
			builderQuery.append("AND LOWER(s.province) LIKE LOWER(CONCAT('%',:province,'%')) ");
			parameter.put("province", request.getProvince());
		}
		if(request.getAge() != 0) {
			builderQuery.append("AND s.age = :age ");
			parameter.put("age",request.getAge());
		}
		if(request.getHeight()!=0) {
			builderQuery.append("AND s.height = :height ");
			parameter.put("height",request.getHeight());
		}
		if(request.getTuition()!=0) {
			builderQuery.append("AND s.tuition = :stution ");
			parameter.put("stution",request.getTuition());
		}
		if(request.getDateAdmission()!=null) {
			builderQuery.append("AND s.dateAdmission = :dateAdmission ");
			parameter.put("dateAdmission",request.getDateAdmission());
		}
		if(request.getFromAdmission()!= null && request.getEndAdmission()!= null) {
			builderQuery.append("AND s.dateAdmission BETWEEN :fromAdmission AND :endAdmission ");
			parameter.put("fromAdmission",request.getFromAdmission());
			parameter.put("endAdmission",request.getEndAdmission());
		}else if(request.getFromAdmission()!= null || request.getEndAdmission()!= null) {
			if(request.getFromAdmission()!=null) {
				builderQuery.append("AND s.dateAdmission >=  :dateAdmission");
				parameter.put("dateAdmission",request.getFromAdmission());
			}else {
				builderQuery.append("AND s.dateAdmission >= :dateAdmission ");
				parameter.put("dateAdmission",request.getEndAdmission());
			}
		}
		builderQuery.append(" ORDER BY s.").append(request.getSort()).append(" ").append(request.getDirection());
//		List<Sort.Order> sortList = pageable.getSort().toList();
//		pageable.getSort().stream().forEach(order -> {
//			builderQuery.append("s.").append(order.getProperty()).append(" ").append(order.getDirection());
//			if (order != sortList.getLast()){
//				builderQuery.append(", ");
//			}
//		});
		TypedQuery<StudentEntity> query = entityManager.createQuery(builderQuery.toString(),StudentEntity.class);
		String countQuery = builderQuery.insert(0,"SELECT count(s) ").toString();
		TypedQuery<Long> countResult = entityManager.createQuery(countQuery,Long.class);
		for(String key: parameter.keySet()) {
			query.setParameter(key,parameter.get(key));
			countResult.setParameter(key,parameter.get(key));
		}
		query.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		long count = countResult.getSingleResult();	
		List<StudentEntity> list = query.getResultList();
		
		int totalPage =(int) Math.ceil((double)count/pageable.getPageSize());
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("page",pageable.getPageNumber());
		result.put("total_page", totalPage);
		result.put("total_items",count);
		result.put("limit", pageable.getPageSize());
		result.put("data",list );
		return result;
	}
}
