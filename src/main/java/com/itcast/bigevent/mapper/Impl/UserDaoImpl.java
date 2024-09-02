package com.itcast.bigevent.mapper.Impl;

import com.itcast.bigevent.mapper.UserDao;
import com.itcast.bigevent.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.net.Inet4Address;
import java.util.List;
import java.util.Map;


@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findUserByUsername(String username) {
        String sql = "select * from user where username = ?";
        List<User> User = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class), username);

        return User;
    }

    @Override
    public Boolean insertUser(String username, String password) {
        String sql = "insert into user (username,password,create_time,update_time) value (?,?,now(),now())";
        int rows = jdbcTemplate.update(sql,username,password);
        return rows>0;
    }

    @Override
    public String findPasswordByUsername(String username) {
        String sql = "select password from user where username = ?";
        String password;
        try {
            password = jdbcTemplate.queryForObject(sql, String.class, username);
        } catch (Exception e){
            password = null;
        }
        return password;
    }

    @Override
    public Integer finIdByUsername(String username) {
        String sql = "select id from user where username = ?";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return id;
    }

    @Override
    public User findUserById(Integer id) {
        String sql = "select * from user where id = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        System.out.println(user);
        return user;
    }


    @Override
    public void updateUserInfoById(User user) {
        String sql = "update user set nickname=?, email=?, update_time=now() where id=?";
        jdbcTemplate.update(sql,user.getNickname(),user.getEmail(),user.getId());
    }

    @Override
    public void updateAvatarById(String url, Integer id) {
        String sql = "update user set user_pic=?, update_time=now() where id=?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public String findPwdById(Integer id) {
        String sql = "select password from user where id=?";
        String s = jdbcTemplate.queryForObject(sql, String.class, id, id);
        return s;
    }

    @Override
    public void updatePwd(String newPwd, Integer id) {
        String sql = "update user set password=?,update_time=now() where id=?";
        jdbcTemplate.update(sql,newPwd,id);
    }
}
