package service;

import pojo.StuSelect;

public interface StuSelectService {
    int countAll();

    int insert(StuSelect stuSelect);

    StuSelect selectByPrimaryId(Integer id);

    int delById(Integer id);

    int update(StuSelect stuSelect);
}
