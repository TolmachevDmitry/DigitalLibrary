package com.tolmic.digitallibrary.entities.embeddable;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.User;

import lombok.Data;

@Data
@Embeddable
public class StarGradePK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}
