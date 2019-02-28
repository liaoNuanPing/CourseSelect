package dao.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.PerClassCourse;
import pojo.PerClassCourseExample;

import java.util.List;

public interface PerClassCourseMapper {
    int countByExample(PerClassCourseExample example);

    int deleteByExample(PerClassCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PerClassCourse record);

    int insertSelective(PerClassCourse record);

    List<PerClassCourse> selectByExample(PerClassCourseExample example);

    PerClassCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PerClassCourse record, @Param("example") PerClassCourseExample example);

    int updateByExample(@Param("record") PerClassCourse record, @Param("example") PerClassCourseExample example);

    int updateByPrimaryKeySelective(PerClassCourse record);

    int updateByPrimaryKey(PerClassCourse record);

    List<PerClassCourse> selectAllLimited(Integer page,Integer rows);

    List<PerClassCourse> selectBySql(@Param("sql") String sql);

    int countAll();

    List<PerClassCourse> selectByCourseId(Integer id);

    List<PerClassCourse> selectByTermAndGradeAndClass(String term,String grade,String classes);




}