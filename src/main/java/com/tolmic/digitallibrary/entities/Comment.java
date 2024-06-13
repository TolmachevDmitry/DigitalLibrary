package com.tolmic.digitallibrary.entities;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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


    public Comment() {

    }

    public Comment(User user, Book book, CommentText commentText) {
        this.user = user;
        this.book = book;
        this.commentText = commentText;
    }


    public String getCommentText() {
        return commentText.getText();
    }

    public void setCommentText(String text) {
        commentText.setText(text);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
