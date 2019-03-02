package controller;

import enums.StuSelectEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.CourseService;
import service.PerClassCourseService;
import service.StuSelectService;
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
public class StuSelectController {

    private String iSsubmitSucessful = "";

    @Autowired
    StuSelectService stuSelectService;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    PerClassCourseService perClassCourseService;

    /**
     * 目前暂时只是针对手动添加
     * @param request
     * @return 成功与否
     * @throws Exception
     */
    @RequestMapping("/mapping-select-add")
    @ResponseBody
    public String stuSelectAdd(HttpServletRequest request) throws Exception {
        try {

//            TODO 重复选课，管理员的问题，看要不要改

        iSsubmitSucessful = "";
        String stuId = request.getParameter("student_id");
        String perCourseId = request.getParameter("per_course_id");

        PerClassCourse perClassCourse = perClassCourseService.selectByPrimaryId(Integer.valueOf(perCourseId));
        Course course = courseService.selectById(perClassCourse.getCourseId());
        Student student = studentService.selectById(Integer.valueOf(stuId));
        stuSelectService.insert(new StuSelect(student,course));

//        String parentName = request.getParameter("parent_name");
//        String mobile = request.getParameter("mobile");
//        String grade = request.getParameter("grade");
//        String classNow = request.getParameter("class_now");
//        String courseName = request.getParameter("course");

////       未指定学生id，查找年级、班级、父母名称一样的，有就证明为同一个人，使用该Student；若对不上新建学生并插入
//        Student student = studentService.selectWithoutId(stuName,grade,classNow,parentName);
//        List<Course> courseList = courseService.selectByCName(courseName);
////            找得到学生，找得到对应课程，信息填上
//        if (courseList.size()>0&&student!=null){
//            StuSelect stuSelect=new StuSelect(student,courseList.get(0));
//            //           插入数据库
//            stuSelectService.insert(stuSelect);
//        }else if (courseList.size()==0&&student!=null){
////                找得到学生，找不到课程，课程相应信息按前端传来的填
//            StuSelect stuSelect=new StuSelect(student,new Course(null,courseName,null));
//            //           插入数据库
//            stuSelectService.insert(stuSelect);
//        }else if (courseList.size()>0&&student==null){
////                找不到学生，找得到课程，新建学生并插入数据库
//            Student newStudent = new Student(Integer.valueOf(StuId), stuName, grade, classNow, parentName, mobile, FileuploadController.studentHeadImg);
//            StuSelect stuSelect=new StuSelect(newStudent,courseList.get(0));
//            //           插入数据库
//            studentService.insert(newStudent);
//            stuSelectService.insert(stuSelect);
//        }else if (courseList.size()==0&&student==null){
////                找不到学生，得不到课程，新建学生并插入数据库,课程相应信息按前端传来的填
//            Student newStudent = new Student(null, stuName, grade, classNow, parentName, mobile, FileuploadController.studentHeadImg);
//            StuSelect stuSelect=new StuSelect(newStudent,new Course(null,courseName,null));
//            //           插入数据库
//            studentService.insert(newStudent);
//            stuSelectService.insert(stuSelect);
//        }
        }catch (Exception e){
            throw new Exception("StudentController stuSelectAdd: 输入的学号或者课程列表的ID不对");
        }
        iSsubmitSucessful = "success";
        return iSsubmitSucessful;
    }

    /**
     * 更新
     * @param id 主键
     * @param course 要更新的课程名
     * @return
     * @throws Exception
     */
    @RequestMapping("/mapping-select-update")
    @ResponseBody
    public String stuSelectUpdate(String id,String course) throws Exception {
        StuSelect stuSelect = stuSelectService.selectByPrimaryId(Integer.valueOf(id));
        if (stuSelect!=null) {
            stuSelect.setcName(course);
            stuSelectService.update(stuSelect);
        }else
            return "failure";
        return "success";
    }

    @RequestMapping("mapping-select-del")
    @ResponseBody
    public String del(String perId, String[] ids) {
        int result = 0;
        if (perId != null)
            result += stuSelectService.delById(Integer.valueOf(perId));
        else
            for (int i = 0; i < ids.length; i++)
                result += stuSelectService.delById(Integer.valueOf(ids[i]));

        if (result == 1 || ids==null|| result == ids.length)
            return "success";
        else
            return "failure";
    }

    /**
     * 前端数据的展示，和datatable对接
     * @param request
     * @return json数据
     */
    @RequestMapping("/loadStudentSelect")
    @ResponseBody
    public String showStudentSelect(HttpServletRequest request) {
        List<StuSelect> stuSelectArrayList = new ArrayList<StuSelect>();
        String orderColumn = request.getParameter("order[0][column]");
        String order = request.getParameter("order[0][dir]");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        DatatablePage<StuSelect> data = new DatatablePage<StuSelect>();
        //search中文编码处理
        String search = "";
        try {
            search = search = EncodingUtils.useToSearch(search,request);

            Connection conn = ConnectDB.getConnection();
            String orderString3 = "order by " + StuSelectEnum.getNameByIndex(Integer.valueOf(orderColumn)) + " " + order + " ";
            String where = "where CONCAT(student_card,stu_name, grade,class_now,parent_name,parent_phone,c_name,select_time) LIKE '%" + search + "%'";

            if ("".equals(search))
                where="";
            String sql3 =
                    "select * " +
                    "from stu_select " +
                    where +
                    " " +
                    orderString3 +
                    "LIMIT " + start + " ," + length;

//                拼凑出StudentShow

//              查找数据库
            Statement pstm = conn.createStatement();
            ResultSet rs = pstm.executeQuery(sql3);
            while (rs.next()) {
                StuSelect stuSelect = new StuSelect(
                        rs.getInt("id"),
                        rs.getString("student_card"),
                        rs.getString("stu_name"),
                        rs.getString("grade"),
                        rs.getString("class_now"),
                        rs.getString("parent_name"),
                        rs.getString("parent_phone"),
                        rs.getString("head_img"),
                        rs.getInt("course_id"),
                        rs.getString("c_name"),
                        rs.getString("c_desc"),
                        rs.getDate("select_time")
                );//end CourseShow

//                    查有
                stuSelectArrayList.add(stuSelect);
            }//end while
            System.out.println(sql3);

//            过滤后的总记录数
            data.setRecordsFiltered(stuSelectService.countAll());
//            总记录数
//            data.setRecordsTotal(perClassCourseService.countAll()-1);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        draw: 表示请求次数
        data.setDraw(Integer.valueOf(request.getParameter("draw")) + 1);

//        data: 具体的数据对象数组
        data.setData(stuSelectArrayList);
        System.out.println(JsonUtils.objectToJson(data));
        return JsonUtils.objectToJson(data);

    }



}
