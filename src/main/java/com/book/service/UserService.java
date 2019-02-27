package com.book.service;

import com.book.dao.UserDao;
import com.book.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public ArrayList<User> List(){
        return userDao.userList();
    }

    public boolean addUser(User user){
        return userDao.addUser(user) > 0;
    }


    public int deleteUserList(String userId ){
        return userDao.deleteUserList(userId);
    }
    public ArrayList<User> queryList(String userName){
        return userDao.queryUser(userName);
    }

    public User queryUserById(String userId){
        return userDao.queryUserById(userId);
    }

    public boolean addUser2(User user){
        return userDao.addUser2(user)>0;
    }
    public boolean updateUser(User user){
        return userDao.updateUser(user)>0;
    }
    public boolean updateUser_chatdata(User user){
        return userDao.updateUser_chatdata(user)>0;
    }
    public boolean updateUser_filename(User user){
        return userDao.updateUser_filename(user)>0;
    }
    public boolean updateUser_filename2(User user){
        return userDao.updateUser_filename2(user)>0;
    }
}
