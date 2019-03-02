package service;

import pojo.IdentityAuditing;
import pojo.Student;

public interface IdentityAuditingService {

    int countAll();

    int update(IdentityAuditing identityAuditing);

    IdentityAuditing selectById(Integer id);

    int delById(Integer id);

    int insert(IdentityAuditing identityAuditing);

    void updateSelfAndInsertStudent(IdentityAuditing identityAuditing, Student student);
}
