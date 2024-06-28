package com.leminhtien.demoSpringJpaDynamic.service;

import java.util.Map;

import com.leminhtien.demoSpringJpaDynamic.dto.request.StudentSearchDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.CreateStudentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.UpdateStudentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.ResponseDTO;

public interface StudentService {
	ResponseDTO findAll(StudentSearchDTO request);
	ResponseDTO getPage(StudentSearchDTO request);
	ResponseDTO getSearch(StudentSearchDTO studentSearchDTO);

    ResponseDTO createStudent(CreateStudentDTO studentDTO, Long id);

	ResponseDTO updateStudent(UpdateStudentDTO studentDTO, Long studentId);

	ResponseDTO findOne(Long id);
	ResponseDTO findOne(Long departmentId, Long studentId);

	ResponseDTO delete(Long studentId);

}
