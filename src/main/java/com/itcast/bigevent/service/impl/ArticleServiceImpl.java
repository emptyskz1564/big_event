package com.itcast.bigevent.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.bigevent.mapper.ArticleMapper;
import com.itcast.bigevent.pojo.Article;
import com.itcast.bigevent.pojo.PageBean;
import com.itcast.bigevent.service.ArticleService;
import com.itcast.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> getAll(Integer pageNum, Integer pageSize, String categoryId, String state) {
        PageBean<Article> pageBean = new PageBean<>();
        //开启分页后，不需要再往mapper中传入分页大小和页数
        PageHelper.startPage(pageNum,pageSize);
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        List<Article> all = articleMapper.getAll(categoryId, state, id);
        Page<Article> p  = (Page<Article>) all;
        pageBean.setTotal(p.getTotal());
        pageBean.setList(p.getResult());
        return pageBean;
    }
}
