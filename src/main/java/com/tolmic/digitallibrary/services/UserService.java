package com.tolmic.digitallibrary.services;

import com.tolmic.digitallibrary.entities.Role;
import com.tolmic.digitallibrary.entities.User;
import com.tolmic.digitallibrary.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;


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
}
