package com.leminhtien.demoSpringJpaDynamic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.leminhtien.demoSpringJpaDynamic.entity.StudentEntity;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<StudentEntity, Long>, JpaSpecificationExecutor<StudentEntity>{

    @Procedure(procedureName = "SEARCH_STUDENT")
    List<StudentEntity> searchStudent(String model);

    @Procedure(procedureName = "COUNT_STUDENT")
    int countStudentSearch(String model,int i);

    @Query(value = "SELECT s FROM StudentEntity s JOIN s.department d WHERE s.id = :id AND d.id = :departmentId")
    Optional<StudentEntity> findOneByStudentIdAndDepartmentId(Long id, Long departmentId);

    @Query("SELECT s FROM StudentEntity s JOIN FETCH s.department p WHERE s.id = :StuId AND p.id = :departId")
    Optional<StudentEntity> findOneByDepartmentIdAndStudentIdGetReference(Long departId,Long StuId);

    Optional<StudentEntity> findOneById(Long id);

    @Query(value = "SELECT s FROM StudentEntity s JOIN FETCH s.department d WHERE s.id = :id")
    Optional<StudentEntity> findOneByIdGetReference(Long id);
}
