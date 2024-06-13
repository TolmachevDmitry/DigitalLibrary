package com.tolmic.digitallibrary.controllers;


import com.tolmic.digitallibrary.FileWorking.FileRecorder;
import com.tolmic.digitallibrary.entities.*;
import com.tolmic.digitallibrary.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookDivisionService bookDivisionService;

    @Autowired
    private OriginalLanguageService originalLanguageService;

    @Autowired
    private UserService userService;

    @Value("${temp.path}")
    private String tempPath;

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String books(
            @RequestParam(name = "authId", required = false) Long authId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "genre", required = false) String genre,
            @RequestParam(name = "year1", required = false) String year1,
            @RequestParam(name = "year2", required = false) String year2,
            @RequestParam(name = "author", required = false) String author,
            Model model
    )
    {

        Iterable<Book> books = bookService.findByManyArguments(name, year1, year2, genre, language, author);
        Iterable<OriginalLanguage> languages = originalLanguageService.findAll();

        Iterable<Author> authors = authorService.findAll();

        Iterable<String> genres = bookService.findGenres();

        model.addAttribute("books", books);
        model.addAttribute("forAuthor", authId != null);
        model.addAttribute("authId", authId);

        model.addAttribute("author", author);
        model.addAttribute("name", name);
        model.addAttribute("language", language);
        model.addAttribute("genre", genre);
        model.addAttribute("year1", year1);
        model.addAttribute("year2", year2);

        model.addAttribute("languages", languages);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "books";
    }

    @RequestMapping(value = "/books/book", method = RequestMethod.GET)
    public String book(
            @RequestParam(name = "id") Long id,
            Model model,
            Principal principal)
    {

        Book book = bookService.findById(id);

        bookDivisionService.setCurrBookDivisions(book);

        User user = principal != null ? userService.findByLogin(principal.getName()) : null;

        model.addAttribute("book", book);
        model.addAttribute("divisions", bookDivisionService.getCurrBookDivisions());
        model.addAttribute("user", user);
        model.addAttribute("existsLike", principal == null || user.existsLike(book.getId()));

        return "book";
    }

    @GetMapping("/book_creation")
    public String createBook(
            @RequestParam(name = "auth", required = false) Long authorId,
            Model model)
    {

        Iterable<OriginalLanguage> languages = originalLanguageService.findAll();

        model.addAttribute("isCreation", true);
        model.addAttribute("authorId", (authorId != null ? authorId : ""));
        model.addAttribute("forAuthor", (authorId != null));
        model.addAttribute("languages", languages);

        return "book_creation";
    }

    @PostMapping("/book/add_like")
    public void addLike(@RequestParam Long bookId, Principal principal) {
        Book book = bookService.findById(bookId);
        User user = userService.findByLogin(principal.getName());

        bookService.addLike(book, user);
    }

    @PostMapping(value = "/book/create")
    public String bookCreate(
            @RequestParam String name,
            @RequestParam String yearCreation1,
            @RequestParam String yearCreation2,
            @RequestParam String originalLanguage,
            @RequestParam String genre,
            @RequestParam String annotation,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) Long authorId) throws IOException
    {

        OriginalLanguage language = originalLanguageService.findByName(originalLanguage);

        if (language == null) {
            language = originalLanguageService.saveAndGet(originalLanguage);
        }

        Book newBook = bookService.saveAndGet(name, yearCreation1, yearCreation2, genre, annotation, language);

        if (file != null) {
            List<String> filePathes = new FileRecorder().recordBook(file);
            bookService.putDivisionToBook(filePathes, newBook);
        }

        if (authorId != null) {
            authorService.addBookForAuthor(authorId, newBook);

            return "redirect:/authors/author?id=" + authorId;
        }

        return "redirect:/books";
    }

    @RequestMapping(value = "/division", method = RequestMethod.GET)
    public String division(
            @RequestParam(name = "id", required = false, defaultValue = "World") Long id,
            Model model, Principal principal)
    {

        if (!bookDivisionService.checkValidationOfDivisionId(id)) {
            bookDivisionService.findOtherBookDivisionsByOne(id);
        }

        BookDivision bookDivision = bookDivisionService.getBookDivisionById(id);

        List<String> text = (new BookFile()).readText(bookDivision.getFileLink());

        model.addAttribute("division", bookDivision);
        model.addAttribute("text", text);

        model.addAttribute("prevDivision", bookDivisionService.getPrevDivision(bookDivision));
        model.addAttribute("nextDivision", bookDivisionService.getNextDivision(bookDivision));

        model.addAttribute("exists", principal != null &&
                userService.findByLogin(principal.getName()).existsMark(bookDivision.getId()));

        return "division";
    }

    @GetMapping("/book_update")
    public String bookUpdate(
            @RequestParam(name = "id") Long id,
            Model model)
    {

        Book currBook = bookService.findById(id);

        Iterable<OriginalLanguage> languages = originalLanguageService.findAll();

        model.addAttribute("isCreation", false);
        model.addAttribute("forAuthor", false);
        model.addAttribute("currData", currBook);
        model.addAttribute("languages", languages);

        return "book_creation";
    }

    @PostMapping("/book/update")
    public String bookUpdate(
            @RequestParam String name,
            @RequestParam String yearCreation1,
            @RequestParam String yearCreation2,
            @RequestParam String originalLanguage,
            @RequestParam String genre,
            @RequestParam String annotation,
            @RequestParam Long id)
    {
        // Обобщить
        OriginalLanguage language = originalLanguageService.findByName(originalLanguage);

        if (language == null) {
            language = originalLanguageService.saveAndGet(originalLanguage);
        }

        bookService.updateById(id, name, yearCreation1, yearCreation2, genre,
                annotation, language);

        return "redirect:/books";
    }

    @PostMapping("/book/delete")
    public String bookDelete(@RequestParam Long bookId,
                             @RequestParam (required = false) Long authorId)
    {

        if (authorId == null) {

            Iterable<User> users = userService.findAll();
            for (User u : users) {
                u.removeMarkByBookID(bookId);
                userService.save(u);
            }

            bookDivisionService.deleteByBookId(bookId);

            authorService.removeBookFromAuthor(bookService.findById(bookId));
            bookService.deleteById(bookId);
        }
        else {
            authorService.removeBookFromAuthorById(authorId, bookId);

            return "redirect:/authors/author?id=" + authorId;
        }

        return "redirect:/books";
    }

}
