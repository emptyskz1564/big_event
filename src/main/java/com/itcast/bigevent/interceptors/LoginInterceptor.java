package com.itcast.bigevent.interceptors;

import com.itcast.bigevent.pojo.Result;
import com.itcast.bigevent.utils.JwtUtil;
import com.itcast.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //通过请求头的Authorization获取token
        String token = request.getHeader("Authorization");
        //验证token
        try {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            String redisToken = ops.get(token);
            if(redisToken == null){
                throw new RuntimeException("过期或未登录");
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //将数据放到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //解析成功放行
            return true;
        } catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocalUtil，请求结束后调用
        ThreadLocalUtil.remove();
    }
}
