package service;

import pojo.Course;

import java.util.List;

public interface CourseService {

    /**
     * 按页数取PerClassCourse
     * @param page 第几行
     * @param rows 取多少行
     * @return List 集合Course
     */
    List<Course> getCourseList(int page, int rows);


    List<Course> selectCourseListByCourseIdList(List<Integer> courseId);

    int insert(Course course);

    int delById(Integer id);

    int update(Course course);

    /**
     * 根据sql语句查找
     * @param sql String sql语句
     * @return 返回List Course
     */
    List<Course> selectBySql(String sql);

    /**
     * 根据课程名查找，理论上是要唯一的，但sql没设置唯一，目前返回List
     * @param name String 课程名
     * @return 返回List Course
     */
    List<Course> selectByCName(String name);

    Course selectByPrimaryId(Integer id);
}
