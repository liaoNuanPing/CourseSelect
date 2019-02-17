package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Student;
import service.IdentityAuditService;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IdentityAuditController {

    @Autowired
    IdentityAuditService identityAuditService;


    /**
    * 身份审核提交
    * */
    @RequestMapping(value="/Audit")
    @ResponseBody
    public String identityAudit(@RequestBody String jsonFromFront){
        System.out.println("IdentityAuditController identityAudit");
        System.out.println("前端传来："+jsonFromFront);


//        Map<String,String> map= JsonUtils.jsonToPojo(jsonFromFront,HashMap.class);
//
//        String parentName=map.get("parentName");
//        String parentPhone=map.get("parentPhone");
//        String stuName=map.get("stuName");
//        String classNow=map.get("childClass");
//        Integer id=0;
//        String grade="";
//        String headImg="";
//
//        Student student=new Student(id, stuName,  grade, classNow, parentName, parentPhone, headImg);

        Student student=new Student(1, "33",  "", "", "55", "", "");

        int isSuccessful = identityAuditService.isInsertDatabaseSuccessful(student);
        List<Map<String, Object>> classList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 2; i++) {
            List<String> picList = new ArrayList<String>();
            Map<String, Object> classmap = new HashMap<String, Object>();
            classList.add(classmap);
            classmap.put("className", "语文"+i);
            classmap.put("picUrl", picList);

            picList.add("www1"+i);
            picList.add("www2"+i);
            picList.add("www3"+i);
        }


//        Map<String,String> map=new HashMap<String, String>();
//        map.put("isSuccessful",String.valueOf( isSuccessful));
//        map.put("message",isSuccessful==0?"插入异常，可能是主键冲突":"");
//        return JsonUtils.objectToJson(map);
        return JsonUtils.objectToJson(classList);
    }



}
