package com.btl.Books.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.btl.Books.Service.BookService;



public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/book/checkTitle")
    public String checkDuplicatedTitle(@RequestParam(name = "title") String title) {
        return bookService.isTitleUnique(title) ? "OK" : "Duplicated";
    }
}
