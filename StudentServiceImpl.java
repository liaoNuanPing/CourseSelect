package service.impl;

import dao.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Student;
import pojo.StudentExample;
import service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

//    public Student selectWithoutId(String stuName,String grade,String classNow,String parentName){
//        return studentMapper.selectWithoutId(stuName,grade,classNow,parentName);
//    }

    public int insert(Student student){
        return studentMapper.insert(student);
    }

    public int delById(Integer id){
        return studentMapper.deleteByPrimaryKey(id);
    }

    public int delWxStuById(Integer id){
        return studentMapper.deleteWxStudentByPrimaryKey(id);
    }

    public Student selectById(Integer id){
        return studentMapper.selectByPrimaryKey(id);
    }

    public int countAll(){
        StudentExample examplp=new StudentExample();
        StudentExample.Criteria criteria = examplp.createCriteria();
        criteria.andIdNotEqualTo(-1);
        return studentMapper.countByExample(examplp);
    }


    public int updateAllStudentGradeUp(Integer num,Integer grade){
        return studentMapper.updateAllStudentGradeUp(num,grade);
    }
    public int updateAllStudentGradeDown(Integer num){
        return studentMapper.updateAllStudentGradeDown(num);
    }




}
