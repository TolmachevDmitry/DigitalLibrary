package com.tolmic.digitallibrary.services;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.BookDivision;
import com.tolmic.digitallibrary.repositories.BookDivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookDivisionService {

    @Autowired
    private BookDivisionRepository bookDivisionRepository;

    private Iterable<BookDivision> currBookDivisions = null;

    public BookDivision findById(Long id) {
        return bookDivisionRepository.findById(id).stream().toList().get(0);
    }

    public void setCurrBookDivisions(Book book) {
        this.currBookDivisions = book.getBookDivisions();
    }

    public Iterable<BookDivision> getCurrBookDivisions() {
        return currBookDivisions;
    }

    public void deleteByBookId(Long id) {
        bookDivisionRepository.deleteByBookId(id);
    }

    private BookDivision getBookDivisionByIdMain(Long id) {

        if (currBookDivisions == null) {
            return null;
        }

        for (BookDivision bd : currBookDivisions) {
            if (bd.getId().equals(id)) {
                return bd;
            }
        }

        return null;
    }

    private BookDivision getDivisionByAll(Integer numberChapter, Integer numberPart, Integer numberValue) {

        if (currBookDivisions == null) {
            return null;
        }

        for (BookDivision bd : currBookDivisions) {

            if (bd.getNumberChapter().equals(numberChapter) &&
                    (bd.getNumberPart() != null ? bd.getNumberPart().equals(numberPart) : (numberPart == null)) &&
                    (bd.getNumberValue() != null ? bd.getNumberValue().equals(numberValue) : (numberValue == null)))
            {
                return bd;
            }
        }

        return null;
    }

    /**
     *
     * @param numberPart number of part
     * @param numberValue number of value
     * @param segment - number of segment, 1 - search min/max chapter in part,
     * 2 - search min/max part and chapter in value, other - return null
     * @return BookDivision
     */
    private BookDivision getLastDivisionInSegment(Integer numberPart,
                                                  Integer numberValue,
                                                  int segment,
                                                  boolean max)
    {

        if (currBookDivisions == null || (segment != 1 && segment != 2)) {
            return null;
        }

        BookDivision bookDivision = null;

        Integer controlNumberChapter = max ? 0 : Integer.MAX_VALUE;
        Integer controlNumberPart    = max ? 0 : Integer.MAX_VALUE;

        for (BookDivision bd : currBookDivisions) {

            if (segment == 1 && bd.getNumberChapter() != null && (max ? bd.getNumberChapter() > controlNumberChapter
                    : bd.getNumberChapter() < controlNumberChapter) && (bd.getNumberPart() != null
                    ? bd.getNumberPart().equals(numberPart) : numberPart == null)
                    && (bd.getNumberValue() != null ? bd.getNumberValue().equals(numberValue) : (numberValue == null)))
            {
                controlNumberChapter = bd.getNumberChapter();

                bookDivision = bd;
            }
            if (segment == 2 && bd.getNumberPart() != null && (max ? bd.getNumberPart() >= controlNumberPart
                    :  bd.getNumberPart() <= controlNumberPart) && bd.getNumberChapter() != null
                    && (max ? bd.getNumberChapter() >= controlNumberChapter : bd.getNumberChapter()
                    <= controlNumberChapter) && (bd.getNumberValue() != null ? bd.getNumberValue().equals(numberValue)
                    : numberValue == null))
            {
                controlNumberChapter = bd.getNumberChapter();
                controlNumberPart = bd.getNumberPart();

                bookDivision = bd;
            }

        }

        return bookDivision;
    }

    public BookDivision getBookDivisionById(Long id) {
        return getBookDivisionByIdMain(id);
    }

    public boolean checkValidationOfDivisionId(Long id) {
        return currBookDivisions != null && getBookDivisionById(id) != null;
    }

    public void findOtherBookDivisionsByOne(Long id) {
        BookDivision bd = bookDivisionRepository.findById(id).stream().toList().get(0);

        currBookDivisions = bookDivisionRepository.findByBookId(bd.getBook().getId());
    }

    public BookDivision getNeighboringDivision(BookDivision bookDivision, boolean forward) {

        Integer currNumberChapter = bookDivision.getNumberChapter();
        Integer currNumberPart = bookDivision.getNumberPart();
        Integer currNumberValue = bookDivision.getNumberValue();

        BookDivision bd = getDivisionByAll(currNumberChapter + ((forward) ? (1) : (-1)), currNumberPart,
                currNumberValue
        );

        if (bd == null && currNumberPart != null) {
            bd = getLastDivisionInSegment( currNumberPart + ((forward) ? (1) : (-1)),
                    currNumberValue, 1, !forward
            );
        }

        if (bd == null && currNumberValue != null) {
            bd = getLastDivisionInSegment(0, currNumberValue + ((forward) ? (1) : (-1)),
                    2, !forward);
        }

        return bd;
    }

    public BookDivision getNextDivision(BookDivision bookDivision) {
        return getNeighboringDivision(bookDivision, true);
    }

    public BookDivision getPrevDivision(BookDivision bookDivision) {
        return getNeighboringDivision(bookDivision, false);
    }

}
