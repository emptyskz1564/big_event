package com.itcast.bigevent.mapper;

import com.itcast.bigevent.pojo.User;

import java.util.List;

public interface UserDao {
    List<User> findUserByUsername(String username);

    Boolean insertUser(String username, String password);

    String findPasswordByUsername(String username);

    Integer finIdByUsername(String username);

    User findUserById(Integer id);

    void updateUserInfoById(User user);

    void updateAvatarById(String url, Integer id);

    String findPwdById(Integer id);

    void updatePwd(String newPwd, Integer id);
}
