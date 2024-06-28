package com.leminhtien.demoSpringJpaDynamic.controller;

import com.leminhtien.demoSpringJpaDynamic.dto.request.CreateDepartmentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.UpdateDepartmentDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.ResponseDTO;
import com.leminhtien.demoSpringJpaDynamic.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@CrossOrigin
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ResponseDTO> findAll(){
        return ResponseEntity.ok((departmentService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(departmentService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody CreateDepartmentDTO dto){
        return ResponseEntity.ok(departmentService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id,@RequestBody UpdateDepartmentDTO dto){
        return ResponseEntity.ok(departmentService.update(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id){
        return ResponseEntity.ok(departmentService.delete(id));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteMultipart(@RequestBody Long[] ids){
        return ResponseEntity.ok(departmentService.delete(ids));
    }



}
