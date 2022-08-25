package com.example.config;


import com.example.common.interceptor.JwInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")    //拦截所有请求，判断token是否合法决定是否登录
                .excludePathPatterns("/user/login","/user/register","**/export");
    }


    @Bean
    public JwInterceptor jwtInterceptor(){
        return new JwInterceptor();
    }
}
