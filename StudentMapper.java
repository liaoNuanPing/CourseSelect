package dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pojo.Student;
import pojo.StudentExample;

import java.util.List;

@Repository
public interface StudentMapper {
    int countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(Integer id);

    int deleteWxStudentByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
    Student selectWithoutId(@Param("stuName") String stuName,@Param("grade") String grade,@Param("classNow") String classNow,@Param("parentName") String parentName);

    int updateAllStudentGradeUp(Integer num,Integer grade);
    int updateAllStudentGradeDown(Integer num);
}