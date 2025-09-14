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
import jakarta.persistence.ManyToOne;
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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private Book book;

    @Column(name = "looked")
    private boolean looked;

    @OneToOne
    @JoinColumn(name = "comment_text_id")
    @JsonIgnore
    private CommentText commentText;

    @OneToMany
    @JoinColumn(name = "parent_id")
    private List<Comment> answers = new ArrayList<>();


    public Comment(CommentText commentText) {
        this.commentText = commentText;
    }

    public String getText() {
        return commentText.getText();
    }

    @JsonIgnore
    public Long getUserId() {
        return user.getId();
    }

    public void addAnswer(Comment comment) {
        answers.add(comment);
    }
}
