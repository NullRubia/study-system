package com.koreait.spring2.mapper;

import com.koreait.spring2.dto.ApplicationDTO;
import com.koreait.spring2.entity.StudyApplication;
import com.koreait.spring2.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudyApplicationMapper {
    void insert(StudyApplication app);
    StudyApplication findByUserAndStudy(@Param("userId") int userId, @Param("studyId") int studyId);
    int countByStudy(int studyId);
    List<User> getApplicantsByStudy(int studyId);
    List<ApplicationDTO> findByUser(int userId);
}
