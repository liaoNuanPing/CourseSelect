package dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pojo.TeacherClass;
import pojo.TeacherClassExample;

public interface TeacherClassMapper {
    int countByExample(TeacherClassExample example);

    int deleteByExample(TeacherClassExample example);

    int insert(TeacherClass record);

    int insertSelective(TeacherClass record);

    List<TeacherClass> selectByExample(TeacherClassExample example);

    int updateByExampleSelective(@Param("record") TeacherClass record, @Param("example") TeacherClassExample example);

    int updateByExample(@Param("record") TeacherClass record, @Param("example") TeacherClassExample example);
}