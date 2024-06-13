package com.tolmic.digitallibrary.entities;

import javax.persistence.*;


@Entity
@Table(name = "original_language")
public class OriginalLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "original_language_id")
    private Long id;

    @Column(name = "name")
    private String name;


    public OriginalLanguage() {

    }

    public OriginalLanguage(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
