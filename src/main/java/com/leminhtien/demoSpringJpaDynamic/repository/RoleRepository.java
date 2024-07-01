package com.leminhtien.demoSpringJpaDynamic.repository;


import com.leminhtien.demoSpringJpaDynamic.entity.RoleEntity;
import com.leminhtien.demoSpringJpaDynamic.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    RoleEntity findByName(RoleEnum name);
}
