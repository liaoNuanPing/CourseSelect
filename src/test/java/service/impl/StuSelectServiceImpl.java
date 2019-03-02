package service.impl;

import dao.mapper.StuSelectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.StuSelect;
import pojo.StuSelectExample;
import service.StuSelectService;

import java.util.List;

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

    public List<StuSelect> selectByStudentId(Integer stu_id){
        StuSelectExample example=new StuSelectExample();
        StuSelectExample.Criteria criteria = example.createCriteria();
        criteria.andStudentCardEqualTo(String.valueOf(stu_id));
        return stuSelectMapper.selectByExample(example);
    }

    public List<StuSelect> selectByStudentIdAndTerm(Integer stu_id,String term){
        StuSelectExample example=new StuSelectExample();
        StuSelectExample.Criteria criteria = example.createCriteria();
        criteria.andStudentCardEqualTo(String.valueOf(stu_id));
        criteria.andTermEqualTo(term);
        return stuSelectMapper.selectByExample(example);
    }

}
