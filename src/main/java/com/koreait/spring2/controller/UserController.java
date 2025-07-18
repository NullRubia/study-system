package com.koreait.spring2.controller;

import com.koreait.spring2.dto.LoginDTO;
import com.koreait.spring2.dto.UserUpdateDTO;
import com.koreait.spring2.entity.User;
import com.koreait.spring2.service.UserService;
import com.koreait.spring2.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    private String extractUserid(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) return null;

        return jwtUtil.getUseridFromToken(token);
    }

    @PostMapping("/signup")
    public String register(@RequestBody User user) {
        userService.register(user);
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        User user = userService.findByUserid(loginRequest.getUserid());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("존재하지 않는 사용자입니다.");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 틀립니다.");
        }

        String token = jwtUtil.createToken(user.getUserid());
        return ResponseEntity.ok().body(Collections.singletonMap("token", token));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        String userid = extractUserid(authHeader);
        if (userid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 없거나 유효하지 않습니다.");
        }

        User user = userService.findByUserid(userid);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 없음");
        }

        user.setPassword(null); // 비밀번호 숨기기
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO dto,
                                        @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String userid = extractUserid(authHeader);
        if (userid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰 없음 또는 유효하지 않음");
        }

        userService.updateUser(userid, dto);
        return ResponseEntity.ok("회원 정보 수정 완료");
    }
}
