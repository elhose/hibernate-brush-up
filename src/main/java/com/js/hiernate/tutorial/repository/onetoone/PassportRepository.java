package com.js.hiernate.tutorial.repository.onetoone;

import com.js.hiernate.tutorial.entity.onetoone.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport, Long> {
}
