package com.koreait.spring2.service;

import com.koreait.spring2.entity.Study;
import com.koreait.spring2.entity.StudyApplication;
import com.koreait.spring2.entity.User;
import com.koreait.spring2.mapper.StudyApplicationMapper;
import com.koreait.spring2.mapper.StudyMapper;
import com.koreait.spring2.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyMapper studyMapper;
    private final UserMapper userMapper;
    private final StudyApplicationMapper studyApplicationMapper;

    public void createStudy(String userid, Study study) {
        User user = userMapper.findByUserid(userid);
        study.setWriterId(user.getId());
        studyMapper.insertStudy(study);

        StudyApplication application = new StudyApplication();
        application.setStudyId(study.getId());
        application.setUserId(user.getId());
        studyApplicationMapper.insert(application);
    }

    public List<Study> getAllStudies() {
        return studyMapper.findAll();
    }

    public Study getStudyById(int id) {
        return studyMapper.findById(id);
    }

    public List<Study> searchStudies(String keyword) {
        return studyMapper.search(keyword);
    }
}
