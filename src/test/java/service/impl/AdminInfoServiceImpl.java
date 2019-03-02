package service.impl;

import dao.mapper.AdminInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.AdminInfo;
import service.AdminInfoService;

@Service
public class AdminInfoServiceImpl implements AdminInfoService {

    @Autowired
    AdminInfoMapper adminInfoMapper;

    public AdminInfo select(){
        return adminInfoMapper.selectByPrimaryKey(1);
    }

    public int update(AdminInfo adminInfo){
        return adminInfoMapper.updateByPrimaryKey(adminInfo);
    }
}
