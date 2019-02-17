package dao.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.StuSelect;
import pojo.StuSelectExample;

import java.util.List;

public interface StuSelectMapper {
    int countByExample(StuSelectExample example);

    int deleteByExample(StuSelectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StuSelect record);

    int insertSelective(StuSelect record);

    List<StuSelect> selectByExample(StuSelectExample example);

    StuSelect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StuSelect record, @Param("example") StuSelectExample example);

    int updateByExample(@Param("record") StuSelect record, @Param("example") StuSelectExample example);

    int updateByPrimaryKeySelective(StuSelect record);

    int updateByPrimaryKey(StuSelect record);

    int countAll();
}