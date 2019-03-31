package com.szamol.elibrary.services;

import com.szamol.elibrary.models.News;
import com.szamol.elibrary.repositories.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class NewsService {

    private NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Page<News> findAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    public News findById(int id) {
        return newsRepository.findById(id);
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

    public void updateNewsStatus(int newStatus, int id) {
        newsRepository.updateNewsStatus(newStatus, id);
    }

    public void saveNews(News news) {
        news.setIsActive(1);
        news.setCreationDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        news.setImgSrc(null);
        newsRepository.save(news);
    }
}
