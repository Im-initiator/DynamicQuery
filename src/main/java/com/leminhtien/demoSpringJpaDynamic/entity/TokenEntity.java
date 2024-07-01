package com.leminhtien.demoSpringJpaDynamic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "token")
@Getter
@Setter
@NoArgsConstructor
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String accessToken;
    @Column(nullable = false)
    private String refreshToken;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean expired;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "account_id",nullable = false)
    private AccountEntity account;
}
