package com.tolmic.digitallibrary.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tolmic.digitallibrary.entities.Book;


public interface BookRepository extends CrudRepository<Book, Long> {
    Book findByName(String name);

    @Query("SELECT b " +
            "FROM Book b " +
            "WHERE (:name is null or b.name like :name) and (:year1 is null or b.yearCreation1 >= :year1)" +
            "and (:year2 is null or ((b.yearCreation2 is null and b.yearCreation1 <= :year2) or (b.yearCreation2 is not null and b.yearCreation2 <= :year2)))" +
            "and (:genre is null or b.genre like :genre)" +
            "and (:language is null or b.originalLanguage.name = :language)" +
            "and (:author is null or exists (select a from b.authors a " +
            "where concat(a.name, ' ', a.surname) like :author))")
    List<Book> findByMany(String name,
                          Long year1,
                          Long year2,
                          String genre,
                          String language,
                          String author,
                          Pageable pageable
    );

    @Query(value = "CALL get_statistics_on_books();", nativeQuery = true)
    List<Object[]> getBookStatistics();

    @Query("select distinct b.genre from Book b")
    List<String> findGenres();

}
