package service.impl;

import dao.mapper.PerClassCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.PerClassCourse;
import pojo.PerClassCourseExample;
import service.PerClassCourseService;

import java.util.List;

@Service
public class PerClassCourseServiceImpl implements PerClassCourseService{

    @Autowired
    PerClassCourseMapper perClassCourseMapper;


    public List<PerClassCourse> selectPerClassCourseListByCourseIdList(Integer courseId){
        return perClassCourseMapper.selectByCourseId(courseId);
    }

    public List<PerClassCourse> selectAllLimited(int page,int rows){
        return perClassCourseMapper.selectAllLimited(page, rows);
    }

    public int insert(PerClassCourse course){
        return perClassCourseMapper.insert(course);
    }
    public int update(PerClassCourse course){
        return perClassCourseMapper.updateByPrimaryKey(course);
    }

    public int countAll(){
        return perClassCourseMapper.countAll();
    }

    public List<PerClassCourse> selectBySql(String sql){
        return perClassCourseMapper.selectBySql(sql);
    }

    public PerClassCourse selectByPrimaryId(Integer id){
        return perClassCourseMapper.selectByPrimaryKey(id);
    }

    public List<PerClassCourse> selectByCourseId(Integer course_id){
        PerClassCourseExample example=new PerClassCourseExample();
        PerClassCourseExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(course_id);
        return perClassCourseMapper.selectByExample(example);
    }

    public int delById(Integer id){
        PerClassCourseExample example=new PerClassCourseExample();
        PerClassCourseExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return perClassCourseMapper.deleteByExample(example);
    }
}
