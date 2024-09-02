package com.itcast.bigevent.controller;


import com.itcast.bigevent.pojo.Article;
import com.itcast.bigevent.pojo.PageBean;
import com.itcast.bigevent.pojo.Result;
import com.itcast.bigevent.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping()
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success("添加成功！");
    }

    @GetMapping("/getAll")
    public Result<PageBean<Article>> allArticle(Integer pageNum, Integer pageSize,
                                                @RequestParam(required = false) String categoryId,
                                                @RequestParam(required = false) String state){
        PageBean<Article> list = articleService.getAll(pageNum,pageSize,categoryId,state);

        return Result.success(list);
    }
}
