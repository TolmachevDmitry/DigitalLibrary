package com.tolmic.digitallibrary.entities.embeddable;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
