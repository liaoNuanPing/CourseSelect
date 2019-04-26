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

    public int insert(Teacher teacher) {
       return teacherMapper.insert(teacher);
    }

    public int delById(Integer id) {
        return teacherMapper.deleteByPrimaryKey(id);
    }

    public int countAll() {
        return teacherMapper.countAll();
    }

    public Teacher selectById(Integer id) {
        teacherMapper.selectByPrimaryKey(id);
        return null;
    }
}
