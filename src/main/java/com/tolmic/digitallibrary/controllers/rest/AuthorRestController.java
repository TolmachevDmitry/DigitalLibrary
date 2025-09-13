package com.tolmic.digitallibrary.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tolmic.digitallibrary.entities.Author;
import com.tolmic.digitallibrary.services.implementations.AuthorService;


@RestController
@RequestMapping(path = "/api/authors", produces = "application/json",
                method = {RequestMethod.GET, RequestMethod.POST})
@CrossOrigin(origins = "*")
public class AuthorRestController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/author")
    public ResponseEntity<Author> getAuthor(@RequestParam("id") Long id) {
        Author author = authorService.findById(id);

        if (author == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(author);
    }

    // @PostMapping("/{name}")
    // @ResponseStatus(HttpStatus.CREATED)
    // public void postAuthor(@RequestBody Author author) {
    //     authorRepository.save(author);
    // }

    // @PutMapping(path = "/{authorId}", consumes = "application/json")
    // public void putAuthor(@RequestBody Author author) {
    //     authorRepository.save(author);
    // }

}
