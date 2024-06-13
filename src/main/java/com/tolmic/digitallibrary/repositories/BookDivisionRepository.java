package com.tolmic.digitallibrary.repositories;


import com.tolmic.digitallibrary.entities.BookDivision;
import org.springframework.data.repository.CrudRepository;


public interface BookDivisionRepository extends CrudRepository<BookDivision, Long> {

    Iterable<BookDivision> findByBookId(Long bookId);

    void deleteByBookId(Long id);
}
