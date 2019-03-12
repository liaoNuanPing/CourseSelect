package controller;

import consts.Path;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileuploadController {



    public static List<String> fileNmaeList=new ArrayList<String>();
    public static String studentHeadImg="";
    private static String uid="";
    private static String uidStu="";

    /**
     *  上传课程图片
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadImage")
    @ResponseBody
    public String uploadImage(HttpServletRequest request) throws Exception {
//       重制uid
        if (!uid.equals(request.getParameter("uid"))) {
            uid = request.getParameter("uid");
            fileNmaeList = new ArrayList<String>();
        }

        String realPath = Path.getTempPath();

//      不对错误try catch，以便抛出异常停止上传
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        //        req.getFile("前端的name");
        MultipartFile multipartFile = req.getFile("file");
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String Oname = multipartFile.getOriginalFilename();
        //        检查格式
        String format = multipartFile.getOriginalFilename().substring(Oname.lastIndexOf('.')+1, Oname.length());
        if (!format.equals("jpg")&&!format.equals("jpeg")&&!format.equals("png"))
            throw new Exception();
        String fname = String.valueOf(System.currentTimeMillis()) +"course."+ format;
        File file = new File(realPath, fname);
        multipartFile.transferTo(file);
        fileNmaeList.add(fname);

//      throw new Exception();
        return "500";

    }

    /**
     * 学生合照手动上传
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadStudentImage")
    @ResponseBody
    public String uploadStudentImageByHand(HttpServletRequest request) throws Exception {
        studentHeadImg="";

        String realPath = Path.getTempPath();

//      不对错误try catch，以便抛出异常停止上传
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        //        req.getFile("前端的name");
        MultipartFile multipartFile = req.getFile("file");
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String Oname = multipartFile.getOriginalFilename();
        //        检查格式

        String format = multipartFile.getOriginalFilename().substring(Oname.lastIndexOf('.')+1, Oname.length());
        if (!format.equals("jpg")&&!format.equals("jpeg")&&!format.equals("png"))
            throw new Exception();
        String fname = String.valueOf(System.currentTimeMillis()) +"stuHandMake."+format;
        File file = new File(realPath, fname);
        multipartFile.transferTo(file);
        studentHeadImg=fname;

//      throw new Exception();
        return "200";

    }

    /**
     * 学生合照微信上传
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/wx-uploadStuHeadImage")
    @ResponseBody
    public String uploadStudentImageByWx(HttpServletRequest request) throws Exception {

        String realPath = Path.getTempPath();

//      不对错误try catch，以便抛出异常停止上传
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        //        req.getFile("前端的name");
        MultipartFile multipartFile = req.getFile("file");
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String Oname = multipartFile.getOriginalFilename();
        //        检查格式

        String format = multipartFile.getOriginalFilename().substring(Oname.lastIndexOf('.')+1, Oname.length());
        if (!format.equals("jpg")&&!format.equals("jpeg")&&!format.equals("png"))
            throw new Exception();
        String fname = String.valueOf(System.currentTimeMillis()) +"stu"+request.getParameter("openId")+"."+format;
        File file = new File(realPath, fname);
        multipartFile.transferTo(file);

//      throw new Exception();
        return "200";

    }
}
