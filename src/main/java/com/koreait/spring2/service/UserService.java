package com.koreait.spring2.service;

import com.koreait.spring2.dto.UserUpdateDTO;
import com.koreait.spring2.entity.User;
import com.koreait.spring2.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
    }

    public User findByUserid(String userid) {
        return userMapper.findByUserid(userid);
    }

    public void updateUser(String userid, UserUpdateDTO dto) {
        User user = new User();
        user.setUserid(userid);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        userMapper.updateUser(user);
    }
}
