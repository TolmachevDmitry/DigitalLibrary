package com.tolmic.digitallibrary.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tolmic.digitallibrary.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    User findByLogin(String login);

    @Query(value = "CALL get_statistics_on_city();", nativeQuery = true)
    List<Object[]> getCityStatistics(); 

    User findByActivationCode(String code);
}
