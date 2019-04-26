package service.impl;

import org.springframework.stereotype.Service;
import pojo.TeacherInfo;
import service.TeacherInfoService;

@Service
public class TeacherInfoServiceImpl implements TeacherInfoService {

    @Override
    public int insert(TeacherInfo teacher_info) {
        return 0;
    }

    @Override
    public int delById(Integer id) {
        return 0;
    }

    @Override
    public int countAll() {
        return 0;
    }

    @Override
    public TeacherInfo selectById(Integer id) {
        return null;
    }
}
