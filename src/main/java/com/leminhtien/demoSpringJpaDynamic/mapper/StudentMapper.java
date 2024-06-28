package com.leminhtien.demoSpringJpaDynamic.mapper;

import com.leminhtien.demoSpringJpaDynamic.dto.request.CreateStudentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.UpdateStudentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.response.DepartmentResponseDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.response.StudentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.response.GetOneStudentDTO;
import com.leminhtien.demoSpringJpaDynamic.entity.DepartmentEntity;
import com.leminhtien.demoSpringJpaDynamic.entity.StudentEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentEntity toEntity(CreateStudentDTO studentDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateStudentDTO studentDTO, @MappingTarget StudentEntity studentEntity);

    @Mapping( target = "department", source = "department" )
    GetOneStudentDTO toResponseDTO(StudentEntity studentEntity);

    @Mapping(target = "departmentId",source = "department.id")
    StudentDTO toDTO(StudentEntity studentEntity);
     DepartmentResponseDTO toDTO(DepartmentEntity department);
}
