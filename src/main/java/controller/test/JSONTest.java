package controller.test;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class JSONTest {


    @RequestMapping(method = RequestMethod.POST, value = "/ObjectToJson")
    @ResponseBody
    public String ObjectToJson(@RequestBody String mes) {
        System.out.println("JSONTest ObjectToJson");
        System.out.println(mes);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        list.add(map);
        map.put("id", "233");
        map.put("name", "lrw");
        System.out.println("结果："+JSONUtils.toJSONString(list));
        System.out.println("---------------------------");
        return JSONUtils.toJSONString(list);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ObjectToJson2")
    @ResponseBody
    public String ObjectToJson2(@RequestBody String mes) {
        System.out.println("JSONTest ObjectToJson2");
        System.out.println(mes);
//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
//        list.add(map);
        map.put("id", "1611");
        map.put("name", "111");
        System.out.println("结果："+JsonUtils.objectToJson(map));
        System.out.println("---------------------------");
        return JsonUtils.objectToJson(map);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/JsonToObject")
    @ResponseBody
    public String JsonToObject(@RequestBody String mes) {
        System.out.println("JSONTest JsonToObject");
        System.out.println(mes);

        HashMap map=JsonUtils.jsonToPojo(mes,HashMap.class);
        System.out.println("结果："+map.toString());
        System.out.println(map.get("id"));
        System.out.println("---------------------------");

        return null;
    }
}
