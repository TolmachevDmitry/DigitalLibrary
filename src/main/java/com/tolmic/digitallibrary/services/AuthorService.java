package com.tolmic.digitallibrary.services;

import com.tolmic.digitallibrary.entities.Author;
import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    private void saveMain(Author author) {
        authorRepository.save(author);
    }

    public void save(String name, String surname, String country,
                     Date birthday, Date dateDeath, String annotation, String imageName) {
        saveMain(new Author(name, surname, country, birthday, dateDeath,
                annotation, toImageLink(imageName)));
    }

    public void save(Author author) {
        saveMain(author);
    }

    public void updateById(Long needsAuthorId, String name, String surname, String country,
                           Date birthday, Date dateDeath, String annotation, String imageLinkPath) {

        Author author = findById(needsAuthorId);

        author.setName(name);
        author.setSurname(surname);
        author.setBirthday(birthday);
        author.setDateDeath(dateDeath);
        author.setCountry(country);
        author.setAnnotation(annotation);
        author.setImageLink(toImageLink(imageLinkPath));

        saveMain(author);

    }

    public void deleteById(Long id) {
        if (id > 12) {
            authorRepository.deleteById(id);
        }
    }

    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    private Author findByIdMain(Long id) {
        return authorRepository.findById(id).orElseThrow();
    }

    public Author findById(Long id) {
        return findByIdMain(id);
    }

    public Iterable<Author> findByManyAttribute(String name, String surname, String country,
                                                String year1, String year2)
    {

        return authorRepository.findByMany(name != null ? "%" + name + "%" : null,
                surname,
                country != null ? '%' + country + '%' : null
        );

    }

    public List<Object[]> getAuthorStatistics() {
        return authorRepository.getAuthorStatistics();
    }

    public void addBookForAuthor(Long authorId, Book book) {
        Author author = findByIdMain(authorId);

        author.addBook(book);

        saveMain(author);
    }

    public List<String> findCountries() {
        return authorRepository.findCountries();
    }

    public void removeBookFromAuthor(Book book) {

        for (Author author : book.getAuthors()) {
            author.removeBook(book.getId());

            saveMain(author);
        }

    }

    public void removeBookFromAuthorById(Long authorId, Long bookId) {
        Author author = findByIdMain(authorId);

        author.removeBook(bookId);

        saveMain(author);
    }

    public String toImageLink(String name) {
        return "D:\\ВГУ\\Проектирование_Баз_Данных\\Изображения_авторов\\" + name;
    }

}
