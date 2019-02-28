package service;

import pojo.StuSelect;

import java.util.List;

public interface StuSelectService {
    int countAll();

    int insert(StuSelect stuSelect);

    StuSelect selectByPrimaryId(Integer id);

    int delById(Integer id);

    int update(StuSelect stuSelect);

    List<StuSelect> selectByStudentId(Integer stu_id);

    List<StuSelect> selectByStudentIdAndTerm(Integer stu_id,String term);
}
