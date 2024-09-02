package com.itcast.bigevent.mapper;

import com.itcast.bigevent.pojo.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {

    @Insert("Insert into category (category_name,category_alias,create_user,create_time,update_time) " +
            "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    @Select("Select * from category where create_user=#{id}")
    List<Category> getAll(Integer id);

    @Select("select * from category where id=#{id}")
    Category getOne(Integer id);

    @Update("update category set category_name = categoryName,category_alias=categoryAlias,update_time=now()" +
            "where id=#{id}")
    void update(Category category);

    @Delete("delete from category where id=#{id}")
    void delete(Integer id);
}
