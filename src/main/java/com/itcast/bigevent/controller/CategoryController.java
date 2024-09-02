package com.itcast.bigevent.controller;

import com.itcast.bigevent.pojo.Category;
import com.itcast.bigevent.pojo.Result;
import com.itcast.bigevent.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController()
@RequestMapping("/category")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated Category category){
        categoryService.addCategory(category);
        return Result.success("添加成功！");
    }

    @GetMapping
    public Result<List<Category>> list(){
        List<Category> list = categoryService.getAll();
        return Result.success(list);
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id){
        Category c = categoryService.detail(id);
        return Result.success(c);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Category category){
        Integer id = category.getId();
        if(id == null){
            return Result.error("id不能为空");
        }
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam @NotNull Integer id){
        categoryService.delete(id);
        return Result.success("删除成功！");
    }
}
