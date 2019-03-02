package controller;

import consts.Path;
import enums.CourseShowEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.*;
import utils.ConnectDB;
import utils.EncodingUtils;
import utils.JsonUtils;
import utils.PicUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PerClassCourseController {

    @Autowired
    PerClassCourseService perClassCourseService;

    @Autowired
    CourseService courseService;

    @Autowired
    CoursePicService coursePicService;

    @Autowired
    WxStudentService wxStudentService;

    @Autowired
    StudentService studentService;


    private String isSubmitSuccessful = "";

    /**
     * 添加课程
     *
     * @param request 前端传来的request
     * @return
     */
    @RequestMapping("mapping-course-add")
    @ResponseBody
    public String perclassCourseAdd(HttpServletRequest request) throws Exception {
        isSubmitSuccessful = "";
        String courseName = request.getParameter("course_name");
        String grade = request.getParameter("grade");
        String term = request.getParameter("term");
        String classes = request.getParameter("classes");
        String total = request.getParameter("total_need");
        int totalNeed = "".equals(total) ? 9999 : Integer.valueOf(request.getParameter("total_need"));
        String course_desc = request.getParameter("course_desc");
        int courseId = -1;
        Course course;
//      课程列表已有的
        List<Course> courseNameList = courseService.selectByCName(courseName);
//        如果Course已经有这个课程，就不继续插入，直接利用,没有则创新的
        if (courseNameList.size() == 0) {
            //插入Course
            course = new Course(null, courseName, course_desc);
            if (courseService.insert(course) != 1)
                throw new Exception();
        } else
//            直接利用
            course = courseNameList.get(0);

        //插入PerClassCourse
        PerClassCourse perClassCourse = new PerClassCourse(null, course.getId(), grade, term, classes, totalNeed, 0);
        if (perClassCourseService.insert(perClassCourse) != 1)
            throw new Exception();

        //插入CoursePic
        if (FileuploadController.fileNmaeList.size() > 0) {
            for (int i = 0; i < FileuploadController.fileNmaeList.size(); i++) {
                String originalName = FileuploadController.fileNmaeList.get(i);
                String newImgName = PicUtils.getNewCourseImageName(originalName);
                    PicUtils.rename(originalName,newImgName);
                CoursePic coursePic = new CoursePic(null, course.getId(), "static/images/" + newImgName);
                    if (coursePicService.insert(coursePic) != 1)
                        throw new Exception("课程图片插入失败");
            }
//            清除，只用一次
            FileuploadController.fileNmaeList = new ArrayList<String>();
        }

        isSubmitSuccessful = "success";
        return isSubmitSuccessful;
    }

    @RequestMapping("/mapping-course-update")
    @ResponseBody
    public String Update(HttpServletRequest request ) throws Exception {
            PerClassCourse perClassCourse = perClassCourseService.selectByPrimaryId(Integer.valueOf(request.getParameter("id")));
            Course course = courseService.selectByPrimaryId(Integer.valueOf(request.getParameter("courseId")));
            if (course != null) {
                course.setcDesc(request.getParameter("course_desc"));
                course.setcName(request.getParameter("course_name"));
                courseService.update(course);
            }
            if (perClassCourse != null) {
                perClassCourse.setTerm(request.getParameter("term"));
                perClassCourse.setGrade(request.getParameter("grade"));
                perClassCourse.setToClass(request.getParameter("classes"));
                perClassCourse.setTotalNeedStuAmount(Integer.valueOf(request.getParameter("total_need")));
                perClassCourse.setHaveStuAmount(Integer.valueOf(request.getParameter("have")));
                perClassCourseService.update(perClassCourse);
            }
        return "success";
    }

    @RequestMapping("mapping-course-del")
    @ResponseBody
    public String del(String perId, String[] ids) {

        int result = 0;
        if (perId != null) {
            PerClassCourse perClassCourse = perClassCourseService.selectByPrimaryId(Integer.valueOf(perId));
            List<PerClassCourse> perClassCourseList = perClassCourseService.selectPerClassCourseListByCourseIdList(perClassCourse.getCourseId());
//           如果perClassCourse里的Course只剩下当前这个要删除的，一并删除Course

            if (perClassCourseList.size() <= 1)
                result += courseService.delById(perClassCourse.getCourseId());
            else
                result += perClassCourseService.delById(Integer.valueOf(perId));
        } else {
            for (int i = 0; i < ids.length; i++) {
                PerClassCourse perClassCourse = perClassCourseService.selectByPrimaryId(Integer.valueOf(ids[i]));
                List<PerClassCourse> perClassCourseList = perClassCourseService.selectPerClassCourseListByCourseIdList(perClassCourse.getCourseId());
//           如果perClassCourse里的Course只剩下当前这个要删除的，一并删除Course
                if (perClassCourseList.size() <= 1) {
                    result += courseService.delById(perClassCourse.getCourseId());
                } else
                    result += perClassCourseService.delById(Integer.valueOf(ids[i]));
            }
        }
        if (result == 1 || result >= ids.length)
            return "success";
        else
            return "failure";
    }

    @RequestMapping("/mapper-check-submit")
    @ResponseBody
    public String perClassCourseList() {
        return isSubmitSuccessful;
    }


    /**
     * datatable加载数据
     *
     * @param request 前端请求
     * @return 分页数据
     */
    @RequestMapping("/loadClass")
    @ResponseBody
    public String showCourse(HttpServletRequest request) {
        List<CourseShow> courseShowList = new ArrayList<CourseShow>();
        String orderColumn = request.getParameter("order[0][column]");
        String order = request.getParameter("order[0][dir]");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        DatatablePage<CourseShow> data = new DatatablePage<CourseShow>();
        //search中文编码处理
        String search = "";
        try {
            search= EncodingUtils.useToSearch(search,request);
            Connection conn = ConnectDB.getConnection();

            String orderString3 = "order by " + CourseShowEnum.getNameByIndex(Integer.valueOf(orderColumn)) + " " + order + " ";
            String sql3 =
                    "select * " +
                            "from course a, per_class_course b " +
                            "where CONCAT(a.id,c_name,c_desc, grade,term,to_class,total_need_stu_amount,have_stu_amount) " +
                            "LIKE '%" + search + "%' " +
                            "and a.id=b.course_id " +
                            orderString3 +
                            " LIMIT " + start + " ," + length;

//                拼凑出CourseShow

//              查找数据库
            Statement pstm = conn.createStatement();
            ResultSet rs = pstm.executeQuery(sql3);
            while (rs.next()) {
                CourseShow courseShow = new CourseShow(
                        rs.getString("course_id"),
                        rs.getString("c_name"),
                        rs.getString("c_desc"),
                        rs.getString("b.id"),
                        rs.getString("grade"),
                        rs.getString("term"),
                        rs.getString("to_class"),
                        rs.getString("total_need_stu_amount"),
                        rs.getString("have_stu_amount")
                );//end CourseShow

//                    查有
                courseShowList.add(courseShow);
            }//end while
            System.out.println(sql3);

//            过滤后的总记录数
            data.setRecordsFiltered(perClassCourseService.countAll());
//            总记录数
//            data.setRecordsTotal(perClassCourseService.countAll()-1);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        draw: 表示请求次数
        data.setDraw(Integer.valueOf(request.getParameter("draw")) + 1);

//        data: 具体的数据对象数组
        data.setData(courseShowList);
        System.out.println(JsonUtils.objectToJson(data));
        return JsonUtils.objectToJson(data);

    }



}
