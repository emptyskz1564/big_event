package com.itcast.bigevent.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageBean<T> {

    private Long total;
    private List<T> list;
}
