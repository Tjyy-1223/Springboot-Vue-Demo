package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.controller.dto.UserDto;
import com.example.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    int mySave(User user);

    List<User> myFindAll();

    int myDeleteById(int id);

    Boolean login(UserDto userDto);
}
