package com.leminhtien.demoSpringJpaDynamic.repository;

import com.leminhtien.demoSpringJpaDynamic.entity.DepartmentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long> {

   @Query("SELECT d FROM DepartmentEntity d WHERE d.id = :id")
   Optional<DepartmentEntity> findOneById(Long id);

   @Transactional
   @Modifying
   @Query("DELETE FROM DepartmentEntity d WHERE d.id IN :ids")
   int deleteAllByIds(Long[] ids);

}
