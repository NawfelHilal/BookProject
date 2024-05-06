package com.example.bookproject.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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

    private String summary;


    // Constructor avec paramètres
    public Book(String isbn, String title, String author, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;

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
        return this.getTitle() + "\n\n" +
                "Author: " + this.getAuthor() + "\n" +
                "ISBN: " + this.getIsbn() + "\n" +
                "Back Cover Text: \n" + this.getSummary();
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
