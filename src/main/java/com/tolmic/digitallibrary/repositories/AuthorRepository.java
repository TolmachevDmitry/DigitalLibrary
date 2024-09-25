package com.tolmic.digitallibrary.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tolmic.digitallibrary.entities.Author;


public interface AuthorRepository extends CrudRepository<Author, Long> {
    Author findByName(String name);

    @Query("SELECT a " +
            "FROM Author a " +
            "WHERE (:name is null or a.name like :name) and (:surname is null or a.surname like :surname) and " +
            "(:country is null or a.country like :country)")
    List<Author> findByMany(String name, String surname, String country, Pageable pageable);

    @Query(value = "CALL get_statistics_on_authors();", nativeQuery = true)
    List<Object[]> getAuthorStatistics();

    @Query("select distinct a.country from Author a")
    List<String> findCountries();

}
