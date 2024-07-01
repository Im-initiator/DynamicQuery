package com.leminhtien.demoSpringJpaDynamic.repository;

import com.leminhtien.demoSpringJpaDynamic.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity,Long> {

    Optional<TokenEntity> findByAccessToken(String accessToken);
    Optional<TokenEntity> findByRefreshToken(String accessToken);

}
