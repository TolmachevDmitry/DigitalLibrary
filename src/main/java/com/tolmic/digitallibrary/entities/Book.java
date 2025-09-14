package com.tolmic.digitallibrary.entities;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
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
    @JsonBackReference
    private List<Author> authors = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "book_id")
    @JsonManagedReference
    private List<BookDivision> bookDivisions = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "book_id")
    private List<StarGrade> starGrades = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "book_id")
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();
    

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


    public String getOriginalLanguageName() {
        return getOriginalLanguage().getName();
    }

    @JsonIgnore
    public int getAuthorCount() {
        return authors.size();
    }

    @JsonIgnore
    public int getCommentCount() {
        return comments.size();
    }

    public void addBookDivision(BookDivision bookDivision) {
        bookDivisions.add(bookDivision);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeCommentById(Long commentId) {
        this.comments.removeIf(comment -> comment.getId().equals(commentId));
    }

}
