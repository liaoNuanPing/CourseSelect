package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.JsonUtils;
import utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ConfigController {

    @RequestMapping("/mapping-config-term-update")
    @ResponseBody
    public String termUpdate(String term){
        PropertiesUtils.modifyProperties("config.properties","term",term);
        return "success";
    }

    @RequestMapping("/mapping-config-term-show")
    @ResponseBody
    public String termShow(){
        return PropertiesUtils.getPropertiesValue ("config.properties","term");
    }

    @RequestMapping("/mapping-config-classes-update")
    @ResponseBody
    public String classesUpdate(String classes){
        PropertiesUtils.modifyProperties("config.properties","classes",classes);
        return "success";
    }

    @RequestMapping("/mapping-config-classes-show")
    @ResponseBody
    public String classesShow(){
        return PropertiesUtils.getPropertiesValue ("config.properties","classes");
    }


    @RequestMapping("/mapping-config-grade-update")
    @ResponseBody
    public String gradeUpdate(String grade){
        PropertiesUtils.modifyProperties("config.properties","grade",grade);
        return "success";
    }

    @RequestMapping("/mapping-config-grade-show")
    @ResponseBody
    public String gradeShow(){
        return PropertiesUtils.getPropertiesValue ("config.properties","grade");
    }

    @RequestMapping("/mapping-config-all-show")
    @ResponseBody
    public String allShow(HttpServletRequest request){
        List<String> list=new ArrayList<String>();
        list.add(PropertiesUtils.getPropertiesValue ("config.properties","term"));
        list.add(PropertiesUtils.getPropertiesValue ("config.properties","classes"));
        list.add(PropertiesUtils.getPropertiesValue ("config.properties","grade"));

        list.add(String.valueOf(request.getScheme()));
        list.add(String.valueOf(request.getServerName()));
        list.add(String.valueOf(request.getServerPort()));
        list.add(String.valueOf(request.getContextPath()));

        System.out.println(JsonUtils.objectToJson(list));
        return JsonUtils.objectToJson(list);
    }


}

