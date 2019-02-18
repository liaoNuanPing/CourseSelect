package service;

import pojo.PerClassCourse;

import java.util.List;

public interface PerClassCourseService {

//    /**
//     *  根据课程id集合查出选课要求的详细信息
//     * @param courseId List<Integer>  课程id集合
//     * @return List<PerClassCourse> 选课要求的详细信息集合
//     */
//    List<PerClassCourse> selectPerClassCourseListByCourseIdList(List<Integer> courseId);
    List<PerClassCourse> selectPerClassCourseListByCourseIdList(Integer courseId);

    /**
     * 按页数取PerClassCourse
     * @param start 第几行
     * @param rows 取多上行
     * @return List 集合PerClassCourse
     */
    List<PerClassCourse> selectAllLimited(int start,int rows);

    int insert(PerClassCourse course);

    int delById(Integer id);

    int update(PerClassCourse course);
    /**
     * 计算per_class_course总行数
     * @return per_class_course总行数
     */
    int countAll();


    /**
     * 根据sql语句查找
     * @param sql String sql语句
     * @return 返回List PerClassCourse
     */
    List<PerClassCourse> selectBySql(String sql);


    PerClassCourse selectByPrimaryId(Integer id);



}