package com.example.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user2")
    List<User> myFindAll();


    @Insert("insert into user2(username,password,nickname,email,phone,address) " +
            "values (#{username},#{password},#{nickname},#{email},#{phone},#{address})")
    int myInsert(User user);


    int myUpdate(User user);

    @Delete("delete from user2 where id = #{id}")
    int myDeleteById(@Param("id") int id);
}
