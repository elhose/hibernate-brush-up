package com.js.hiernate.tutorial.repository.sample;

import com.js.hiernate.tutorial.entity.sample.SimpleStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleStudentJpaRepository extends JpaRepository<SimpleStudentEntity, Integer> {
}
