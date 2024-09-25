package com.tolmic.digitallibrary.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tolmic.digitallibrary.entities.Author;
import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.services.AuthorService;
import com.tolmic.digitallibrary.services.BookDivisionService;
import com.tolmic.digitallibrary.services.BookService;


@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookDivisionService bookDivisionService;

    @Value("${sample.page-size}")
    private int pageSize;

    @GetMapping("/authors/author")
    public String author(
            @RequestParam(name = "id", required = false) Long id,
            Model model)
    {

        Author author = authorService.findById(id);

        model.addAttribute("author", author);
        model.addAttribute("books", author.getBooks());

        return "author";
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public String authors(  @RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "surname", required = false) String surname,
                            @RequestParam(name = "country", required = false) String country,
                            @RequestParam(name = "page", required = false) Integer page,
                            Model model) throws IOException
    {

        if (page == null) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        List<Author> authors = authorService.findByManyAttribute(name, surname, country, pageable);

        Iterable<String> countries = authorService.findCountries();

        model.addAttribute("authors", authors);

        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        model.addAttribute("country", country);

        model.addAttribute("countries", countries);

        model.addAttribute("countPages", Math.ceil(authorService.count() / authors.size()));
        model.addAttribute("page", page);

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
        Book book = bookService.findById(bookId);

        String authors = bookService.getAuthorsStringWithNewAuthor(book, author);

        bookDivisionService.changeDivisions(book.getBookDivisions(), 
                            authors, book.getName());

        author.addBook(book);
        authorService.save(author);

        return "redirect:/authors/author?id=" + authId;
    }

}
