package com.leminhtien.demoSpringJpaDynamic.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStudentDTO {
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private String address;
    private int age;
    private String province;
    private float height;
    private double tuition;
    @JsonProperty("department_id")
    private Long departmentId;
}
