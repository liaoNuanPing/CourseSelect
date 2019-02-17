package service.impl;

import dao.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Course;
import pojo.CourseExample;
import service.CourseService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired CourseMapper courseMapper;

    public List<Course> getCourseList(int page,int rows){
        return courseMapper.selectLimit((page-1)*rows, rows);
    }

    public int insert(Course course){
        return courseMapper.insert(course);
    }
     public int update(Course course){
        return  courseMapper.updateByPrimaryKey(course);
    }


    public List<Course> selectCourseListByCourseIdList(List<Integer> courseId){
        List<Course> courseList=new ArrayList<Course>();
        for (int i=0;i<courseId.size();i++) {
            Course course = courseMapper.selectByPrimaryKey(courseId.get(i));
            courseList.add(course);
        }
        return courseList;
    }

    public List<Course> selectBySql(String sql){
        return courseMapper.selectBySql(sql);
    }

    public int delById(Integer id){
        CourseExample example=new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return courseMapper.deleteByExample(example);
    }

    public List<Course> selectByCName(String name){
        CourseExample example=new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        criteria.andCNameEqualTo(name);
        return courseMapper.selectByExample(example);
    }

    public Course selectByPrimaryId(Integer id){
        return courseMapper.selectByPrimaryKey(id);
    }
}
