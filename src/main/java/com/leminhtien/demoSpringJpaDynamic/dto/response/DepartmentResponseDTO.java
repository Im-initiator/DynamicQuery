package com.leminhtien.demoSpringJpaDynamic.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DepartmentResponseDTO {
    private Long id;
    private String name;
    private String address;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("time_start")
    private Date timeStart;
    private Double acreage;
}
