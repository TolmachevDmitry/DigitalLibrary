package com.tolmic.digitallibrary;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tolmic.digitallibrary.entities.Author;
import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.BookDivision;
import com.tolmic.digitallibrary.repositories.BookRepository;
import com.tolmic.digitallibrary.services.BookDivisionService;
import com.tolmic.digitallibrary.services.BookService;

@SpringBootTest
class BookTests {

	@Autowired
    BookRepository bookRepository;

	@Autowired
    private BookService bookService;

	@Autowired
	private BookDivisionService bookDivisionService;

	private HelperMethods helperMethods = new HelperMethods();

	private Pageable pageable = PageRequest.of(0, 1);

	@Test
	void findBookByLanguage() {
		String languageName = "русский";

		Pageable pageable = PageRequest.of(0, 1);

		Iterable<Book> books2 = bookService.findByManyArguments("", "", 
								null, null, languageName, null, pageable);

		for (Book b : books2) {
			assertEquals(b.getOriginalLanguage(), languageName);
		}
	}

	@Test
	void findBooksByCreationYear() {
		int creationYear1 = 1900;

		int creationYear2 = 1850;
		int creationYear3 = 1900;

		Iterable<Book> books1 = bookService.findByManyArguments("", 
					creationYear1 + "", null, null, "", null, pageable);

		Iterable<Book> books2 = bookService.findByManyArguments("", 
									creationYear2 + "", creationYear3 + "",
									null, "", null, pageable);

		for (Book b : books1) {
			assertTrue(helperMethods.moreOrEqual(b.getYearCreation1().intValue(), 
												 creationYear1));
		}

		for (Book b : books2) {
			assertTrue(helperMethods.moreOrEqual(b.getYearCreation1().intValue(), 
												 creationYear2));

			if (b.getYearCreation2() == null) {
				assertTrue(helperMethods.lessOrEqual(b.getYearCreation1().intValue(), 
												 	 creationYear3));
			} else {
				assertTrue(helperMethods.lessOrEqual(b.getYearCreation2().intValue(), 
												 	 creationYear3));
			}
		}

	}

	@Test
	void getSortedDivisions() {
		List<BookDivision> divisions = new ArrayList<>();

		divisions.add(new BookDivision(0, 1, 1, "", "", ""));
		divisions.add(new BookDivision(0, 1, 2, "", "", ""));
		divisions.add(new BookDivision(0, 2, 1, "", "", ""));
		divisions.add(new BookDivision(0, 2, 2, "", "", ""));

		int n = divisions.size();

		List<BookDivision> sortedDivisions = bookDivisionService.getSortedDivisions(divisions);

		int[] chapters = new int[n];
		int[] parts = new int[n];
		for (int i = 0; i < sortedDivisions.size(); i++) {
			BookDivision d = sortedDivisions.get(i);

			chapters[i] = d.getNumberChapter();
			parts[i] = d.getNumberPart();
		}
		
		assertArrayEquals(new int[] {1, 2, 1, 2}, chapters);
		assertArrayEquals(new int[] {1, 1, 2, 2}, parts);
	}

	@Test
	void toAuthorsString() {
		Book book = new Book();
		book.getAuthors().add(new Author("Алексей", "Жвачкин", "", null, null, "", ""));
		book.getAuthors().add(new Author("Василий", "Ямщиков", "", null, null, "", ""));

		String authors = bookService.getAuthorsString(book);

		assertEquals(authors, "Алексей Жвачкин, Василий Ямщиков");
	}

}
