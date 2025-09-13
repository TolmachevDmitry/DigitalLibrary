package com.tolmic.digitallibrary.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.services.implementations.BookService;


@RestController
@RequestMapping(path = "/api/books", produces = "application/json",
                method = {RequestMethod.GET, RequestMethod.POST})
@CrossOrigin(origins = "*")
public class BookRestContorller {

    @Autowired
    private BookService bookService;

    @GetMapping("/book/{name}")
    public ResponseEntity<Book> getBook(@RequestParam("name") String name) {
        Book book = bookService.findByName(name);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    @PostMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void postBook(@RequestBody Book book) {
        
    }

    @PutMapping(path = "/{bookId}", consumes = "application/json")
    public void putBook(@RequestBody Book book) {
        
    }
    
}
