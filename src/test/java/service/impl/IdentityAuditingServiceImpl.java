package service.impl;

import dao.mapper.IdentityAuditingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.IdentityAuditing;
import pojo.Student;
import service.IdentityAuditingService;
import service.StudentService;

@Service
public class IdentityAuditingServiceImpl implements IdentityAuditingService {

    @Autowired
    IdentityAuditingMapper identityAuditingMapper;

    @Autowired
    StudentService studentService;

    public int countAll(){
        return identityAuditingMapper.countAll();
    }

    public IdentityAuditing selectById(Integer id){
        return identityAuditingMapper.selectByPrimaryKey(id);
    }

    public int delById(Integer id){
        return identityAuditingMapper.deleteByPrimaryKey(id);
    }

    public int update(IdentityAuditing identityAuditing){
        return identityAuditingMapper.updateByPrimaryKey(identityAuditing);
    }

    public void updateSelfAndInsertStudent(IdentityAuditing identityAuditing, Student student){
        identityAuditingMapper.updateByPrimaryKey(identityAuditing);
        studentService.insert(student);
    }

    public int insert(IdentityAuditing identityAuditing){
        return identityAuditingMapper.insert(identityAuditing);
    }

}
