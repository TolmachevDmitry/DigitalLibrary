package com.tolmic.digitallibrary.controllers;


import com.tolmic.digitallibrary.repositories.BookRepository;
import com.tolmic.digitallibrary.services.AuthorService;
import com.tolmic.digitallibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class StatisticsController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public String statistics(@RequestParam(name = "obj", required = false) String obj,
                             Model model
    )
    {

        List<Object[]> statistics = null;

        if (obj != null && obj.equals("authors")) {
            statistics = authorService.getAuthorStatistics();
        }
        if (obj != null && obj.equals("books")) {
            statistics = bookService.getBookStatistics();
        }

        model.addAttribute("object", obj);
        model.addAttribute("statistics", statistics);

        return "statistics";
    }

    @RequestMapping(value = "/script", method = RequestMethod.GET)
    public String script() {
        return "script";
    }
}
