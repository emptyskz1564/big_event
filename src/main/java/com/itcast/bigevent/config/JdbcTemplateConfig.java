package com.itcast.bigevent.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {

//    @Bean()
//    @ConfigurationProperties(prefix="spring.datasource.two")//@ConfigurationProperties 是 Spring Boot 提供的类型安全的属性绑定
//    DataSource getDataSource(){
//        return DruidDataSourceBuilder.create().build();
//    }

    @Bean
    JdbcTemplate getJdbcTemplate(@Autowired DataSource ds){
        System.out.println(ds);
        return new JdbcTemplate(ds);
    }
}
