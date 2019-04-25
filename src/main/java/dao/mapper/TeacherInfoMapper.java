package dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pojo.TeacherInfo;
import pojo.TeacherInfoExample;

public interface TeacherInfoMapper {
    int countByExample(TeacherInfoExample example);

    int deleteByExample(TeacherInfoExample example);

    int insert(TeacherInfo record);

    int insertSelective(TeacherInfo record);

    List<TeacherInfo> selectByExample(TeacherInfoExample example);

    int updateByExampleSelective(@Param("record") TeacherInfo record, @Param("example") TeacherInfoExample example);

    int updateByExample(@Param("record") TeacherInfo record, @Param("example") TeacherInfoExample example);
}