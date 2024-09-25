package com.tolmic.digitallibrary.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.tolmic.digitallibrary.entities.embeddable.StarGradePK;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class StarGrade {

    @EmbeddedId
    private StarGradePK pk; 

    @Column(name = "date")
    private Date date;

    @Column(name = "number_stars")
    private Double numberStars;

}