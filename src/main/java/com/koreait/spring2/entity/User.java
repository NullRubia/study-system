package com.koreait.spring2.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String userid;
    private String password;
    private String name;
    private String email;
}