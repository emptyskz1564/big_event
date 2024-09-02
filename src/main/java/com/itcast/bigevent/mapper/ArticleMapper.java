package com.itcast.bigevent.mapper;

import com.itcast.bigevent.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {

    @Insert("Insert into article (title,content,cover_img,state,category_id,create_user,create_time,update_time)" +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},now(),now()")
    void add(Article article);


    List<Article> getAll(String categoryId, String state, Integer userId);
}
