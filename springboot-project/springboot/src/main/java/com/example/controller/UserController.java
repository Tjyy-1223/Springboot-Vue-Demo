package com.example.controller;

import com.example.Mapper.UserMapper;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public List<User> index(){
        return userMapper.myFindAll();
    }
}
