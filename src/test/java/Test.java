package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/mvc")
public class Test {

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("2233333");
        return "hello";
    }
    @RequestMapping("/String")
    @ResponseBody
    public String getString(){
        System.out.println("ResponseBody String");
        String value="this is 'ResponseBody value' responsed ";
        return value;
    }

    @RequestMapping("/List")
    @ResponseBody
    public ObjectMapper getList(){
        System.out.println("ResponseBody ObjectMapper");

        return new ObjectMapper();
    }
}
