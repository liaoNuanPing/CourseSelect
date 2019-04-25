package controller;

import consts.Path;
import enums.StudentEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.DatatablePage;
import pojo.Student;
import pojo.WxResultJson;
import service.StudentService;
import utils.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional(rollbackFor = { Exception.class })
public class StudentController {

    @Autowired
    StudentService studentService;

    /**
     * 点击姓名显示详情页面
     * @param id 学生id
     * @return json
     */
    @RequestMapping(value = {"/mapping-student-page-show"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String studentPageShow(String id) {
        try {
            String json= JsonUtils.objectToJson( studentService.selectById(Integer.valueOf(id)));
            return json;
        }catch (Exception e){
            return "";
        }
    }

    @RequestMapping(value = {"/loadStudent"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String allShudentShow(HttpServletRequest request) {
        List<Student> studentList = new ArrayList<Student>();
        String orderColumn = request.getParameter("order[0][column]");
        String order = request.getParameter("order[0][dir]");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        DatatablePage<Student> data = new DatatablePage<Student>();
        //search中文编码处理
        String search = "";
        try {
            search = EncodingUtils.useToSearch(search,request);

            Connection conn = ConnectDB.getConnection();
            String orderString3 = "order by " + StudentEnum.getNameByIndex(Integer.valueOf(orderColumn)) + " " + order + " ";
            String where = "where CONCAT(id,stu_name, grade,class_now,parent_name,parent_phone,parent_code) LIKE '%" + search + "%'";

            if ("".equals(search))
                where="";
            String sql3 =
                    "select * " +
                            "from student " +
                            where +
                            " " +
                            orderString3 +
                            "LIMIT " + start + " ," + length;

//                拼凑出StudentShow

//              查找数据库
            Statement pstm = conn.createStatement();
            ResultSet rs = pstm.executeQuery(sql3);
            while (rs.next()) {
                Student stuSelect = new Student(
                        rs.getInt("id"),
                        rs.getString("stu_name"),
                        rs.getString("grade"),
                        rs.getString("class_now"),
                        rs.getString("parent_name"),
                        rs.getString("parent_phone"),
                        rs.getString("parent_code"),
                        rs.getString("head_img")
                );//end CourseShow

//                    查有
                studentList.add(stuSelect);
            }//end while

//            过滤后的总记录数
            data.setRecordsFiltered(studentService.countAll());
//            总记录数
//            data.setRecordsTotal(perClassCourseService.countAll()-1);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        draw: 表示请求次数
        data.setDraw(Integer.valueOf(request.getParameter("draw")) + 1);

//        data: 具体的数据对象数组
        data.setData(studentList);
        return JsonUtils.objectToJson(data);

    }


    @RequestMapping(value = {"/mapping-student-hand-add"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String handMakeAdd(HttpServletRequest request) throws Exception {
        try {
            String stuId = request.getParameter("student_id");
            String stuName = request.getParameter("student_name") == null ? "" : request.getParameter("student_name");
            String parentName = request.getParameter("parent_name") == null ? "" : request.getParameter("parent_name");
            String mobile = request.getParameter("mobile");
            String parentCode = request.getParameter("parent_code");
            String grade = request.getParameter("grade");
            String classNow = request.getParameter("class_now");
            String newImgName = PicUtils.getNewCourseImageName(FileuploadController.studentHeadImg);
            PicUtils.rename(FileuploadController.studentHeadImg, newImgName);

            Student student = new Student("".equals(stuId) ? null : Integer.valueOf(stuId), stuName, grade, classNow, parentName, mobile,parentCode, "static/images/" + newImgName);
//        上传图片的链接只做一次，用完重制
            FileuploadController.studentHeadImg = "";
            studentService.insert(student);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LoggerUtlis.getLogger(this.getClass()).error("------mapping-student-hand-add-" + e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0, "添加错误"));
        }
        return JsonUtils.objectToJson(new WxResultJson(1, ""));
    }

    @RequestMapping(value = {"/mapping-student-update"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String update(HttpServletRequest request) throws Exception {
        try {
            String oldId = request.getParameter("id");
            String newId = request.getParameter("student_id");
            String stuName = request.getParameter("student_name");
            String parentName = request.getParameter("parent_name");
            String mobile = request.getParameter("mobile");
            String parentCode = request.getParameter("parent_code");
            String grade = request.getParameter("grade");
            String classNow = request.getParameter("classes");

            Student oStudent = studentService.selectById(Integer.valueOf(oldId));
            Student nStudent;

            if ("".equals(FileuploadController.studentHeadImg)) {
                nStudent = new Student(Integer.valueOf(newId), stuName, grade, classNow, parentName, mobile,parentCode, oStudent.getHeadImg());
            } else {
                String newImgName = PicUtils.getNewCourseImageName(FileuploadController.studentHeadImg);
                PicUtils.rename(FileuploadController.studentHeadImg, newImgName);
                FileuploadController.studentHeadImg="";
                nStudent = new Student(Integer.valueOf(newId), stuName, grade, classNow, parentName, mobile,parentCode, "static/images/"+newImgName);
            }

            studentService.delById(Integer.valueOf(oldId));
            studentService.insert(nStudent);

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LoggerUtlis.getLogger(this.getClass()).error("------mapping-student-update-" + e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0, "更新错误"));
        }
        return JsonUtils.objectToJson(new WxResultJson(1, ""));
    }

    @RequestMapping("/mapping-student-del")
    @ResponseBody
    public String del(String perId, String[] ids) throws Exception {
        try {
            int result = 0;
            if (perId != null) {
                Student student = studentService.selectById(Integer.valueOf(perId));
                result += studentService.delById(Integer.valueOf(perId));
                File file=new File(Path.getImagesPath()+"/"+student.getHeadImg());
                if (file.exists()&&file.isFile())
                    file.delete();
            }
            else
                for (int i = 0; i < ids.length; i++) {
                    Student student = studentService.selectById(Integer.valueOf(ids[i]));
                    result += studentService.delById(Integer.valueOf(ids[i]));
                    File file=new File(Path.getImagesPath()+"/"+student.getHeadImg());
                    if (file.exists()&&file.isFile())
                        file.delete();
                }

            if (result == 1 || ids == null || result == ids.length)
                return JsonUtils.objectToJson(new WxResultJson(1, ""));
            else
                return JsonUtils.objectToJson(new WxResultJson(0, "删除错误"));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LoggerUtlis.getLogger(this.getClass()).error("------mapping-student-hand-add-" + e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0, "删除错误"));
        }
    }


    @RequestMapping("/mapping-student-grade-modify")
    @ResponseBody
    public String modifyStudentGrade(String num){
//        TODO 返回值
        if ("A".equals(num))
            return String.valueOf( studentService.updateAllStudentGradeDown(-1));
        else if ("B".equals(num))
            return String.valueOf( studentService.updateAllStudentGradeUp(1,Integer.valueOf(PropertiesUtils.getPropertiesValue("config.properties","grade"))));
        else
            return "0";
    }


}
