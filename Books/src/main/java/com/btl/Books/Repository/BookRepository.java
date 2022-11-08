package com.btl.Books.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.btl.Books.Entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByTitle(String title);
}
