package service;

import pojo.IdentityAuditing;
import pojo.WxStudent;

public interface WxStudentService {

    WxStudent selectByOpenId(String openId);

    WxStudent selectByAuditingId(Integer auditId);
    int insert(WxStudent wxStudent);

    int update(WxStudent wxStudent);


}
