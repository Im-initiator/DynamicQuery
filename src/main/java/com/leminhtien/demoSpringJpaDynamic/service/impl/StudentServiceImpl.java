package com.leminhtien.demoSpringJpaDynamic.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.leminhtien.demoSpringJpaDynamic.dto.ResponseDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.CreateStudentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.StudentSearchDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.UpdateStudentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.response.GetOneStudentDTO;
import com.leminhtien.demoSpringJpaDynamic.entity.DepartmentEntity;
import com.leminhtien.demoSpringJpaDynamic.entity.StudentEntity;
import com.leminhtien.demoSpringJpaDynamic.exception.CustomRuntimeException;
import com.leminhtien.demoSpringJpaDynamic.mapper.StudentMapper;
import com.leminhtien.demoSpringJpaDynamic.repository.CustomStudentRepository;
import com.leminhtien.demoSpringJpaDynamic.repository.DepartmentRepository;
import com.leminhtien.demoSpringJpaDynamic.repository.StudentRepository;
import com.leminhtien.demoSpringJpaDynamic.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService{
	private final StudentRepository studentRepository;
	private final CustomStudentRepository customStudentRepository;
	private final DepartmentRepository departmentRepository;
	private final StudentMapper studentMapper;

	@PersistenceContext
	private final EntityManager entityManager;

	@Override
	public ResponseDTO findAll(StudentSearchDTO request) {
		if (request.getLimit() <= 0 ){
			request.setLimit(10);
		}
		if (request.getPage()<0){
			request.setPage(0);
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
		
		Specification<StudentEntity> specification = (root,query,builder)->{
			Join<StudentEntity,DepartmentEntity> join = null;
			List<Predicate> predicates = new ArrayList<>();
			boolean isJoin = true;
			if( request.getAddress() !=null&&!request.getAddress().isBlank()) {
				predicates.add(builder.like(root.get("address"),"%"+request.getAddress()+"%"));
			}
			if(request.getName()!=null&&!request.getName().isBlank()) {
				predicates.add(builder.like(root.get("name"),"%"+request.getName()+"%"));
			}
			if(request.getFullName()!=null&&!request.getFullName().isBlank()) {
				predicates.add(builder.like(root.get("fullName"),"%"+request.getFullName()+"%"));
			}
			if(request.getProvince()!=null&&!request.getProvince().isBlank()) {
				predicates.add(builder.like(root.get("province"),"%"+request.getProvince()+"%"));
			}
			if(request.getAge() != 0) {
				predicates.add(builder.equal(root.get("age"), request.getAge()));
			}
			if(request.getHeight()!=0) {
				predicates.add(builder.equal(root.get("height"), request.getHeight()));
			}
			if(request.getTuition()!=0) {
				predicates.add(builder.equal(root.get("tuition"), request.getTuition()));
			}
			if(request.getDateAdmission()!=null) {
				predicates.add(builder.equal(root.get("dateAdmission"), request.getDateAdmission()));
			}
			if(request.getFromAdmission()!= null && request.getEndAdmission()!= null) {
				predicates.add(builder.between(root.get("dateAdmission"), request.getFromAdmission(),request.getEndAdmission()));
			}else if(request.getFromAdmission()!= null || request.getEndAdmission()!= null) {
				if(request.getFromAdmission()!=null) {
					predicates.add(builder.greaterThan(root.get("dateAdmission"), request.getFromAdmission()));
				}else {
					predicates.add(builder.greaterThan(root.get("dateAdmission"), request.getEndAdmission()));
				}
			}
			if (request.getDepartmentId()!=null){
				if (join==null){
					join = root.join("department");
					predicates.add(builder.equal(join.get("id"),request.getDepartmentId()));
				}else {
					predicates.add(builder.equal(join.get("id"),request.getDepartmentId()));
				}
			}
			if (request.getDepartmentAcreage()!=0){
				if (join==null){
					join = root.join("department");
					predicates.add(builder.equal(join.get("acreage"),request.getDepartmentAcreage()));
				}else {
					predicates.add(builder.equal(join.get("acreage"),request.getDepartmentAcreage()));
				}
			}
			if (request.getDepartmentName()!=null&&!request.getDepartmentName().isBlank()){
				if (join==null){
					join = root.join("department");
					predicates.add(builder.like(join.get("name"),"%"+request.getDepartmentName()+"%"));
				}else {
					predicates.add(builder.like(join.get("name"),"%"+request.getDepartmentName()+"%"));
				}
			}
			if (request.getDepartmentAddress()!=null&&!request.getDepartmentAddress().isBlank()){
				if (join==null){
					join = root.join("department");
					predicates.add(builder.like(join.get("address"),"%"+request.getDepartmentAddress()+"%"));
				}else {
					predicates.add(builder.like(join.get("address"),"%"+request.getDepartmentAddress()+"%"));
				}
			}
			if (request.getDepartmentTimeStart()!=null){
				if (join==null){
					join = root.join("department");
					predicates.add(builder.equal(join.get("timeStart"),request.getDepartmentTimeStart()));
				}else {
					predicates.add(builder.equal(join.get("timeStart"),request.getDepartmentTimeStart()));
				}
			}
			return builder.and(predicates.toArray(new Predicate[0]));
		};
		Page<StudentEntity> page = studentRepository.findAll(specification,pageable);
		Map<String,Object> result = new HashMap<>();
		result.put("page",pageable.getPageNumber());
		result.put("total_page", page.getTotalPages());
		result.put("total_items",page.getTotalElements());
		result.put("limit", pageable.getPageSize());
		result.put("list",page.getContent().stream().map(studentMapper::toDTO) );
		return ResponseDTO.success(result);
	}

	@Override
	public ResponseDTO getPage(StudentSearchDTO request) {
		return ResponseDTO.success(customStudentRepository.getPage(request));
	}

	@Transactional
	public ResponseDTO getSearch(StudentSearchDTO studentSearchDTO) {
		if (studentSearchDTO.getLimit() <= 0){
			studentSearchDTO.setLimit(10);
		}
		if (studentSearchDTO.getPage()<0){
			studentSearchDTO.setPage(0);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		List<StudentEntity> list;
		String json;
		try {
			json = objectMapper.writeValueAsString(studentSearchDTO);

		}catch (Exception e){
			log.error(e.getMessage());
			log.error(Arrays.toString(e.getStackTrace()));
			throw new RuntimeException("error to convert String");
		}
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SEARCH_STUDENT", StudentEntity.class);
		StoredProcedureQuery queryCount = entityManager.createStoredProcedureQuery("COUNT_STUDENT",Integer.class);
		query.registerStoredProcedureParameter(1,String.class,ParameterMode.IN);
		query.setParameter(1,json);
		queryCount.registerStoredProcedureParameter(1,String.class, ParameterMode.IN);
		queryCount.registerStoredProcedureParameter(2,Integer.class,ParameterMode.OUT);
		queryCount.setParameter(1,json);
		queryCount.execute();
		Integer outParam = (Integer) queryCount.getOutputParameterValue(2);
		list = query.getResultStream().toList();
		int totalPage =(int) Math.ceil((double)outParam/studentSearchDTO.getLimit());
		Map<String,Object> result = new HashMap<>();
		result.put("page",studentSearchDTO.getPage());
		result.put("total_page", totalPage);
		result.put("total_items",outParam);
		result.put("limit", studentSearchDTO.getLimit());
		result.put("list",list.stream().map(studentMapper::toStudentSearchProcedureDTO));
		return ResponseDTO.success(result);
	}

	@Override
	public ResponseDTO createStudent(CreateStudentDTO studentDTO, Long id) {
			if (studentDTO.getAddress()==null||studentDTO.getAddress().isBlank()){
				ResponseDTO.failure("Address not allow null");
			}
			if (studentDTO.getName()==null||studentDTO.getName().isBlank()){
				ResponseDTO.failure("Name not allow null");
			}
			if (studentDTO.getFullName()==null||studentDTO.getFullName().isBlank()){
				ResponseDTO.failure("Full name not allow null");
			}
			if (studentDTO.getDateAdmission()==null){
				ResponseDTO.failure("Date admission not allow null");
			}
			if (studentDTO.getAge()<18){
				ResponseDTO.failure("Age not allow less 18");
			}
			if (studentDTO.getHeight()<=0){
				ResponseDTO.failure("height is not valid");
			}
			if (studentDTO.getProvince()==null||studentDTO.getProvince().isBlank()){
				ResponseDTO.failure("Province not allow null");
			}
			if (studentDTO.getTuition()<=0){
				ResponseDTO.failure("Tuition is not valid");
			}
			if (id == null){
				throw new NullPointerException("Department not found");
			}
			DepartmentEntity departmentEntity = departmentRepository.findOneById(id).orElseThrow(
					() -> new NullPointerException("Department not found")
			);
			StudentEntity studentEntity = studentMapper.toEntity(studentDTO);
			studentEntity.setCreatDate(LocalDateTime.now());
			studentEntity.setDepartment(departmentEntity);
			studentRepository.save(studentEntity);
			return ResponseDTO.success("Create student success");

	}

	@Override
	@Transactional(rollbackOn = {NullPointerException.class,RuntimeException.class})
	public ResponseDTO updateStudent(UpdateStudentDTO studentDTO, Long studentId) {
			if (studentDTO.getDepartmentId()==null){
				ResponseDTO.failure("Department not allow null");
			}
			if (studentDTO.getAddress()==null||studentDTO.getAddress().isBlank()){
				ResponseDTO.failure("Address not allow null");
			}
			if (studentDTO.getName()==null||studentDTO.getName().isBlank()){
				ResponseDTO.failure("Name not allow null");
			}
			if (studentDTO.getFullName()==null||studentDTO.getFullName().isBlank()){
				ResponseDTO.failure("Full name not allow null");
			}
			if (studentDTO.getAge()<18){
				ResponseDTO.failure("Age not allow less 18");
			}
			if (studentDTO.getHeight()<=0){
				ResponseDTO.failure("height is not valid");
			}
			if (studentDTO.getProvince()==null||studentDTO.getProvince().isBlank()){
				ResponseDTO.failure("Province not allow null");
			}
			if (studentDTO.getTuition()<=0){
				ResponseDTO.failure("Tuition is not valid");
			}
			if (studentId ==null){
				ResponseDTO.failure("Student not found");
			}
			StudentEntity studentEntity = studentRepository.findOneByIdGetReference(studentId).orElseThrow(
					() -> new CustomRuntimeException("Student not found")
			);
 			if (!Objects.equals(studentEntity.getDepartment().getId(), studentDTO.getDepartmentId())){
				DepartmentEntity department = departmentRepository.findOneById(studentDTO.getDepartmentId()).orElseThrow(
						() -> new CustomRuntimeException("Department not found",HttpStatus.NOT_FOUND)
				);
				studentEntity.setDepartment(department);
			}
			studentMapper.updateEntity(studentDTO,studentEntity);
			studentRepository.save(studentEntity);
			return ResponseDTO.success("update student success");
	}

	@Override
	public ResponseDTO findOne(Long id) {
		if (id == null){
			return ResponseDTO.failure("Student not found");
		}
		StudentEntity studentEntity = studentRepository.findOneByIdGetReference(id).orElseThrow(
				() -> new CustomRuntimeException("Student not found")
		);
		GetOneStudentDTO response = studentMapper.toResponseDTO(studentEntity);
		return ResponseDTO.success("get student success");
	}

	@Override
	public ResponseDTO findOne(Long departmentId, Long studentId) {
		if (departmentId ==null ||  studentId ==null){
			throw new CustomRuntimeException("Student not found");
		}
		StudentEntity studentEntity = studentRepository.findOneByDepartmentIdAndStudentIdGetReference(departmentId,studentId).orElseThrow(
				() -> new CustomRuntimeException("Student not found")
		);
		GetOneStudentDTO response = studentMapper.toResponseDTO(studentEntity);
		return ResponseDTO.success("get student success");
	}

	@Override
	public ResponseDTO delete(Long studentId) {
			if (studentId ==null){
				return ResponseDTO.failure("Student not found");
			}
			StudentEntity studentEntity = studentRepository.findOneById(studentId).orElseThrow(
					() -> new CustomRuntimeException("Student not found")
			);
			studentRepository.delete(studentEntity);
			return ResponseDTO.success("Delete student success");
	}


}
