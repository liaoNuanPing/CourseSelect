package controller;

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
        System.out.println("uploadImage");
//       重制uid
        if (!uid.equals(request.getParameter("uid"))) {
            uid = request.getParameter("uid");
            fileNmaeList = new ArrayList<String>();
        }

//        List<MultipartFile> fileList = req.getFiles("file");

//        out static目录
        String classesPath = this.getClass().getClassLoader().getResource("/").getPath();
        String[] rootPath = classesPath.split("WEB-INF");
        String realPath = rootPath[0] + "static/images";

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
        String fname = String.valueOf(System.currentTimeMillis()) + Oname.substring(Oname.length() - 10, Oname.length());
        File file = new File(realPath, fname);
        multipartFile.transferTo(file);
        fileNmaeList.add(fname);

//      throw new Exception();
        return "500";

    }

    /**
     * 学生合照
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadStudentImage")
    @ResponseBody
    public String uploadStudentImage(HttpServletRequest request) throws Exception {
        System.out.println("uploadStudentImage");
//       重制uid
        if (!uidStu.equals(request.getParameter("uid"))) {
            uidStu = request.getParameter("uid");
            studentHeadImg="";
        }


//        out static目录
        String classesPath = this.getClass().getClassLoader().getResource("/").getPath();
        String[] rootPath = classesPath.split("WEB-INF");
        String realPath = rootPath[0] + "static/images";

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
        String fname = String.valueOf(System.currentTimeMillis()) +"."+format;
        File file = new File(realPath, fname);
        multipartFile.transferTo(file);
        studentHeadImg=fname;

//      throw new Exception();
        return "500";

    }

}
