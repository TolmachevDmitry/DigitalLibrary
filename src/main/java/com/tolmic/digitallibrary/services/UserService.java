package com.tolmic.digitallibrary.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.Role;
import com.tolmic.digitallibrary.entities.StarGrade;
import com.tolmic.digitallibrary.entities.User;
import com.tolmic.digitallibrary.repositories.UserRepository;


@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public boolean createUser(User user) {

        if (userRepository.findByLogin(user.getUsername()) != null) {
            return false;
        }

        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDateRegistration(java.sql.Date.valueOf(formatForDateNow.format(new Date())));
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);

        return true;
    }

    public User findById(Long id) {
        return userRepository.findById(id).stream().toList().get(0);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public void deleteBookById(Long bookId) {
        for (User user : userRepository.findAll()) {
            user.removeMarkByBookID(bookId);
            save(user);
        }
    }

    public Double getUserGrade(User user, Book book) {

        for (StarGrade starGrade : user.getStarGrades()) {
            if (starGrade.getPk().getBook().getId().equals(book.getId())) {
                return starGrade.getNumberStars();
            }
        }

        return null;
    }
    
}
