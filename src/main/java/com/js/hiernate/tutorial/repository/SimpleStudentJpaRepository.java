package com.js.hiernate.tutorial.repository;

import com.js.hiernate.tutorial.entity.SimpleStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleStudentJpaRepository extends JpaRepository<SimpleStudentEntity, Integer> {
}
