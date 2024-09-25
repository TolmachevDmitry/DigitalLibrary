package com.tolmic.digitallibrary.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tolmic.digitallibrary.entities.Book;
import com.tolmic.digitallibrary.entities.BookDivision;
import com.tolmic.digitallibrary.file_working.FileReader;
import com.tolmic.digitallibrary.file_working.FileRecorder;
import com.tolmic.digitallibrary.file_working.HandleStringParams;
import com.tolmic.digitallibrary.repositories.BookDivisionRepository;


@Service
public class BookDivisionService {

    @Autowired
    private BookDivisionRepository bookDivisionRepository;

    @Autowired
    private HandleStringParams handleStringParams;

    @Autowired
    private FileRecorder fileRecorder;

    @Autowired
    private FileReader fileReader;


    private BookDivision findByIdMain(Long id) {
        return bookDivisionRepository.findById(id).stream().toList().get(0);
    }

    public BookDivision findById(Long id) {
        return findByIdMain(id);
    }

    public void deleteByBook(Book book) {
        bookDivisionRepository.deleteByBook(book);
    }

    private void save(BookDivision bookDivision) {
        bookDivisionRepository.save(bookDivision);
    }

    public void findOtherBookDivisionsByOne(Long id) {
        BookDivision bd = findByIdMain(id);
    }

    public List<String> getText(BookDivision bookDivision) {
        return fileReader.readText(bookDivision.getFileLink());
    }

    private boolean divisionEquals(BookDivision b1, BookDivision b2) {
        return b1.getId().equals(b2.getId());
    }

    private BookDivision getNeighbour(BookDivision division, List<BookDivision> divisionList, boolean isDesc) {
        BookDivision neighbour = null;

        for (BookDivision d : divisionList) {

            if (divisionEquals(d, division)) {
                continue;
            }

            // prev division
            if (isDesc && isLess(d, division) && (neighbour == null || !isLess(d, neighbour))) {
                neighbour = d;
            }

            // next division
            if (!isDesc && !isLess(d, division) && (neighbour == null || isLess(d, neighbour))) {
                neighbour = d;
            }
        }

        return neighbour;
    }

    public BookDivision getNextDivision(BookDivision division, List<BookDivision> divisionList) {
        return getNeighbour(division, divisionList, false);
    }

    public BookDivision getPrevDivision(BookDivision division, List<BookDivision> divisionList) {
        return getNeighbour(division, divisionList, true);
    }

    private boolean integersIsNull(Integer a, Integer b) {
        return a == null && b == null;
    }

    private boolean partsIsNull(BookDivision a, BookDivision b) {
        return integersIsNull(a.getNumberPart(), b.getNumberPart());
    }

    private boolean valuesIsNull(BookDivision a, BookDivision b) {
        return integersIsNull(a.getNumberValue(), b.getNumberValue());
    }

    private boolean isLess(BookDivision d1, BookDivision d2) {

        if (!valuesIsNull(d1, d2) && !d1.getNumberValue().equals(d2.getNumberValue())) {
            return d1.getNumberValue() < d2.getNumberValue();
        }

        if (!partsIsNull(d1, d2) && d1.getNumberPart().equals(d2.getNumberPart())) {
            return d1.getNumberPart() < d2.getNumberPart();
        }

        return d1.getNumberChapter() < d2.getNumberChapter();
    }

    public ArrayList<BookDivision> getSortedDivisions(List<BookDivision> bd) {
        ArrayList<BookDivision> divisions = new ArrayList<>(20);

        for (BookDivision d : bd) {

            int n = divisions.size();

            for (int i = 0; i < n; i++) {
                if (divisionEquals(d, divisions.get(i)) && isLess(d, divisions.get(i))) {
                    divisions.add(i, d);
                    break;
                }
            }

            if (n == divisions.size()) {
                divisions.add(d);
            }
        }

        return divisions;
    }

    public Map<Long, Integer> getIndents(List<BookDivision> divisions) {

        Map<Long, Integer> map = new HashMap<>();

        Integer currNumberValue = 0;
        Integer currNumberPart = 0;

        for (BookDivision d : divisions) {

            Long id = d.getId();
            Integer numberValue = d.getNumberValue();
            Integer numberPart = d.getNumberPart();

            if (numberValue != null && numberValue != currNumberValue) {
                map.put(id, 1);
                currNumberValue = numberValue;
            }

            if (numberPart != null && numberPart != currNumberPart) {
                Integer count = map.get(id);
                map.put(id, count == null ? 1 : count + 1);
                currNumberPart = numberPart;
            }
        }

        return map;
    }

    private Integer convertToInteger(String str) {
        return str != null ? Integer.valueOf(str) : null;
    }

    public List<BookDivision> createBookDivisionOnStrings(List<String> string) {
        List<Map<String, String>> maps = handleStringParams.splitOutStringsToParamMap(string);
        List<BookDivision> bookDivisions = new ArrayList<BookDivision>();

        for (Map<String, String> map : maps) {
            BookDivision bookDivision = new BookDivision();
            bookDivision.setFileLink(map.get("fileLink"));
            bookDivision.setChapterName(map.get("chapterName"));
            bookDivision.setPartName(map.get("partName"));

            bookDivision.setNumberChapter(convertToInteger(map.get("chapterNumber")));
            bookDivision.setNumberPart(convertToInteger(map.get("partNumber")));
            bookDivision.setNumberValue(convertToInteger(map.get("valueNumber")));

            bookDivisions.add(bookDivision);
        }

        return bookDivisions;
    }

    public void addDivisionsToBook(List<BookDivision> bookDivisions, Book book) {
        for (BookDivision bookDivision : bookDivisions) {
            bookDivision.setBook(book);
            save(bookDivision);
        }
    }

    public void changeDivisions(List<BookDivision> bookDivisions, 
                                String authorsString, String bookName
    )
    {
        List<String> divisionLinks = new ArrayList<>();

        for (BookDivision bookDivision : bookDivisions) {
            divisionLinks.add(bookDivision.getFileLink());
        }

        List<String> actualLinks = fileRecorder.changeBookDirectory(divisionLinks, authorsString, bookName);

        Iterator<BookDivision> divisionIterator = bookDivisions.iterator();
        Iterator<String> linkIterator = actualLinks.iterator();

        while (divisionIterator.hasNext() && linkIterator.hasNext()) {
            BookDivision bookDivision = divisionIterator.next();
            String link = linkIterator.next();
            
            bookDivision.setFileLink(link);
            save(bookDivision);
        }
    }

}
