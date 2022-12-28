package com.js.hiernate.tutorial.repository.manytomany;

import com.js.hiernate.tutorial.entity.manytomany.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
