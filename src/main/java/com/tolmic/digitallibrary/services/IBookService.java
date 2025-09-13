package com.tolmic.digitallibrary.services;

import java.util.List;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.User;

public interface IBookService {

    public void deleteComment(Book book, Long commentId);

    public List<String> findGenres();

    public void addGrade(Book book, User user, Double numberGrade);

    public double countAverageRating(Book book);

}
