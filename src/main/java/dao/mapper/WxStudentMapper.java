package dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pojo.WxStudent;
import pojo.WxStudentExample;

import java.util.List;

@Repository
public interface WxStudentMapper {
    int countByExample(WxStudentExample example);

    int deleteByExample(WxStudentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WxStudent record);

    int insertSelective(WxStudent record);

    List<WxStudent> selectByExample(WxStudentExample example);

    WxStudent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WxStudent record, @Param("example") WxStudentExample example);

    int updateByExample(@Param("record") WxStudent record, @Param("example") WxStudentExample example);

    int updateByPrimaryKeySelective(WxStudent record);

    int updateByPrimaryKey(WxStudent record);
}