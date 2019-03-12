package dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pojo.CoursePic;
import pojo.CoursePicExample;

import java.util.List;

@Repository
public interface CoursePicMapper {
    int countByExample(CoursePicExample example);

    int deleteByExample(CoursePicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CoursePic record);

    int insertSelective(CoursePic record);

    List<CoursePic> selectByExample(CoursePicExample example);

    CoursePic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CoursePic record, @Param("example") CoursePicExample example);

    int updateByExample(@Param("record") CoursePic record, @Param("example") CoursePicExample example);

    int updateByPrimaryKeySelective(CoursePic record);

    int updateByPrimaryKey(CoursePic record);


}