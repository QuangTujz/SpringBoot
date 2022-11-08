package com.btl.Books.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btl.Books.Entity.Book;
import com.btl.Books.Repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImp implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book getBookById(int id) {
        Optional<Book> optional = bookRepository.findById(id);
        Book book = null;
        if(optional.isPresent()) {
            book = optional.get();
        }
        else {
            throw new RuntimeException("Book not found with ID: " + id);
        }
        return book;
    }

    @Override
    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBook(int id, Book book) {
        Optional<Book> optional = bookRepository.findById(id);
        Book bookInDB = null;
        if(optional.isPresent()) {
            bookInDB = optional.get();
            bookInDB.setTitle(book.getTitle());
            bookInDB.setAuthor(book.getAuthor());
            bookInDB.setCategory(book.getCategory());
            bookInDB.setReleasedate(book.getReleasedate());
            bookInDB.setPage(book.getPage());
            bookInDB.setPhoto(book.getPhoto());
        }
        else {
            throw new RuntimeException("Book not found with ID: " + id);
        }

        bookRepository.save(bookInDB);
    }

    @Override
    public boolean isTitleUnique(String title) {
        Book bookByTitle = bookRepository.findByTitle(title);
        return bookByTitle == null;
    }

}
