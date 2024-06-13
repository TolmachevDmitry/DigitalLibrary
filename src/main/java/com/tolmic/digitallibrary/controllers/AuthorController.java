package com.tolmic.digitallibrary.controllers;

import com.tolmic.digitallibrary.entities.Author;
import com.tolmic.digitallibrary.services.AuthorService;
import com.tolmic.digitallibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.mail.Multipart;
import java.io.File;
import java.io.IOException;
import java.sql.Date;


@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @GetMapping("/authors/author")
    public String author(
            @RequestParam(name = "id", required = false) Long id,
            Model model)
    {

        int i = 0;

        Author author = authorService.findById(id);

        model.addAttribute("author", author);
        model.addAttribute("books", author.getBooks());

        return "author";
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public String authors(  @RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "surname", required = false) String surname,
                            @RequestParam(name = "country", required = false) String country,
//                            @RequestParam(name = "year1", required = false) String year1,
//                            @RequestParam(name = "year2", required = false) String year2,
                            Model model
    ) throws IOException
    {

        Iterable<Author> authors = authorService.findByManyAttribute(name, surname, country, null, null);

        Iterable<String> countries = authorService.findCountries();

        model.addAttribute("authors", authors);

        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        model.addAttribute("country", country);
//        model.addAttribute("year1", year1);
//        model.addAttribute("year2", year2);

        model.addAttribute("countries", countries);

        return "authors";
    }

    @GetMapping("/author_creation")
    public String authorCreation(Model model) {

        model.addAttribute("isCreation", true);

        return "author_creation";
    }

    @PostMapping("/author/create")
    public String createAuthor(@RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String country,
                               @RequestParam Date birthday,
                               @RequestParam Date dateDeath,
                               @RequestParam String annotation,
                               @RequestParam MultipartFile file)
    {

        authorService.save(name, surname, country, birthday, dateDeath,
                annotation, file.getName());

        return "redirect:/authors";
    }

    @PostMapping("/author/delete")
    public String authorDelete(@RequestParam Long id) {

        authorService.deleteById(id);

        return "redirect:/authors";
    }

    @GetMapping("/author_update")
    public String authorUpdate(
            @RequestParam(name = "id", required = false, defaultValue = "World") Long id,
            Model model)
    {

        Author author = authorService.findById(id);

        model.addAttribute("currData", author);
        model.addAttribute("isCreation", false);

        return "author_creation";
    }

    @PostMapping("/author/update")
    public String authorUpdate(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam Date birthday,
            @RequestParam Date dateDeath,
            @RequestParam String country,
            @RequestParam String annotation,
            @RequestParam Long id,
            @RequestParam File file)
    {

        authorService.updateById(id, name, surname, country, birthday, dateDeath,
                annotation, file.getName());

        return "redirect:/authors/author?id=" + id;
    }

    @PostMapping("/add_book")
    public String addBook(@RequestParam Long authId,
                          @RequestParam Long bookId)
    {

        Author author = authorService.findById(authId);
        author.addBook(bookService.findById(bookId));
        authorService.save(author);

        return "redirect:/authors/author?id=" + authId;
    }

}
