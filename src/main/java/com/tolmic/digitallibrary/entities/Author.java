package com.tolmic.digitallibrary.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "date_death")
    private Date dateDeath;

    @Column(name = "country")
    private String country;

    @Column(name = "annotation")
    private String annotation;

    @Column(name = "image_link")
    private String imageLink;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    List<Book> books = new ArrayList<Book>();


    public Author() {

    }

    public Author(String name, String surname, String country,
                  Date birthday, Date dateDeath, String annotation, String picturePath)
    {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.birthday = birthday;
        this.dateDeath = dateDeath;
        this.annotation = annotation;
        this.imageLink = picturePath;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getDateDeath() {
        return dateDeath;
    }

    public String getCountry() {
        return country;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getImageLink() {
        return imageLink;
    }

    public List<Book> getBooks() {
        return books;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setDateDeath(Date dateDeath) {
        this.dateDeath = dateDeath;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }

}
