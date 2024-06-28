package com.leminhtien.demoSpringJpaDynamic.repository;

import java.util.Map;

import com.leminhtien.demoSpringJpaDynamic.dto.request.StudentSearchDTO;

public interface CustomStudentRepository {
	
	 Map<String, Object> getPage(StudentSearchDTO request);

}
