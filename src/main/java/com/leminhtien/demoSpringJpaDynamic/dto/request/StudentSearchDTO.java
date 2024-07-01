package com.leminhtien.demoSpringJpaDynamic.dto.request;


import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;



@Getter
@Setter
public class StudentSearchDTO {
	private int page = 0;
	private int limit =10;
	private String sort;
	private String direction;
	private String name;
	private String fullName;
	private String address;
	private int age;
	private float height;
	private double tuition;
	private String province;
	private LocalDate dateAdmission;
	private LocalDate fromAdmission;
	private LocalDate endAdmission;
	private String departmentName;
	private String departmentAddress;
//	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date departmentTimeStart;
	private double departmentAcreage;
	private Long departmentId;
	
}
