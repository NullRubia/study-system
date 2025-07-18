package com.koreait.spring2.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Study {
    private int id;
    private String title;
    private String content;
    private int maxMember;
    private LocalDate deadline;
    private int writerId;
    private String writerUserid; // 작성자 이름 출력용 (JOIN)
    private String createdAt;
}
