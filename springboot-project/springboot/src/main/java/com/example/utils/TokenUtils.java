package com.example.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
public class TokenUtils {

    private static UserService staticUserService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void setUserService(){
        staticUserService = userService;
    }

    public static String getToken(String userId,String sign){
        return JWT.create().withAudience(userId)   //将user id 保存到token里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))
                .sign(Algorithm.HMAC256(sign));
    }


    //java获取当前的用户信息 - 即user对象
    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                staticUserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e){
                return null;
            }
        return null;
    }
}
