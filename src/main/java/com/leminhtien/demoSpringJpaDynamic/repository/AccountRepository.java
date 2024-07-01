package com.leminhtien.demoSpringJpaDynamic.repository;

import com.leminhtien.demoSpringJpaDynamic.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity,Long> {

    @Query("SELECT a FROM AccountEntity a JOIN FETCH a.roles WHERE a.username = :username")
    Optional<AccountEntity> findOneByUsername(String username);
}
