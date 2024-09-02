package com.itcast.bigevent.service;

import com.itcast.bigevent.pojo.User;

import java.util.List;

public interface UserService {

    List<User> findUserByUsername(String username);

    Boolean addUser(String username, String password);

    String login(String username, String password);

    Integer finIdByUsername(String username);

    User findUserById(Integer id);

    void updateUserInfoById(User user);

    void updateAvatar(String url);

    String findPwdById(Integer id);

    void updatePwd(String newPwd, Integer id);
}
