package com.koreait.spring2.service;

import com.koreait.spring2.dto.ApplicationDTO;
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
public class StudyApplicationService {

    private final StudyApplicationMapper applyMapper;
    private final StudyMapper studyMapper;
    private final UserMapper userMapper;

    public String apply(String userid, int studyId) {
        User user = userMapper.findByUserid(userid);
        Study study = studyMapper.findById(studyId);
        if (user == null || study == null) return "존재하지 않는 사용자 또는 스터디";

        // 중복 체크
        StudyApplication existing = applyMapper.findByUserAndStudy(user.getId(), studyId);
        if (existing != null) return "이미 신청한 스터디입니다";

        // 정원 초과 체크
        int appliedCount = applyMapper.countByStudy(studyId);
        if (appliedCount >= study.getMaxMember()) return "정원이 초과되어 신청할 수 없습니다";

        StudyApplication app = new StudyApplication();
        app.setUserId(user.getId());
        app.setStudyId(studyId);
        applyMapper.insert(app);
        return "신청 완료";
    }

    public List<ApplicationDTO> getMyApplications(String userid) {
        User user = userMapper.findByUserid(userid);
        return applyMapper.findByUser(user.getId());
    }
}
