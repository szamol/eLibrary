package com.szamol.elibrary.controllers;


import com.szamol.elibrary.models.News;
import com.szamol.elibrary.services.NewsService;
import com.szamol.elibrary.validators.NewsValidator;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@Secured("ROLE_WORKER")
public class WorkerController {

    private NewsService newsService;
    private MessageSource messageSource;

    public WorkerController(NewsService newsService, MessageSource messageSource) {
        this.newsService = newsService;
        this.messageSource = messageSource;
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

    @GetMapping("/worker/addNews")
    public String showAddNewsPage(Map<String, Object> model) {
        News news = new News();
        model.put("news", news);
        return "worker/addNews";
    }

    @PostMapping("/worker/addNews")
    public String addNews(News news, BindingResult bindingResult, Model model, Locale locale) {
        String pageToReturn = null;

        new NewsValidator().validate(news, bindingResult);

        if (bindingResult.hasErrors()) {
            pageToReturn = "worker/addNews";
        } else {
            newsService.saveNews(news);
            model.addAttribute("news", new News());
            model.addAttribute("success", messageSource.getMessage("news.success", null, locale));
            pageToReturn = "worker/addNews";
        }
        return pageToReturn;
    }

    private Page<News> getAllNewsPageable(int pageNumber) {
        int rowsInPage = 5;

        Page<News> page = newsService.findAllNews(PageRequest.of(pageNumber, rowsInPage));
        return page;
    }

}
