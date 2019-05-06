package service;

import org.springframework.stereotype.Service;
import pojo.Student;

public interface StudentService {

//    /**
//     *  根据学生名、年级、班级、父母名字，父母身份证确定是否有该学生
//     * @param stuName 学生名
//     * @param grade 年级
//     * @param classNow 班级
//     * @param parentName 父母名字
//     * @param parentCode 父母身份证
//     * @return Student实体
//     */
//    Student selectWithoutId(String stuName, String grade, String classNow, String parentName,String parentCode);

    int insert(Student student);
    int delById(Integer id);
    int delWxStuById(Integer id);
    int countAll();
    //     选择学生通过当前传入id
    Student selectById(Integer id);


    /**
     * 学生年级加减
     * @param num 加减数
     * @param grade 最高年级数
     * @return 受影响行数
     */
    int updateAllStudentGradeUp(Integer num,Integer grade);

    int updateAllStudentGradeDown(Integer num);

}
