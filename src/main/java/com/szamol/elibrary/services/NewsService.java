package com.szamol.elibrary.services;

import com.szamol.elibrary.models.News;
import com.szamol.elibrary.repositories.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NewsService {

    private NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> findAllNews() {
        return newsRepository.findAll();
    }

    public List<News> findAllActiveNews() {
        List<News> newsList = newsRepository.findAll();
        List<News> resultList = new ArrayList<>();
        for (News news:newsList) {
            if(news.getIsActive() == 1)
                resultList.add(news);
        }
        return  resultList;
    }

}
