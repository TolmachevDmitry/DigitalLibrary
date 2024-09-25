package com.tolmic.digitallibrary.services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tolmic.digitallibrary.entities.Author;
import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.OriginalLanguage;
import com.tolmic.digitallibrary.entities.StarGrade;
import com.tolmic.digitallibrary.entities.User;
import com.tolmic.digitallibrary.entities.embeddable.StarGradePK;
import com.tolmic.digitallibrary.repositories.BookRepository;
import com.tolmic.digitallibrary.repositories.StarGradeRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private StarGradeRepository starGradeRepository;

    private void saveMain(Book book) {
        bookRepository.save(book);
    }

    public String getAuthorsString(Book book) {
        return book.getAuthors().stream()
                                .map(a -> a.getFullName())
                                .collect(Collectors.joining(", "));
    }

    public String getAuthorsStringWithNewAuthor(Book book, Author author) {
        String authors = getAuthorsString(book);
        authors += (authors.equals("") ? "" : ", ") + author.getFullName();

        return authors;
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

    public void save(Book book) {
        saveMain(book);
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

    public boolean checkValidityParam(String param) {
        return !(param == null || param == "");
    }

    public List<Book> findByManyArguments(String name, String year1, String year2,
                                              String genre, String language, String author,
                                              Pageable pageable)
    {
        return bookRepository.findByMany(
                checkValidityParam(name)        ? "%" + name + "%"        : null,
                checkValidityParam(year1)       ? Long.parseLong(year1)   : null,
                checkValidityParam(year2)       ? Long.parseLong(year2)   : null,
                checkValidityParam(genre)       ? "%" + genre + "%"       : null,
                checkValidityParam(language)    ? language                : null,
                checkValidityParam(author)      ? "%" + author + "%"      : null,
                pageable
        );
    }

    public List<Object[]> getBookStatistics() {
        return bookRepository.getBookStatistics();
    }

    public Map<String, Integer> getGenresStatistics(int countPositions) {
        Map<String, Integer> genreMap = new HashMap<>();

        List<String> genresLines = bookRepository.findGenres();

        TreeSet<String> genresSet = new TreeSet<>();

        for (String genresLine : genresLines) {
            for (String genre : genresLine.split(", ")) {
                genreMap.put(genre, genreMap.containsKey(genre) ? genreMap.get(genre) + 1 : 1);
                genresSet.add(genre);
            }
        }

        Collection<Integer> genresCounts = genreMap.values();

        return genreMap;
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

    public void deleteComment(Book book, Long commentId) {
        
        book.removeCommentById(commentId);

        saveMain(book);

    }

    public void addGrade(Book book, User user, Double numberGrade) {

        StarGrade starGrade = new StarGrade();
        starGrade.setNumberStars(numberGrade);

        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        starGrade.setDate(java.sql.Date.valueOf(formatForDateNow.format(new Date())));
        
        StarGradePK starGradePK = new StarGradePK();
        starGradePK.setBook(book);
        starGradePK.setUser(user);

        starGrade.setPk(starGradePK);

        starGradeRepository.save(starGrade);
    }

    public double countAverageRating(Book book) {
        List<StarGrade> starGrades = book.getStarGrades();

        double sum = 0;
        int count = starGrades.size();

        if (count == 0) {
            return 0;
        }

        for (StarGrade grade : starGrades) {
            sum += grade.getNumberStars();
        }

        return sum / count;
    }

    public int count() {
        Long count = bookRepository.count();
        return count.intValue();
    }

}
