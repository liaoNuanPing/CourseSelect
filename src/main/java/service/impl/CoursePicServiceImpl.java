package service.impl;

import dao.mapper.CoursePicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.CoursePic;
import pojo.CoursePicExample;
import service.CoursePicService;

import java.util.List;

@Service
public class CoursePicServiceImpl implements CoursePicService {

    @Autowired
    CoursePicMapper coursePicMapper;


    public List<CoursePic> selectCoursePicByCourseId(Integer id){
        CoursePicExample example=new CoursePicExample();
        CoursePicExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(id);
        List<CoursePic> coursePicList = coursePicMapper.selectByExample(example);
        return coursePicList;
    }

    public int insert(CoursePic coursePic){
        return coursePicMapper.insert(coursePic);
    }

    public int delById(Integer id){
        return coursePicMapper.deleteByPrimaryKey(id);

    }

}
