package com.leminhtien.demoSpringJpaDynamic.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student")
@NoArgsConstructor
@Getter
@Setter
public class StudentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false,length = 50)
	private String name;
	@Column(nullable = false, length = 70)
	private String fullName;
	@Column(nullable = false,length = 100)
	private String address;
	@Column(nullable = false)
	private int age;
	@Column(nullable = false)
	private String province;
	@Column(nullable = false)
	private float height;
	@Column(nullable = false)
	private double tuition;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private LocalDate dateAdmission;
	@Column(nullable = false)
	private LocalDateTime creatDate;
	private LocalDateTime modifyDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id",nullable = false)
	private DepartmentEntity department;

}
