package service;

import org.springframework.stereotype.Service;
import pojo.Student;

@Service
public interface StudentService {

    /**
     *  根据学生名、年级、班级、父母名字确定是否有该学生
     * @param stuName 学生名
     * @param grade 年级
     * @param classNow 班级
     * @param parentName 父母名字
     * @return Student实体
     */
    Student selectWithoutId(String stuName, String grade, String classNow, String parentName);

    int insert(Student student);
    int delById(Integer id);
    int countAll();

    Student selectById(Integer id);

    /**
     * 通过插入新的，删除原先的实现更新
     * @param id 通过id删除
     * @param student 插入的学生
     */
    void updateByDel(Integer id, Student student);

    /**
     * 学生年级加减
     * @param num 加减数
     * @param grade 最高年级数
     * @return 受影响行数
     */
    int updateAllStudentGradeUp(Integer num, Integer grade);
    int updateAllStudentGradeDown(Integer num);

}
