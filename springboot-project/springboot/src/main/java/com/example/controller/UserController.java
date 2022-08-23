package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.User;
import com.example.service.UserService;
import org.apache.commons.lang.StringUtils;
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


    @GetMapping("/page")
    public Page<User> findPage(@RequestParam int pageNum,@RequestParam int pageSize,@RequestParam String username){
        Page<User> pageInfo = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(username),User::getUsername,username);
        queryWrapper.orderByDesc(User::getId);
        userService.page(pageInfo,queryWrapper);
        return pageInfo;
    }

    @DeleteMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeByIds(ids);
    }
}
