package com.koreait.spring2.mapper;

import com.koreait.spring2.entity.Study;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyMapper {
    void insertStudy(Study study);
    List<Study> getStudiesByWriter(int writerId);
    List<Study> findAll();
    Study findById(int id);
    List<Study> search(String keyword); // 제목+작성자
}
