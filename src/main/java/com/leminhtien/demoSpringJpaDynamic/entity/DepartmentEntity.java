package com.leminhtien.demoSpringJpaDynamic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 50)
    private String name;
    @Column(nullable = false,length = 100)
    private String address;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date timeStart;
    @Column(nullable = false)
    private Double acreage;

    @OneToMany(mappedBy = "department",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private Set<StudentEntity> students = new HashSet<>();

}
