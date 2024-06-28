package com.leminhtien.demoSpringJpaDynamic.dto.request;


import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;



@Getter
@Setter
public class StudentSearchDTO {
	//	@JsonFormat(pattern = "dd/mm/yyyy")
	//	@JsonProperty("create_date")
	//	private LocalDate creatDate;
	//	@JsonProperty("modify_date")
	//	@JsonFormat(pattern = "dd/mm/yyyy")
	//	private LocalDate modifyDate;
	private int page = 0;
	private int limit =10;
	private String sort;
	private String direction;
	private String name;
	//@JsonProperty("full_name")
	private String fullName;
	private String address;
	private int age;
	private float height;
	private double tuition;
	private String province;
	//	@JsonProperty("date_admission")
//	@JsonFormat(pattern = "dd/mm/yyyy")
	private LocalDate dateAdmission;
//	@JsonProperty("from_admission")
//	@JsonFormat(pattern = "dd/mm/yyyy")
	private LocalDate fromAdmission;
//	@JsonProperty("end_admission")
//	@JsonFormat(pattern = "dd/mm/yyyy")
	private LocalDate endAdmission;
	
}
