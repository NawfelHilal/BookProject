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

    private String isbn;
    private String title;
    private String author;
    private Integer publicationYear;
    private Integer pageCount;

    @Lob
    private String backCoverText;

    @Lob
    private byte[] coverImage;

    // Constructor avec paramètres
    public Book(String isbn, String title, String author, Integer publicationYear, Integer pageCount, String backCoverText, byte[] coverImage) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.pageCount = pageCount;
        this.backCoverText = backCoverText;
        this.coverImage = coverImage;
    }

    // Constructeur sans paramètres (requis pour Hibernate)
    public Book() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getBackCoverText() {
        return backCoverText;
    }

    public void setBackCoverText(String backCoverText) {
        this.backCoverText = backCoverText;
    }

    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    // Méthode pour fournir un aperçu des informations du livre
    public String getPreview() {
        return this.getTitle() + "\n\n" +
                "Author: " + this.getAuthor() + "\n" +
                "ISBN: " + this.getIsbn() + "\n" +
                "Publication Year: " + this.getPublicationYear() + "\n" +
                "Page Count: " + this.getPageCount() + "\n\n" +
                "Back Cover Text: \n" + this.getBackCoverText();
    }

    // Redéfinir la méthode toString pour afficher le titre du livre
    @Override
    public String toString() {
        return getTitle();
    }
}
