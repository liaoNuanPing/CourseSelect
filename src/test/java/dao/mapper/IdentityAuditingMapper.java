package dao.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.IdentityAuditing;
import pojo.IdentityAuditingExample;

import java.util.List;

public interface IdentityAuditingMapper {
    int countByExample(IdentityAuditingExample example);

    int deleteByExample(IdentityAuditingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IdentityAuditing record);

    int insertSelective(IdentityAuditing record);

    List<IdentityAuditing> selectByExample(IdentityAuditingExample example);

    IdentityAuditing selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IdentityAuditing record, @Param("example") IdentityAuditingExample example);

    int updateByExample(@Param("record") IdentityAuditing record, @Param("example") IdentityAuditingExample example);

    int updateByPrimaryKeySelective(IdentityAuditing record);

    int updateByPrimaryKey(IdentityAuditing record);

    int countAll();
}