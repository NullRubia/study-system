package com.koreait.spring2.controller;

import com.koreait.spring2.entity.Study;
import com.koreait.spring2.entity.User;
import com.koreait.spring2.mapper.StudyMapper;
import com.koreait.spring2.mapper.UserMapper;
import com.koreait.spring2.service.StudyService;
import com.koreait.spring2.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final StudyMapper studyMapper;

    @PostMapping
    public ResponseEntity<?> createStudy(@RequestBody Study study,
                                         @RequestHeader("Authorization") String authHeader) {
        String userid = jwtUtil.getUseridFromToken(authHeader.substring(7));
        studyService.createStudy(userid, study);
        return ResponseEntity.ok("스터디 등록 완료");
    }

    @GetMapping
    public ResponseEntity<?> getAllStudies() {
        return ResponseEntity.ok(studyService.getAllStudies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudy(@PathVariable int id) {
        Study study = studyService.getStudyById(id);
        if (study == null) return ResponseEntity.status(404).body("존재하지 않음");
        return ResponseEntity.ok(study);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchStudies(@RequestParam String keyword) {
        return ResponseEntity.ok(studyService.searchStudies(keyword));
    }

    @GetMapping("/mine")
    public ResponseEntity<?> getMyStudies(@RequestHeader("Authorization") String authHeader) {
        String userid = jwtUtil.getUseridFromToken(authHeader.substring(7));
        User user = userMapper.findByUserid(userid);
        return ResponseEntity.ok(studyMapper.getStudiesByWriter(user.getId()));
    }
}
