package controller;

import enums.StudentEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.DatatablePage;
import pojo.Student;
import service.StudentService;
import utils.ConnectDB;
import utils.EncodingUtils;
import utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
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
    public String shudentPageShow(String id) {
        String json= JsonUtils.objectToJson( studentService.selectById(Integer.valueOf(id)));
        System.out.println(json);
        return json;
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
            String where = "where CONCAT(student_card,stu_name, grade,class_now,parent_name,parent_phone) LIKE '%" + search + "%'";

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
                        rs.getString("head_img")
                );//end CourseShow

//                    查有
                studentList.add(stuSelect);
            }//end while
            System.out.println(sql3);

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
        System.out.println(JsonUtils.objectToJson(data));
        return JsonUtils.objectToJson(data);

    }


    @RequestMapping("/mapping-student-add")
    @ResponseBody
    public String handMakeAdd(HttpServletRequest request) throws Exception{
        String stuId = request.getParameter("student_id");
        String stuName = request.getParameter("student_name");
        String parentName = request.getParameter("parent_name");
        String mobile = request.getParameter("mobile");
        String grade = request.getParameter("grade");
        String classNow = request.getParameter("class_now");
        String img="".equals(FileuploadController.studentHeadImg)?"":"static/images/"+FileuploadController.studentHeadImg;
        Student student=new Student("".equals(stuId)?null:Integer.valueOf(stuId),stuName,grade,classNow,parentName,mobile,img);
//        上传图片的链接只做一次，用完重制
        FileuploadController.studentHeadImg="";
        studentService.insert(student);
        return "success";
    }

    @RequestMapping("/mapping-student-update")
    @ResponseBody
    public String update(HttpServletRequest request) throws Exception{
        String oldId = request.getParameter("id");
        String newId = request.getParameter("student_id");
        String stuName = request.getParameter("student_name");
        String parentName = request.getParameter("parent_name");
        String mobile = request.getParameter("mobile");
        String grade = request.getParameter("grade");
        String classNow = request.getParameter("class_now");

        Student oStudent = studentService.selectById(Integer.valueOf(oldId));
        Student nStudent=new Student(Integer.valueOf(newId),stuName,grade,classNow,parentName,mobile,"".equals(FileuploadController.studentHeadImg)?oStudent.getHeadImg():FileuploadController.studentHeadImg);

//        插入新的删除旧的，相当于更新
//        TODO 原子操作
        studentService.delById(Integer.valueOf(oldId));
        studentService.insert(nStudent);
        return "success";

    }

    @RequestMapping("/mapping-student-del")
    @ResponseBody
    public String del(String perId, String[] ids) throws Exception{
        int result = 0;
        if (perId != null)
            result += studentService.delById(Integer.valueOf(perId));
        else
            for (int i = 0; i < ids.length; i++)
                result += studentService.delById(Integer.valueOf(ids[i]));

        if (result == 1 || ids==null|| result == ids.length)
            return "success";
        else
            return "failure";
    }

}
