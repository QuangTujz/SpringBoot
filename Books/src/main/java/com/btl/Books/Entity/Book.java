package com.btl.Books.Entity;

import javax.persistence.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookcode")
    private int bookcode;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Empty Title")
    private String title;

    @Column(nullable = false)
    @NotEmpty(message = "Empty Author")
    private String author;

    @Column(nullable = false)
    @NotEmpty(message = "Empty Category")
    private String category;
    
    @Column(nullable = false)
    @NotEmpty(message = "Cannot be empty")
    private String releasedate;
    
    @Column(nullable = false)
    @Pattern(regexp = "[0-9]+", message = "Only numbers allow")
    private String page;
    
    @Column(nullable = true, length = 45)
    private String photo;
    
    public Book() {}
    

	public Book(int bookcode, String title, String author, String category, String releasedate, String page) {
		super();
		this.bookcode = bookcode;
		this.title = title;
		this.author = author;
		this.category = category;
		this.releasedate = releasedate;
		this.page = page;
	}


	public int getBookcode() {
		return bookcode;
	}

	public void setBookcode(int bookcode) {
		this.bookcode = bookcode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(String release) {
		this.releasedate = release;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}
    
}
