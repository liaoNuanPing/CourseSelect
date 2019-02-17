package service;

import pojo.Student;

public interface IdentityAuditService {

    /**
     *
     * @param student 学生信息
     * @return 成功与否
     */
    int isInsertDatabaseSuccessful(Student student);
}
