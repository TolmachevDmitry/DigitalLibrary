package com.tolmic.digitallibrary.repositories;

import com.tolmic.digitallibrary.entities.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AuthorRepository extends CrudRepository<Author, Long> {
    Author findByName(String name);

    @Query("SELECT a " +
            "FROM Author a " +
            "WHERE (:name is null or a.name like :name) and (:surname is null or a.surname = :surname) and " +
            "(:country is null or a.country like :country)")
    List<Author> findByMany(String name, String surname, String country);

    @Query(value = "call get_statistics_on_authors();", nativeQuery = true)
    List<Object[]> getAuthorStatistics();

    @Query("select distinct a.country from Author a")
    List<String> findCountries();

}
