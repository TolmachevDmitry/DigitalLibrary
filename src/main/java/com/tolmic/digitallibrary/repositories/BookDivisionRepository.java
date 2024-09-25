package com.tolmic.digitallibrary.repositories;


import org.springframework.data.repository.CrudRepository;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.BookDivision;


public interface BookDivisionRepository extends CrudRepository<BookDivision, Long> {
    void deleteByBook(Book book);
}
