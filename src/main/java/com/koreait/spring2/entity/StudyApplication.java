package com.koreait.spring2.entity;

import lombok.Data;

@Data
public class StudyApplication {
    private int id;
    private int userId;
    private int studyId;
    private String appliedAt;
}
