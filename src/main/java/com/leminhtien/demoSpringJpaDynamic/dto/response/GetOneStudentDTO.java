package com.leminhtien.demoSpringJpaDynamic.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class GetOneStudentDTO {
    private Long id;
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private String address;
    private int age;
    private String province;
    private float height;
    private double tuition;
    @JsonProperty("date_admission")
    private LocalDate dateAdmission;
    @JsonProperty("creat_date")
    private LocalDateTime creatDate;
    @JsonProperty("modify_date")
    private LocalDateTime modifyDate;
    private DepartmentResponseDTO department;
}
