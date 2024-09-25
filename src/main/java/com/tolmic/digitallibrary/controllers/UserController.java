package com.tolmic.digitallibrary.controllers;

import java.security.Principal;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tolmic.digitallibrary.entities.BookDivision;
import com.tolmic.digitallibrary.entities.User;
import com.tolmic.digitallibrary.services.BookDivisionService;
import com.tolmic.digitallibrary.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookDivisionService bookDivisionService;

    @GetMapping("/")
    public String library(Model model, Principal principal) {

        User user = null;

        if (principal != null) {
            user = userService.findByLogin(principal.getName());
        }

        model.addAttribute("user", user);

        return "library";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {

        model.addAttribute("error", null);

        return "/registration";
    }

    @PostMapping("/add_mark")
    private String addMark(@RequestParam Long divisionId, Principal principal) {

        BookDivision bookDivision = bookDivisionService.findById(divisionId);

        User user = userService.findByLogin(principal.getName());
        user.addMark(bookDivision);
        userService.save(user);

        return "redirect:/division?id=" + divisionId;
    }

    @PostMapping("/mark_delete")
    public String deleteMark(@RequestParam Long markId, Principal principal) {

        User user = userService.findByLogin(principal.getName());
        user.removeMark(markId);
        userService.save(user);

        return "redirect:/";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam Date birthday,
                               @RequestParam String city,
                               @RequestParam String login,
                               @RequestParam String password,
                               Model model
    )
    {

        User user = new User(name, surname, login, password, birthday, city);

        if (!userService.createUser(user)) {
            model.addAttribute("error", "Пользователь с логином " + user.getUsername()
                    + " уже существует !");

            model.addAttribute("name", name);
            model.addAttribute("surname", surname);
            model.addAttribute("birthday", birthday);
            model.addAttribute("city", city);

            return "/registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/restore_password")
    public String restorePassword() {
        return "restore_password";
    }

}
