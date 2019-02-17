package controller.test;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReceptImage {

    public ModelAndView uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        System.out.println("进入get方法！");
//
//        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
//        //        req.getFile("前端的name");
//        MultipartFile multipartFile =  req.getFile("file");
//
//        String realPath = "../webapp/static/images";
//        try {
//            File dir = new File(realPath);
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//            File file  =  new File(realPath,"test.jpg");
//            multipartFile.transferTo(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
        return null;

    }
}
