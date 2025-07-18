package com.koreait.spring2.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String password;
    private String name;
    private String email;
}