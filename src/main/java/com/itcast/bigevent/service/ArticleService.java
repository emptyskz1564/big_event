package com.itcast.bigevent.service;

import com.itcast.bigevent.pojo.Article;
import com.itcast.bigevent.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> getAll(Integer pageNum, Integer pageSize, String categoryId, String state);
}
