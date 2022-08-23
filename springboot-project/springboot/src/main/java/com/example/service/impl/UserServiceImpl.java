package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Mapper.UserMapper;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    public int mySave(User user){
        if (user.getId() == null){
            return userMapper.myInsert(user);
        }else{
            return userMapper.myUpdate(user);
        }
    }

    public List<User> myFindAll(){
        return userMapper.myFindAll();
    }


    public int myDeleteById(int id) {
        return userMapper.myDeleteById(id);
    }
}