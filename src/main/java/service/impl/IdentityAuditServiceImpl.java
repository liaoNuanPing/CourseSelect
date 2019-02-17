package service.impl;

import dao.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Student;
import service.IdentityAuditService;


@Service
public class IdentityAuditServiceImpl implements IdentityAuditService {



    @Autowired
    StudentMapper studentMapper;




    public int isInsertDatabaseSuccessful(Student student) {
        int result=0;
        try{
            result= studentMapper.insert(student);
        }catch (Exception e){
            result=0;
        }


        return result;
    }
}
