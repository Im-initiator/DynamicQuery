package com.leminhtien.demoSpringJpaDynamic.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateStudentDTO {
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private String address;
    private int age;
    private String province;
    private float height;
    private double tuition;
    @JsonProperty("date_admission")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateAdmission;
}
