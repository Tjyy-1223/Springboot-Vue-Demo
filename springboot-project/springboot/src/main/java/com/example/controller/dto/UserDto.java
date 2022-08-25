package com.example.controller.dto;


import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String token;
}
