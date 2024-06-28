package com.leminhtien.demoSpringJpaDynamic.mapper;

import com.leminhtien.demoSpringJpaDynamic.dto.request.CreateDepartmentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.UpdateDepartmentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.response.DepartmentResponseDTO;
import com.leminhtien.demoSpringJpaDynamic.entity.DepartmentEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentResponseDTO toResponseDTO(DepartmentEntity department);
    DepartmentEntity toEntity(CreateDepartmentDTO departmentDTO);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeDTO(UpdateDepartmentDTO departmentDTO, @MappingTarget DepartmentEntity entity);

}
