package com.leminhtien.demoSpringJpaDynamic.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentSearchByProcedureResponseDTO {
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
}
