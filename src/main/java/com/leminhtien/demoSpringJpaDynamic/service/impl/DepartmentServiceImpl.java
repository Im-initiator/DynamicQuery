package com.leminhtien.demoSpringJpaDynamic.service.impl;

import com.leminhtien.demoSpringJpaDynamic.dto.request.CreateDepartmentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.UpdateDepartmentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.ResponseDTO;
import com.leminhtien.demoSpringJpaDynamic.entity.DepartmentEntity;
import com.leminhtien.demoSpringJpaDynamic.exception.CustomRuntimeException;
import com.leminhtien.demoSpringJpaDynamic.mapper.DepartmentMapper;
import com.leminhtien.demoSpringJpaDynamic.repository.DepartmentRepository;
import com.leminhtien.demoSpringJpaDynamic.service.DepartmentService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class DepartmentServiceImpl implements DepartmentService {

    DepartmentRepository departmentRepository;
    DepartmentMapper departmentMapper;

    @Override
    public ResponseDTO create(CreateDepartmentDTO departmentDTO) {
        if (departmentDTO.getName()==null||departmentDTO.getName().isBlank()){
            ResponseDTO.failure("Department name not allow null");
        }
        if (departmentDTO.getAddress()==null||departmentDTO.getAddress().isBlank()){
            ResponseDTO.failure("Address not allow null");
        }
        if (departmentDTO.getAcreage()<=0){
            ResponseDTO.failure("Acreage allow less zero");
        }
        DepartmentEntity department = departmentMapper.toEntity(departmentDTO);
        departmentRepository.save(department);
        return ResponseDTO.success("Create department successful");
    }

    @Override
    public ResponseDTO update(Long id,UpdateDepartmentDTO departmentDTO) {

        if (departmentDTO.getName()==null||departmentDTO.getName().isBlank()){
            ResponseDTO.failure("Department name not allow null");
        }
        if (departmentDTO.getAddress()==null||departmentDTO.getAddress().isBlank()){
            ResponseDTO.failure("Address not allow null");
        }
        if (departmentDTO.getAcreage()<=0){
            ResponseDTO.failure("Acreage allow less zero");
        }
        if (id==null){
            ResponseDTO.failure("Department not found");
        }
        DepartmentEntity department = departmentRepository.findOneById(id).orElseThrow(
                () -> new CustomRuntimeException("department not found")
        );

        departmentMapper.mergeDTO(departmentDTO,department);
        departmentRepository.save(department);
        return ResponseDTO.success("update department successful");
    }

    @Override
    public ResponseDTO delete(Long id) {
        if (id == null){
            ResponseDTO.failure("Department not found");
        }
        DepartmentEntity department = departmentRepository.findOneById(id).orElseThrow(
                () -> new CustomRuntimeException("Department not found",HttpStatus.NOT_FOUND)
        );
        departmentRepository.delete(department);
        return ResponseDTO.success("delete department successful");
    }

    @Override
    @Transactional
    public ResponseDTO delete(Long[] ids) {
        try {
            int count = departmentRepository.deleteAllByIds(ids);
            return ResponseDTO.success("delete department successful "+ count + " row");
        }catch (Exception e){
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new CustomRuntimeException("Delete fail",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseDTO findOne(Long id) {
        if (id == null){
            throw new CustomRuntimeException("Department not found",HttpStatus.NOT_FOUND);
        }
        DepartmentEntity department = departmentRepository.findOneById(id).orElseThrow(
                () -> new CustomRuntimeException("Department not found",HttpStatus.NOT_FOUND)
        );
        return ResponseDTO.success("Get department success",departmentMapper.toResponseDTO(department));
    }

    @Override
    public ResponseDTO findAll() {
        List<DepartmentEntity> list = departmentRepository.findAll();
        return ResponseDTO.success("Get all department success",list.stream().map(departmentMapper::toResponseDTO));
    }

}
