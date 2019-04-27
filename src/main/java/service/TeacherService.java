package service;

import pojo.Teacher;


public interface TeacherService {

//    /**
//     *  根据教师名、教师id确定是否有该教师
//     * @param teacherName 教师名
//     * @param teacherId教师id
//     * @return TeacherInfo实体
//     */
//    TeacherInfo selectWithoutId(String teacherName, String teacherId);

    int insert(Teacher teacher_info);
    int delById(Integer id);
    int countAll();

    int update(Teacher teacher);

    Teacher selectById(Integer id);
}