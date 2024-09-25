package com.tolmic.digitallibrary.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class CommentText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_text_id")
    private Long id;

    @Column(name = "text")
    private String text;

    public CommentText(String text) {
        this.text = text;
    }
}
