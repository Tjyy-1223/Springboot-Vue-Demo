package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Mapper.UserMapper;
import com.example.common.Constants;
import com.example.controller.dto.UserDto;
import com.example.entity.User;
import com.example.exception.ServiceException;
import com.example.service.UserService;
import com.example.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
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

    @Override
    public UserDto login(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername());
        queryWrapper.eq("password",userDto.getPassword());
        User one = getOne(queryWrapper);

        if(one != null){
            BeanUtil.copyProperties(one,userDto,true);
            String token = TokenUtils.getToken(one.getId().toString(), one.getPassword());
            userDto.setToken(token);
            return userDto;
        }else{
            throw new ServiceException(Constants.CODE_500,"登录业务异常");
        }
    }
}
