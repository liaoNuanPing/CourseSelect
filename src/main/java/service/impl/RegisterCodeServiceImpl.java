package service.impl;

import dao.mapper.RegisterCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.RegisterCode;
import pojo.RegisterCodeExample;
import service.RegisterCodeService;

import java.util.List;

@Service
public class RegisterCodeServiceImpl implements RegisterCodeService {
    @Autowired
    RegisterCodeMapper registerCodeMapper;

    public int countAll(){
        return registerCodeMapper.countAll();
    }

    public int delById(String code){return registerCodeMapper.deleteByPrimaryKey(code);}

    public int insert(RegisterCode registerCode){return registerCodeMapper.insert(registerCode);}

    public RegisterCode selectByCode(String code){
        return registerCodeMapper.selectByPrimaryKey(code);
    }

    @Override
    public List<RegisterCode> selectEnableCode() {
        RegisterCodeExample example=new RegisterCodeExample();
        RegisterCodeExample.Criteria criteria = example.createCriteria();
        criteria.andDisableEqualTo("false");
        return registerCodeMapper.selectByExample(example);
    }
}
