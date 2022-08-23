package com.example.bootmybatis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.example.bootmybatis.service.IUser2Service;
import com.example.bootmybatis.entity.User2;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tjyy
 * @since 2022-08-23
 */
@RestController
@RequestMapping("/user2")
public class User2Controller {

    @Resource
    private IUser2Service user2Service;

    @PostMapping
    public Boolean save(@RequestBody User2 user2) {
        return user2Service.saveOrUpdate(user2);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return user2Service.removeById(id);
    }

    @GetMapping
    public List<User2> findAll() {
        return user2Service.list();
    }

    @GetMapping("/{id}")
    public User2 findOne(@PathVariable Integer id) {
        return user2Service.getById(id);
    }

    @GetMapping("/page")
    public Page<User2> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        return user2Service.page(new Page<>(pageNum, pageSize));
    }

}

