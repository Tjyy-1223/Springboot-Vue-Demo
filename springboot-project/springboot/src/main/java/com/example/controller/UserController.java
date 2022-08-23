package com.example.controller;

import com.example.Mapper.UserMapper;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Integer save(@RequestBody User user){
        return userService.mySave(user);
    }

    @GetMapping
    public List<User> index(){
        return userService.myFindAll();
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Integer id){
        return userService.myDeleteById(id);
    }
}
