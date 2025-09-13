package com.tolmic.digitallibrary.services;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.User;

public interface IUserService {

    public Double getUserGrade(User user, Book book);

    public User findByLogin(String login);

    public void deleteBookById(Long bookId);

    public boolean activateUser(String code);

}
