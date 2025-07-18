package com.koreait.spring2.controller;

import com.koreait.spring2.dto.ApplicationDTO;
import com.koreait.spring2.entity.StudyApplication;
import com.koreait.spring2.mapper.StudyApplicationMapper;
import com.koreait.spring2.service.StudyApplicationService;
import com.koreait.spring2.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class StudyApplicationController {

    private final StudyApplicationService appService;
    private final JwtUtil jwtUtil;
    private final StudyApplicationMapper applicationMapper;

    @PostMapping("/{studyId}")
    public ResponseEntity<?> apply(@PathVariable int studyId,
                                   @RequestHeader("Authorization") String authHeader) {
        String userid = jwtUtil.getUseridFromToken(authHeader.substring(7));
        String result = appService.apply(userid, studyId);

        if (result.equals("신청 완료")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/mine")
    public ResponseEntity<?> getMyApplications(@RequestHeader("Authorization") String authHeader) {
        String userid = jwtUtil.getUseridFromToken(authHeader.substring(7));
        List<ApplicationDTO> list = appService.getMyApplications(userid);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/members/{studyId}")
    public ResponseEntity<?> getMembers(@PathVariable int studyId) {
        return ResponseEntity.ok(applicationMapper.getApplicantsByStudy(studyId));
    }
}
