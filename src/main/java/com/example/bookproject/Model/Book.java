package com.example.bookproject.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String isbn;
    private String title;
    private String author;
    private String publicationYear;
    private String pageCount;
    private String summary;


    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    // Constructor avec paramètres
    public Book(String isbn, String title, String author, String summary, String publicationYear, String pageCount) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.publicationYear = publicationYear;
        this.pageCount = pageCount;

    }

    // Constructeur sans paramètres (requis pour Hibernate)
    public Book() {
    }

    // Getters et Setters

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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


    public String getSummary() {
        return summary;
    }

    public void setSummary(String backCoverText) {
        this.summary = backCoverText;
    }


    // Méthode pour fournir un aperçu des informations du livre
    public String getPreview() {
        return "\n\nTitle" + this.getTitle() + "\n " +
                "\n\nAuthor: " + this.getAuthor() + "\n" +
                "\n\nISBN: " + this.getIsbn() + "\n" +
                "\n\nSummary: \n" + this.getSummary();
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
