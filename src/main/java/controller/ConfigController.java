package controller;

import consts.Url;
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

        list.add(String.valueOf(Url.getScheme(request)));
        list.add(String.valueOf(Url.getServerName(request)));
        list.add(String.valueOf(Url.getPort(request)));
        list.add(String.valueOf(Url.getContextPath(request)));
        list.add(String.valueOf(Url.getUrl(request)));

        list.add(PropertiesUtils.getPropertiesValue ("config.properties","min_date"));
        list.add(PropertiesUtils.getPropertiesValue ("config.properties","max_date"));

        return JsonUtils.objectToJson(list);
    }

    @RequestMapping("/mapping-config-date-modify")
    @ResponseBody
    public String selectDateUpdate(String minDate,String maxDate){
        PropertiesUtils.modifyProperties("config.properties","min_date",minDate);
        PropertiesUtils.modifyProperties("config.properties","max_date",maxDate);

        return "success";
    }

}

