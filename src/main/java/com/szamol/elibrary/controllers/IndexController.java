package com.szamol.elibrary.controllers;

import com.szamol.elibrary.models.News;
import com.szamol.elibrary.services.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    private NewsService newsService;

    public IndexController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String showIndexPage(Model model){

        //List<News> newsList = newsService.findAllNews();
        List<News> newsList = newsService.findAllActiveNews();
        model.addAttribute("newsList", newsList);

        return "index";
    }
}
