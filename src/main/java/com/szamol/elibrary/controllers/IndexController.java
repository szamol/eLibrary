package com.szamol.elibrary.controllers;

import com.szamol.elibrary.models.News;
import com.szamol.elibrary.services.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.security.PermitAll;
import java.util.List;

@Controller
public class IndexController {

    private NewsService newsService;

    public IndexController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String showIndexPage(Model model){
        List<News> newsList = newsService.findAllActiveNews();
        model.addAttribute("newsList", newsList);

        return "index";
    }

    @GetMapping("/news/{newsId}")
    public String showNews(@PathVariable("newsId") int newsId, Model model) {
        News news = newsService.findById(newsId);

        model.addAttribute("news", news);

        return "news";
    }
}
