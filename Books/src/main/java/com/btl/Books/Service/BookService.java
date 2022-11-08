package com.btl.Books.Service;

import java.util.List;
import com.btl.Books.Entity.Book;

public interface BookService {
    List<Book> getAllBook();
    void saveBook(Book book);
    Book getBookById(int id);
    void deleteBookById(int id);
    void updateBook(int id, Book book);

    boolean isTitleUnique(String title);
}
