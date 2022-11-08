package com.btl.Books.Controller;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;

import com.btl.Books.Entity.Book;
import com.btl.Books.Entity.User;
import com.btl.Books.Repository.UserRepository;
import com.btl.Books.Service.BookService;
 
@Controller
public class AppController {
	
    private int tmpId = 0;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserRepository userRepo;
    
    
	// Users Controller
    @GetMapping("/home")
    public String viewHomePage() {
        return "LoginView/home";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "LoginView/signup_form";
    }
    
    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);  
        userRepo.save(user);
        return "LoginView/register_success";
    }
    
    
    //Books Controller -- 
    
    @GetMapping("/books")
    public String showListBook(Model model) {
        model.addAttribute("books", bookService.getAllBook());
        return "books";
    }

    // Create Book
    @GetMapping("/book/-1")
    public String formAddBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "NewBook";
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult result,
    		@RequestParam("image") MultipartFile image, ModelMap model) throws IOException {
    	
    	if(result.hasErrors() || image.isEmpty()) {
            return "NewBook";
        }
    	Path path = Paths.get("uploads/");
    	try {
    		InputStream inputStream = image.getInputStream();
    		Files.copy(inputStream, path.resolve(image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
    		book.setPhoto(image.getOriginalFilename().toLowerCase());
    		model.addAttribute("book",book);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	bookService.saveBook(book);
        return "redirect:/books";
    }

    // Update book
    @GetMapping("/book/editBook/{id}")
    public String formViewBook(@PathVariable(value = "id") int id, Model model) {
        tmpId = id;
        Book book = bookService.getBookById(tmpId);
        if(book == null) {
            return "error";
        }
        model.addAttribute("book", book);
        return "BookEdited";
    }

    @PutMapping("/edit/{id}")
    public String updateBook(@PathVariable(value = "id") int id,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult result,
                             @RequestParam("image") MultipartFile image, ModelMap model) {
        if(result.hasErrors() || image.isEmpty()) {
            book.setBookcode(tmpId);
            return "BookEdited";
        }
    	Path path = Paths.get("uploads/");
    	try {
    		InputStream inputStream = image.getInputStream();
    		Files.copy(inputStream, path.resolve(image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
    		book.setPhoto(image.getOriginalFilename().toLowerCase());
    		model.addAttribute("book",book);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    // Delete book
    @GetMapping("/book/delete/{id}")
    public String formDeleteBook(@PathVariable(value = "id") int id, Model model) {
        Book book = bookService.getBookById(id);
        if(book == null) {
            return "error";
        }
        model.addAttribute("book", book);
        return "BookDeleted";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") int id){
        bookService.deleteBookById(id);
        return "redirect:/books";
    }
}
