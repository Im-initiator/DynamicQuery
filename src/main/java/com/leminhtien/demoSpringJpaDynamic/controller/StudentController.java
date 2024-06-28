package com.leminhtien.demoSpringJpaDynamic.controller;

import com.leminhtien.demoSpringJpaDynamic.dto.request.CreateStudentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.UpdateStudentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.leminhtien.demoSpringJpaDynamic.dto.request.StudentSearchDTO;
import com.leminhtien.demoSpringJpaDynamic.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin
public class StudentController {

	@Autowired
	private StudentService service;
	
	@GetMapping("student")
	public ResponseEntity<?> findAll(StudentSearchDTO search){
		return ResponseEntity.ok(service.findAll(search));
	}
	
	@GetMapping("student/entity-manager")
	public ResponseEntity<?> getPage(StudentSearchDTO search){
		return ResponseEntity.ok(service.getSearch(search));
	}

	@PostMapping("department/{id}/student")
	public ResponseEntity<ResponseDTO> createStudent(@RequestBody CreateStudentDTO studentDTO,@PathVariable Long id){
		return ResponseEntity.ok().body(service.createStudent(studentDTO,id));
	}

	@PutMapping("student/{studentId}")
	public ResponseEntity<ResponseDTO> updateStudent(@RequestBody UpdateStudentDTO studentDTO, @PathVariable Long studentId){
		return ResponseEntity.ok().body(service.updateStudent(studentDTO,studentId));
	}

	@GetMapping("student/{id}")
	public ResponseEntity<ResponseDTO> findOne(@PathVariable Long id){
		return ResponseEntity.ok().body(service.findOne(id));
	}

	@DeleteMapping("student/{studentId}")
	public ResponseEntity<ResponseDTO> deleteStudent(@PathVariable Long studentId){
		return ResponseEntity.ok().body(service.delete(studentId));
	}

}
