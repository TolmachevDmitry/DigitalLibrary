package com.tolmic.digitallibrary.entities;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "user")
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

    @ManyToMany
    @JoinTable(
            name = "user_heart_book",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> bookLiked = new ArrayList<>();


    public User() {

    }

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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getCity() {
        return city;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public boolean getActive() {
        return active;
    }

    public List<BookDivision> getMarks() {
        return marks;
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

    public boolean existsLike(Long bookId) {
        if (bookId == null) {
            return false;
        }

        for (Book b : bookLiked) {
            if (b.getId().equals(bookId)) {
                return false;
            }
        }

        return false;
    }

    public void setMark(BookDivision bookDivision) {
        this.marks.add(bookDivision);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void removeMark(Long id) {

        if (id == null) {
            return;
        }

        marks.removeIf(bookDivision -> bookDivision.getId().equals(id));
    }

    public void removeMarkByBookID(Long id) {
        this.marks.removeIf(bookDivision -> bookDivision.getBook().getId().equals(id));
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
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
