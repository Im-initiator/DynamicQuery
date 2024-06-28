package com.leminhtien.demoSpringJpaDynamic.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Getter
@Setter
public class StudentSearchByProcedureDTO {
    private int limit;
    private String sort;
    private String  sortBy;
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
}
