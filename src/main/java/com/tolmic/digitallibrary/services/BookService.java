package com.tolmic.digitallibrary.services;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.BookDivision;
import com.tolmic.digitallibrary.entities.OriginalLanguage;
import com.tolmic.digitallibrary.entities.User;
import com.tolmic.digitallibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    private void saveMain(Book book) {
        bookRepository.save(book);
    }

    public Book saveAndGet(String name, String yearCreation1, String yearCreation2,
                     String genre, String annotation, OriginalLanguage originalLanguage)
    {

        Book book = new Book(name,
                            Long.parseLong(yearCreation1),
                            !yearCreation2.equals("") ? Long.parseLong(yearCreation2) : null,
                            genre,
                            !annotation.equals("") ? annotation : null,
                            originalLanguage
        );

        saveMain(book);

        return book;
    }

    public void updateById(Long needsBookId, String name, String yearCreation1, String yearCreation2,
                           String genre, String annotation, OriginalLanguage originalLanguage)
    {
        Book book = findByIdMain(needsBookId);

        book.setName(name);
        book.setYearCreation1(Long.parseLong(yearCreation1));
        book.setYearCreation2(!yearCreation2.equals("") ? Long.parseLong(yearCreation2) : null);
        book.setOriginalLanguage(originalLanguage);
        book.setGenre(genre);
        book.setAnnotation(!annotation.equals("") ? annotation : null);

        saveMain(book);
    }

    public Iterable<Book> findByManyArguments(String name, String year1, String year2,
                                              String genre, String language, String author)
    {

        return bookRepository.findByMany(name,
                year1 != null ? Long.parseLong(year1) : null,
                year2 != null ? Long.parseLong(year2) : null,
                genre != null ? "%" + genre + "%" : null,
                language,
                author
        );

    }

    public void putDivisionToBook(List<String> filePathes, Book book) {
        
        

        saveMain(book);
    }

    public List<Object[]> getBookStatistics() {
        return bookRepository.getBookStatistics();
    }

    public void deleteById(Long id) {

        bookRepository.deleteById(id);

    }

    public List<String> findGenres() {
        return bookRepository.findGenres();
    }

    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    private Book findByIdMain(Long id) {
        return bookRepository.findById(id).stream().toList().get(0);
    }

    public Book findById(Long id) {
        return findByIdMain(id);
    }

    public void addLike(Book book, User user) {
        book.setUserFan(user);
        saveMain(book);
    }

}
