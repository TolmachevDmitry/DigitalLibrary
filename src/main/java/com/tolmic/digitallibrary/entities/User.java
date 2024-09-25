package com.tolmic.digitallibrary.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_surname")
    private String surname;

    @Column
    private Date birthday;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Transient
    transient private String confirmPassword;

    @Column(name = "date_registration")
    private Date dateRegistration;

    @Column(name = "city")
    private String city;

    @Column(name = "active")
    private boolean active;

    @ManyToMany()
    @JoinTable(
            name = "user_records",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "division_id")
    )
    private List<BookDivision> marks = new ArrayList<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
                     joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<StarGrade> starGrades = new ArrayList<>();


    public User(String name, String surname, String login, String password, Date birthday, String city) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.birthday = birthday;
        this.city = city;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getFullName() {
        return getName() + " " + getSurname();
    }

    public boolean existsMark(Long divisionId) {

        if (divisionId == null) {
            return false;
        }

        for (BookDivision bd : marks) {
            if (bd.getId().equals(divisionId)) {
                return true;
            }
        }

        return false;
    }

    public void addMark(BookDivision bookDivision) {
        this.marks.add(bookDivision);
    }

    public void removeMark(Long id) {

        if (id == null) {
            return;
        }

        marks.removeIf(bookDivision -> bookDivision.getId().equals(id));
    }

    public void removeMarkByBookID(Long id) {
        this.marks.removeIf(bookDivision -> bookDivision.getBookId().equals(id));
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeCommentById(Long commentId) {
        this.comments.removeIf(comment -> comment.getId().equals(commentId));
    }


    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

}
