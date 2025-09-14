package com.tolmic.digitallibrary.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({ "fullName", "imageLink" })
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
    @JsonManagedReference
    List<Book> books = new ArrayList<Book>();

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

    public String getFullName() {
        return getName() + " " + getSurname();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }

}
