package com.leminhtien.demoSpringJpaDynamic.service;

import com.leminhtien.demoSpringJpaDynamic.dto.request.CreateDepartmentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.UpdateDepartmentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.ResponseDTO;

public interface DepartmentService {
    ResponseDTO create (CreateDepartmentDTO departmentDTO);
    ResponseDTO update (Long id,UpdateDepartmentDTO departmentDTO);
    ResponseDTO delete (Long id);
    ResponseDTO delete (Long[] ids);
    ResponseDTO findOne (Long id);

    ResponseDTO findAll();
}
