package com.tolmic.digitallibrary.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
    private List<Author> authors = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "book_id")
    private List<BookDivision> bookDivisions = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "book_id")
    private List<StarGrade> starGrades = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "book_id")
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

    public int getAuthorCount() {
        return authors.size();
    }

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
