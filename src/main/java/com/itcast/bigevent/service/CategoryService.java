package com.itcast.bigevent.service;

import com.itcast.bigevent.pojo.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    List<Category> getAll();

    Category detail(Integer id);

    void update(Category category);

    void delete(Integer id);
}
