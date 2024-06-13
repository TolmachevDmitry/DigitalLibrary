package com.tolmic.digitallibrary.entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_name")
    private String name;

    @Column(name = "year_creation1")
    private Long yearCreation1;

    @Column(name = "year_creation2")
    private Long yearCreation2;

    @Column(name = "genre")
    private String genre;

    @Column(name = "annotation")
    private String annotation;

    @OneToOne
    @JoinColumn(name = "original_language_id", referencedColumnName = "original_language_id")
    private OriginalLanguage originalLanguage;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "book_id")
    private List<BookDivision> bookDivisions = new ArrayList<BookDivision>();

    @ManyToMany
    @JoinTable(
            name = "user_heart_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> userFan = new ArrayList<>();


    public Book() {

    }

    public Book(String name, Long yearCreation1, Long yearCreation2, String genre,
                String annotation, OriginalLanguage originalLanguage)
    {
        this.name = name;
        this.yearCreation1 = yearCreation1;
        this.yearCreation2 = yearCreation2;
        this.genre = genre;
        this.annotation = annotation;
        this.originalLanguage = originalLanguage;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getYearCreation1() {
        return yearCreation1;
    }

    public Long getYearCreation2() {
        return yearCreation2;
    }

    public String getGenre() {
        return genre;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getOriginalLanguage() {
        return originalLanguage.getName();
    }

    public List<BookDivision> getBookDivisions() {
        return bookDivisions;
    }

    public int getCountHeart() {
        return userFan.size();
    }

    public void setBookDivision(BookDivision bookDivision) {
        bookDivisions.add(bookDivision);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYearCreation1(Long yearCreation1) {
        this.yearCreation1 = yearCreation1;
    }

    public void setYearCreation2(Long yearCreation2) {
        this.yearCreation2 = yearCreation2;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setUserFan(User user) {
        userFan.add(user);
    }


    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setOriginalLanguage(OriginalLanguage originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public int getAuthorCount() {
        return authors.size();
    }

}
