package service;

import pojo.WxStudent;

public interface WxStudentService {

    WxStudent selectByOpenId(String openId);
}
