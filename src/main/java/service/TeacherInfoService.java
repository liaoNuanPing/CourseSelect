package service;

import org.springframework.stereotype.Service;
import pojo.TeacherInfo;

@Service
public interface TeacherInfoService {

//    /**
//     *  根据教师名、教师id确定是否有该教师
//     * @param teacherName 教师名
//     * @param teacherId教师id
//     * @return TeacherInfo实体
//     */
//    TeacherInfo selectWithoutId(String teacherName, String teacherId);

    int insert(TeacherInfo teacher_info);
    int delById(Integer id);
    int countAll();

    TeacherInfo selectById(Integer id);
}