package com.tolmic.digitallibrary.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "looked")
    private boolean looked;

    @OneToOne
    @JoinColumn(name = "comment_text_id")
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

    public Long getUserId() {
        return user.getId();
    }

    public void addAnswer(Comment comment) {
        answers.add(comment);
    }
}
