package com.tolmic.digitallibrary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.repositories.BookRepository;


@RestController
@RequestMapping(path = "/api/books", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class BookContorller {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/{name}")
    public ResponseEntity<Book> getBook(@RequestParam("name") String name) {
        Book book = bookRepository.findByName(name);

        if (book == null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void postBook(@RequestBody Book book) {
        bookRepository.save(book);
    }

    @PutMapping(path = "/{bookId}", consumes = "application/json")
    public void putBook(@RequestBody Book book) {
        bookRepository.save(book);
    }
    
}
