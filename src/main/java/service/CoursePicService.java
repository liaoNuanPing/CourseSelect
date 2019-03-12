package service;

import pojo.CoursePic;

import java.util.List;

public interface CoursePicService {
    /**
     * 根据课程id查找该课程的所有图片
     * @param id Integer 课程id
     * @return List List<CoursePic>
     */
    List<CoursePic> selectCoursePicByCourseId(Integer id);

    int insert(CoursePic coursePic);

    int delById(Integer id);

    CoursePic selectById(Integer id);
}
