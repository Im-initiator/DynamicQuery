package com.leminhtien.demoSpringJpaDynamic.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StudentDTO {
	private Long id;
	private String name;
	@JsonProperty("full_name")
	private String fullName;
	private String address;
	private String province;
	private int age;
	private float height;
	private double tuition;
	@JsonProperty("date_admission")
	private LocalDate dateAdmission;
	@JsonProperty("department_id")
	private Long departmentId;

}
