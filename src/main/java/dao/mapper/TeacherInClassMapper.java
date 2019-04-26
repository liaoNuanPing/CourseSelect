package dao.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.TeacherInClass;
import pojo.TeacherInClassExample;

import java.util.List;

public interface TeacherInClassMapper {
    int countByExample(TeacherInClassExample example);

    int deleteByExample(TeacherInClassExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TeacherInClass record);

    int insertSelective(TeacherInClass record);

    List<TeacherInClass> selectByExample(TeacherInClassExample example);

    TeacherInClass selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TeacherInClass record, @Param("example") TeacherInClassExample example);

    int updateByExample(@Param("record") TeacherInClass record, @Param("example") TeacherInClassExample example);

    int updateByPrimaryKeySelective(TeacherInClass record);

    int updateByPrimaryKey(TeacherInClass record);
}