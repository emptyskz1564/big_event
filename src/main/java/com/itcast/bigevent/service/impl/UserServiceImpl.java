package com.itcast.bigevent.service.impl;

import com.itcast.bigevent.mapper.UserDao;
import com.itcast.bigevent.pojo.User;
import com.itcast.bigevent.service.UserService;
import com.itcast.bigevent.utils.MD5;
import com.itcast.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public List<User> findUserByUsername(String username) {
        List<User> user = userDao.findUserByUsername(username);
        return user;
    }

    @Override
    public Boolean addUser(String username, String password) {
        //加密
        System.out.println(password);
        String s = MD5.encrypByMd5(password);
        Boolean row = userDao.insertUser(username, s);
        return row;
    }

    @Override
    public String login(String username, String password) {
        String truePassword = userDao.findPasswordByUsername(username);
        return truePassword;
    }

    @Override
    public Integer finIdByUsername(String username) {
        Integer id = userDao.finIdByUsername(username);
        return id;
    }

    @Override
    public User findUserById(Integer id) {
        User user = userDao.findUserById(id);
        return user;
    }

    @Override
    public void updateUserInfoById(User user) {
        userDao.updateUserInfoById(user);
    }

    @Override
    public void updateAvatar(String url) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer)map.get("id");
        userDao.updateAvatarById(url,id);
    }

    @Override
    public String findPwdById(Integer id) {
        String pwd = userDao.findPwdById(id);
        return pwd;
    }

    @Override
    public void updatePwd(String newPwd, Integer id) {
        userDao.updatePwd(newPwd,id);
    }
}
