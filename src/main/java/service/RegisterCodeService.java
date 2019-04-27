package service;


import org.springframework.stereotype.Service;
import pojo.RegisterCode;

import java.util.List;


public interface RegisterCodeService {

    int countAll();

    int delById(String code);

    int insert(RegisterCode registerCode);

    int update(RegisterCode registerCode);
    /**
     * 根据微信传来的code查找数据库
     * @param code 注册码
     * @return RegisterCode 返回注册码pojo
     */
    RegisterCode selectByCode(String code);

    List<RegisterCode> selectEnableCode();



}
