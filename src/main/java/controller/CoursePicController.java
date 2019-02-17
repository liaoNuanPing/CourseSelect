package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.CoursePic;
import service.CoursePicService;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CoursePicController {

    @Autowired
    CoursePicService coursePicService;

    @RequestMapping("/select-picture-show")
    @ResponseBody
    public String showCoursePic(@RequestParam(name = "ids",defaultValue = "0") int ids){
        System.out.println("id="+ids);
        List<CoursePic> coursePicList = coursePicService.selectCoursePic(ids);
        System.out.println(JsonUtils.objectToJson(coursePicList));
        return JsonUtils.objectToJson(coursePicList);
    }

    @RequestMapping("/mapping-picture-add")
    @ResponseBody
    public String add(String courseId) throws Exception{
        System.out.println("courseId="+courseId);
         for(String pic:FileuploadController.fileNmaeList){
             coursePicService.insert(new CoursePic(null,Integer.valueOf(courseId),"".equals(pic)?"":"static/images/"+pic));
         }
        FileuploadController.fileNmaeList=new ArrayList<String>();
         return "success";
    }

    @RequestMapping("/mapping-picture-del")
    @ResponseBody
    public String del(String perId, String[] ids) throws Exception{
        int result = 0;
        if (perId != null)
            result += coursePicService.delById(Integer.valueOf(perId));
        else
            for (int i = 0; i < ids.length; i++)
                result += coursePicService.delById(Integer.valueOf(ids[i]));

        if (result == 1 || ids==null|| result == ids.length)
            return "success";
        else
            return "failure";
    }
}
