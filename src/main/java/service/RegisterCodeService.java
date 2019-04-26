package service;


import pojo.RegisterCode;

import java.util.List;

public interface RegisterCodeService {

    int countAll();

    int delById(Integer id);

    int insert(RegisterCode registerCode);

    List<RegisterCode> selectEnableCode();
}
