package com.szamol.elibrary.controllers;


import com.szamol.elibrary.models.News;
import com.szamol.elibrary.services.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Secured("ROLE_WORKER")
public class WorkerController {

    private NewsService newsService;

    public WorkerController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/worker/newsList/{pageNumber}")
    public String showPageWithAllNews(@PathVariable("pageNumber") int pageNumber, Model model) {
        Page<News> pages = getAllNewsPageable(pageNumber - 1);
        List<News> newsList = pages.getContent();

        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("currentPage", pages.getNumber() + 1);
        model.addAttribute("newsList", newsList);

        return "worker/newsList";
    }

    @PostMapping("/worker/newsList")
    public String changeNewsStatus(@RequestParam(value = "statusButton") String newStatus, @RequestParam(value = "id") int id) {

        int newStatusInt = newStatus.equals("Activate") ? 1 : 0;
        newsService.updateNewsStatus(newStatusInt, id);
        return "redirect:/worker/newsList/1";
    }

    private Page<News> getAllNewsPageable(int pageNumber) {
        int rowsInPage = 5;

        Page<News> page = newsService.findAllNews(PageRequest.of(pageNumber, rowsInPage));
        return  page;
    }

}
