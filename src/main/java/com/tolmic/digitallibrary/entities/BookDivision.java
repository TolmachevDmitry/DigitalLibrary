package com.tolmic.digitallibrary.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class BookDivision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long id;

    @Column(name = "number_value")
    private Integer numberValue;

    @Column(name = "number_part")
    private Integer numberPart;

    @Column(name = "part_name")
    private String partName;

    @Column(name = "number_chapter")
    private Integer numberChapter;

    @Column(name = "chapter_name")
    private String chapterName;

    @Column(name = "file_link")
    private String fileLink;

    @ManyToOne()
    @JoinColumn(name = "book_id")
    private Book book;


    public BookDivision(Integer numberValue, Integer numberPart, Integer numberChapter,
                        String partName, String chapterName, String fileLink) 
    {
        this.numberValue = numberValue;
        this.numberPart = numberPart;
        this.numberChapter = numberChapter;
        this.partName = partName;
        this.chapterName = chapterName;
        this.fileLink = fileLink;
    }

    public Long getBookId() {
        return book.getId();
    }

    public List<BookDivision> getBrotherDivisions() {
        return book.getBookDivisions();
    }

}
