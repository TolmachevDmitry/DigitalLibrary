package com.tolmic.digitallibrary.entities;

import javax.persistence.*;

@Entity
@Table(name = "book_division")
public class BookDivision {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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


    public Long getId() {
        return id;
    }

    public Integer getNumberValue() {
        return numberValue;
    }

    public Integer getNumberPart() {
        return numberPart;
    }

    public String getPartName() {
        return partName;
    }

    public Integer getNumberChapter() {
        return numberChapter;
    }

    public String getChapterName() {
        return chapterName;
    }

    public String getFileLink() {
        return fileLink;
    }

    public Book getBook() {
        return book;
    }

}
