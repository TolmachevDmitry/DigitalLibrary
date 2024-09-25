package com.tolmic.digitallibrary.controllers;


import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tolmic.digitallibrary.entities.Author;
import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.BookDivision;
import com.tolmic.digitallibrary.entities.Comment;
import com.tolmic.digitallibrary.entities.OriginalLanguage;
import com.tolmic.digitallibrary.entities.User;
import com.tolmic.digitallibrary.file_working.FileRecorder;
import com.tolmic.digitallibrary.services.AuthorService;
import com.tolmic.digitallibrary.services.BookDivisionService;
import com.tolmic.digitallibrary.services.BookService;
import com.tolmic.digitallibrary.services.CommentService;
import com.tolmic.digitallibrary.services.OriginalLanguageService;
import com.tolmic.digitallibrary.services.UserService;


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
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileRecorder fileRecorder;

    @Value("${sample.page-size}")
    private int pageSize;


    @GetMapping(value = "/books")
    public String books(
            @RequestParam(name = "authId", required = false) Long authId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "genre", required = false) String genre,
            @RequestParam(name = "year1", required = false) String year1,
            @RequestParam(name = "year2", required = false) String year2,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "page", required = false) Integer page,
            Model model) 
    {

        if (page == null) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        List<Book> books = bookService.findByManyArguments(name, year1, year2, 
                                    genre, language, author, pageable);
        Iterable<OriginalLanguage> languages = originalLanguageService.findAll();

        Iterable<Author> authors = authorService.findAll();

        Iterable<String> genres = bookService.findGenres();

        model.addAttribute("books", books);
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

        model.addAttribute("countPages", Math.ceil(bookService.count() / books.size()));
        model.addAttribute("page", page);

        return "books";
    }

    @GetMapping(value = "/books/book")
    public String book(
            @RequestParam(name = "id") Long id,
            Model model,
            Principal principal)
    {

        Book book = bookService.findById(id);

        User user = principal != null ? userService.findByLogin(principal.getName()) : null;

        List<BookDivision> sortedDivisions = bookDivisionService
                                                .getSortedDivisions(book.getBookDivisions());

        model.addAttribute("book", book);
        model.addAttribute("divisions", sortedDivisions);
        model.addAttribute("user", user);
        model.addAttribute("authors", bookService.getAuthorsString(book));
        model.addAttribute("authorsCount", book.getAuthorCount());
        model.addAttribute("indentsMap", bookDivisionService
                                                        .getIndents(sortedDivisions));
        model.addAttribute("averageGrade", bookService.countAverageRating(book));

        if (user != null) {
            model.addAttribute("userGrade", userService.getUserGrade(user, book));
        }

        return "book";
    }

    @PutMapping("/book_creation")
    public String createBook(
            @RequestParam(name = "auth", required = false) Long authorId,
            Model model)
    {

        Iterable<OriginalLanguage> languages = originalLanguageService.findAll();

        model.addAttribute("isCreation", true);
        model.addAttribute("authorId", authorId != null ? authorId : "");
        model.addAttribute("forAuthor", (authorId != null));
        model.addAttribute("languages", languages);

        return "book_creation";
    }

    @PostMapping("/add_comment")
    public String addComment(@RequestParam Long userId,
                             @RequestParam Long bookId, 
                             @RequestParam(required = false) Long commentId,
                             @RequestParam String text
    ) 
    {
        Book book = bookService.findById(bookId);
        User user = userService.findById(userId);

        Comment comment = commentService.createComment(book, user, text);

        if (commentId != null) {
            Comment otherComment = commentService.findById(commentId);
            commentService.addAnserToComment(otherComment, comment);
        }

        return "redirect:/books/book?id=" + bookId;
    }

    @DeleteMapping("/delete_comment")
    public ResponseEntity<String> deleteComment(@RequestParam Long bookId,
                                @RequestParam Long commentId
    ) 
    {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok().body("Comment was deleted");
    }

    @PostMapping("/add_grade")
    public ResponseEntity<String> addGrade(@RequestParam Long bookId,
                                           @RequestParam Long userId,
                                           @RequestParam Double numberGrade
    ) 
    {

        User user = userService.findById(userId);
        Book book = bookService.findById(bookId);

        bookService.addGrade(book, user, numberGrade);

        return ResponseEntity.ok().body("Grade was added");
    }

    @PostMapping(value = "/book/create")
    public String bookCreate(
            @RequestParam String name,
            @RequestParam Long yearCreation1,
            @RequestParam(required = false) Long yearCreation2,
            @RequestParam String originalLanguage,
            @RequestParam String genre,
            @RequestParam(required = false) String annotation,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) Long authorId) throws IOException
    {

        OriginalLanguage language = originalLanguageService.findByName(originalLanguage);

        if (language == null) {
            language = originalLanguageService.saveAndGet(originalLanguage);
        }

        Book newBook = new Book(name, yearCreation1, yearCreation2, 
                                                genre, annotation, language);

        if (authorId != null) {
            authorService.addBookForAuthor(authorId, newBook);
        }

        List<String> fileParams = fileRecorder.recordBook(file, name, null);
        List<BookDivision> bookDivisions = 
                bookDivisionService.createBookDivisionOnStrings(fileParams);

        bookService.save(newBook);

        bookDivisionService.addDivisionsToBook(bookDivisions, newBook);

        return "redirect:/books";
    }

    @GetMapping(value = "/division")
    public String getDivision(
            @RequestParam(name = "id", required = false, defaultValue = "World") Long id,
            Model model, Principal principal)
    {

        BookDivision bookDivision = bookDivisionService.findById(id);

        List<BookDivision> brotherDivisions = bookDivision.getBrotherDivisions();
        BookDivision prevDivision = bookDivisionService.getPrevDivision(bookDivision, brotherDivisions);
        BookDivision nextDivision = bookDivisionService.getNextDivision(bookDivision, brotherDivisions);

        List<String> text = bookDivisionService.getText(bookDivision);

        model.addAttribute("division", bookDivision);
        model.addAttribute("text", text);

        model.addAttribute("prevDivision", prevDivision);
        model.addAttribute("nextDivision", nextDivision);

        model.addAttribute("exists", principal != null &&
                userService.findByLogin(principal.getName()).existsMark(bookDivision.getId()));

        return "division";
    }

    @GetMapping("/book_update")
    public String bookUpdate(
            @RequestParam Long bookId,
            Model model)
    {

        Book currBook = bookService.findById(bookId);

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

    @DeleteMapping("/book/delete")
    public String bookDelete(@RequestParam Long bookId,
                             @RequestParam (required = false) Long authorId)
    {

        userService.deleteBookById(bookId);

        bookDivisionService.deleteByBook(bookService.findById(bookId));

        authorService.removeBook(bookService.findById(bookId));
        bookService.deleteById(bookId);

        if (authorId != null) {
            return "redirect:/authors/author?id=" + authorId;
        }

        return "redirect:/books";
    }

}
