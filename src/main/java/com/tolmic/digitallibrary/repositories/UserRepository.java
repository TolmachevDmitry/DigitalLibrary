package com.tolmic.digitallibrary.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tolmic.digitallibrary.entities.User;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);

    User findByLogin(String login);

    @Query(value = "CALL get_statistics_on_city();", nativeQuery = true)
    List<Object[]> getCityStatistics(); 
}
