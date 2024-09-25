package com.tolmic.digitallibrary.rest;

import java.util.Optional;

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

import com.tolmic.digitallibrary.entities.Author;
import com.tolmic.digitallibrary.repositories.AuthorRepository;


@RestController
@RequestMapping(path = "/api/authors", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class AythorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Author> getBook(@RequestParam("id") Long id) {
        Optional<Author> author = authorRepository.findById(id);

        if (author.isPresent()) {
            return new ResponseEntity<>(author.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void postAuthor(@RequestBody Author author) {
        authorRepository.save(author);
    }

    @PutMapping(path = "/{authorId}", consumes = "application/json")
    public void putAuthor(@RequestBody Author author) {
        authorRepository.save(author);
    }

}
