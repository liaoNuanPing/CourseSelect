package service.impl;

import dao.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Teacher;
import service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public int insert(Teacher teacher) {
       return teacherMapper.insert(teacher);
    }

    @Override
    public int delById(Integer id) {
        return teacherMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int countAll() {
        return teacherMapper.countAll();
    }

    @Override
    public Teacher selectById(Integer id) {
        teacherMapper.selectByPrimaryKey(id);
        return null;
    }
}
