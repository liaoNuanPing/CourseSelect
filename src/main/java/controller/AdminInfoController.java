package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.AdminInfo;
import pojo.DatatablePage;
import service.AdminInfoService;
import utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminInfoController {

    @Autowired
    AdminInfoService adminInfoService;

    @RequestMapping("/loadAdmin")
    @ResponseBody
    public String adminShowc(){
        DatatablePage<AdminInfo> data = new DatatablePage<AdminInfo>();
        List<AdminInfo> list=new ArrayList<AdminInfo>();
        AdminInfo adminInfo = adminInfoService.select();
//        TODO mysql时间戳转化成java时间
        adminInfo.setLastLoginTime((adminInfo.getLastLoginTime()));
        list.add(adminInfo);
        data.setRecordsTotal(1);
        data.setRecordsFiltered(1);
        data.setData(list);
        System.out.println(JsonUtils.objectToJson(data));
        return JsonUtils.objectToJson(data);
    }

    @RequestMapping("/mapping-admin-update")
    @ResponseBody
    public String update(HttpServletRequest request){

        AdminInfo admin = adminInfoService.select();
        adminInfoService.update(new AdminInfo(1,
                request.getParameter("login_name"),
                request.getParameter("password"),
                request.getParameter("ic_name"),
                request.getParameter("email"),
                request.getParameter("mobile"),
                null,
                admin.getThisLoginTime()
                ));
        return "success";

    }
}
