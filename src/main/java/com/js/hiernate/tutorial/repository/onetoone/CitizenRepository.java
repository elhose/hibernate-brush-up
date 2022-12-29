package com.js.hiernate.tutorial.repository.onetoone;

import com.js.hiernate.tutorial.entity.onetoone.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
}
