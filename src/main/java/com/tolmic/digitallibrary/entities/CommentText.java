package com.tolmic.digitallibrary.entities;


import javax.persistence.*;


@Entity
@Table(name = "comment_text")
public class CommentText {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_text_id")
    private Long id;

    @Column(name = "text")
    private String text;


    public CommentText() {

    }

    public CommentText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
