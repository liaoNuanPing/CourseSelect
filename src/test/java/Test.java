package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Test {

    @org.junit.Test
    public void t() throws Exception{
        throw new Exception("移动图片从temp到images不成功");

    }
}
