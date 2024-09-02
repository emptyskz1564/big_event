package com.itcast.bigevent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest     //这个注解会初始化springIOC容器
class BigEventApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void redisTest() {
        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
        op.set("username","shliu");
    }

}
