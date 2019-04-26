package dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pojo.RegisterCode;
import pojo.RegisterCodeExample;

import java.util.List;

@Repository
public interface RegisterCodeMapper {
    int countByExample(RegisterCodeExample example);

    int deleteByExample(RegisterCodeExample example);

    int deleteByPrimaryKey(String code);

    int insert(RegisterCode record);

    int insertSelective(RegisterCode record);

    List<RegisterCode> selectByExample(RegisterCodeExample example);

    RegisterCode selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") RegisterCode record, @Param("example") RegisterCodeExample example);

    int updateByExample(@Param("record") RegisterCode record, @Param("example") RegisterCodeExample example);

    int updateByPrimaryKeySelective(RegisterCode record);

    int updateByPrimaryKey(RegisterCode record);

    int countAll();
}