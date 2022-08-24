package com.example.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.controller.dto.UserDto;
import com.example.entity.User;
import com.example.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
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

    @PostMapping("/login")
    public Boolean login(@RequestBody UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return false;
        }
        return userService.login(userDto);
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


    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<User> list = userService.list();

        ExcelWriter writer = ExcelUtil.getWriter(true);
//        writer.addHeaderAlias("username","用户名");
//        writer.addHeaderAlias("address","地址");

        writer.write(list,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");

        String fileName = URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public void imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> users = reader.readAll(User.class);
        System.out.println(users);
    }


}
