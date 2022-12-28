package com.js.hiernate.tutorial.repository.manytomany;

import com.js.hiernate.tutorial.entity.manytomany.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
