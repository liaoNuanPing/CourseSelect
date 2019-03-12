package service.impl;

import dao.mapper.PerClassCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.PerClassCourse;
import pojo.PerClassCourseExample;
import service.PerClassCourseService;
import utils.ConnectDB;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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

    public List<PerClassCourse> selectByTermAndGradeAndClass(String term,String grade,String classes){
        return perClassCourseMapper.selectByTermAndGradeAndClass(term,grade,classes);
    }

    public List<PerClassCourse> selectByCourseIdAndTermAndGradeAndClass(String courseId, String term, String grade, String classes) throws Exception {

        String c = "";
        if (!"0".equals(grade))
            c += "and (grade=0 or grade=" + grade + ")";
        if (!"0".equals(term))
            c += "and (term=0 or term=" + term + ")";
        if (!"0".equals(classes))
            c += "and (to_class=0 or to_class=" + classes + ")";
        String sql = "SELECT * " +
                "FROM per_class_course " +
                "where course_id=" + courseId + " " +
                c;
//              查找数据库
        Statement ptsm = ConnectDB.getConnection().createStatement();
        ResultSet rs = ptsm.executeQuery(sql);
        List<PerClassCourse> perClassCourseList = new ArrayList<PerClassCourse>();
        while (rs.next()) {
            PerClassCourse perClass = new PerClassCourse(
                    rs.getInt("id"),
                    rs.getInt("course_id"),
                    rs.getString("grade"),
                    rs.getString("term"),
                    rs.getString("to_class"),
                    rs.getInt("total_need_stu_amount"),
                    rs.getInt("have_stu_amount")
            );//end CourseShow
            perClassCourseList.add(perClass);
        }
        return perClassCourseList;

    }

    public int updateHaveStuAmountAddOne(Integer id){
        return perClassCourseMapper.updateHaveStuAmountAddOne(id);
    }

}
