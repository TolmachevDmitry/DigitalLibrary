package com.tolmic.digitallibrary.repositories;

import com.tolmic.digitallibrary.entities.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);

    User findByLogin(String login);
}
