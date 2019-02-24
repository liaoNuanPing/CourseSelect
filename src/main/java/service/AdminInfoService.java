package service;

import pojo.AdminInfo;

public interface AdminInfoService {
    AdminInfo select();

    int update(AdminInfo adminInfo);
}
