package controller;

import enums.CourseEnum;
import enums.CourseShowEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.CoursePicService;
import service.CourseService;
import utils.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional(rollbackFor = { Exception.class })
public class CourseController {
    Logger logger=LoggerUtlis.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;

    @Autowired
    CoursePicService coursePicService;

    @RequestMapping(value = {"loadCourse"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String showCourse(HttpServletRequest request) {
        List<Course> courseList = new ArrayList<Course>();
        String orderColumn = request.getParameter("order[0][column]");
        String order = request.getParameter("order[0][dir]");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        DatatablePage<Course> data = new DatatablePage<Course>();
        //search中文编码处理
        String search = "";
        try {
            search= EncodingUtils.useToSearch(search,request);
            Connection conn = ConnectDB.getConnection();

            String orderString3 = "order by " + CourseEnum.getNameByIndex(Integer.valueOf(orderColumn)) + " " + order + " ";
            String sql3 =
                    "select * " +
                            "from course " +
                            "where CONCAT(id,c_name,c_desc) " +
                            "LIKE '%" + search + "%' " +
                            orderString3 +
                            " LIMIT " + start + " ," + length;

//                拼凑出CourseShow

//              查找数据库
            Statement pstm = conn.createStatement();
            ResultSet rs = pstm.executeQuery(sql3);
            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("c_name"),
                        rs.getString("c_desc")
                );//end CourseShow
                courseList.add(course);
            }//end while

//            过滤后的总记录数
            data.setRecordsFiltered(courseService.countAll());
//            总记录数
//            data.setRecordsTotal(perClassCourseService.countAll()-1);
        } catch (Exception e) {
            logger.error("mapping-course-add-"+e.getMessage());
        }

//        draw: 表示请求次数
        data.setDraw(Integer.valueOf(request.getParameter("draw")) + 1);

//        data: 具体的数据对象数组
        data.setData(courseList);
        return JsonUtils.objectToJson(data);

    }

    /**
     * 添加课程
     *
     * @param request 前端传来的request
     * @return
     */
    @RequestMapping(value = {"mapping-course-add"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String perclassCourseAdd(HttpServletRequest request) throws Exception {
        try {
            String courseName = request.getParameter("course_name");
            String course_desc = request.getParameter("course_desc");
            Course course;

//          课程列表已有的
            List<Course> courseNameList = courseService.selectByCName(courseName);

//          如果Course已经有这个课程，报错
            if (courseNameList.size() > 0)
                return JsonUtils.objectToJson(new WxResultJson(0,"课程已经存在，不要添加重复的课程名"));

            //插入Course
            course = new Course(null, courseName, course_desc);
            if (courseService.insert(course) != 1)
                return JsonUtils.objectToJson(new WxResultJson(0,"课程添加失败"));

            //插入CoursePic
            if (FileuploadController.fileNmaeList.size() > 0) {
                for (int i = 0; i < FileuploadController.fileNmaeList.size(); i++) {
                    String originalName = FileuploadController.fileNmaeList.get(i);
                    String newImgName = PicUtils.getNewCourseImageName(originalName);
                    PicUtils.rename(originalName, newImgName);
                    CoursePic coursePic = new CoursePic(null, course.getId(), "static/images/" + newImgName);
                    if (coursePicService.insert(coursePic) != 1)
                        return JsonUtils.objectToJson(new WxResultJson(0,"课程图片插入失败"));
                }
//            清除，只用一次
                FileuploadController.fileNmaeList = new ArrayList<String>();
            }
        }catch (Exception e){
            logger.error("mapping-course-add-"+e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return JsonUtils.objectToJson(new WxResultJson(1, "success"));
    }

    @RequestMapping(value = {"/mapping-course-del"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String del(String perId, String[] ids) {
        try {
            if (perId != null) {
                courseService.delById(Integer.valueOf(perId));

            } else {
                for (int i = 0; i < ids.length; i++)
                    courseService.delById(Integer.valueOf(ids[i]));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonUtils.objectToJson(new WxResultJson(0,"课程图片删除失败"));
        }
        return JsonUtils.objectToJson(new WxResultJson(1,"success"));

    }



    @RequestMapping(value = {"/mapping-course-update"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String Update(HttpServletRequest request) throws Exception {
        try {
            String id = request.getParameter("id");
            String cName = request.getParameter("course_name");
            String cDsec = request.getParameter("course_desc");
            if (id == null)
                return JsonUtils.objectToJson(new WxResultJson(0, "课程id不能为空"));

            Course course = courseService.selectByPrimaryId(Integer.valueOf(id));
            if (course != null) {
                course.setcDesc(cDsec);
                course.setcName(cName);
                courseService.update(course);
            } else
                return JsonUtils.objectToJson(new WxResultJson(0, "课程不存在"));
        }catch (Exception e){
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonUtils.objectToJson(new WxResultJson(0, "更新错误"));
        }
        return JsonUtils.objectToJson(new WxResultJson(1,"success"));

    }

}
