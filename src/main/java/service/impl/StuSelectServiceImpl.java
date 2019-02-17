package service.impl;

import dao.mapper.StuSelectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.StuSelect;
import service.StuSelectService;

@Service
public class StuSelectServiceImpl implements StuSelectService{

    @Autowired
    StuSelectMapper stuSelectMapper;

    public int countAll(){
        return stuSelectMapper.countAll();
    }

    public int insert(StuSelect stuSelect){
        return stuSelectMapper.insert(stuSelect);
    }

    public StuSelect selectByPrimaryId(Integer id){
        return stuSelectMapper.selectByPrimaryKey(id);
    }

    public int delById(Integer id){
        return stuSelectMapper.deleteByPrimaryKey(id);
    }

    public int update(StuSelect stuSelect){
        return stuSelectMapper.updateByPrimaryKey(stuSelect);
    }
}
