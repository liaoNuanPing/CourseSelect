package controller;

import enums.CourseShowEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.*;
import utils.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional(rollbackFor = { Exception.class })
public class PerClassCourseController {
    Logger logger=LoggerUtlis.getLogger(PerClassCourseController.class);
    
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


    /**
     * 添加课程
     *
     * @param request 前端传来的request
     * @return
     */
    @RequestMapping(value = {"/mapping-course-rule-add"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String perClassCourseAdd(HttpServletRequest request) throws Exception {
        try {
            String courseId = request.getParameter("id");
            String grade = request.getParameter("grade");
            String term = request.getParameter("term");
            String classes = request.getParameter("classes");
            String total = request.getParameter("total_need");
            int totalNeed = "".equals(total) ? 9999 : Integer.valueOf(request.getParameter("total_need"));
//          查看是不是已经有相同效果的per_class_course,不能出现效果重叠

            if (courseService.selectByPrimaryId(Integer.valueOf(courseId))==null)
                return JsonUtils.objectToJson(new WxResultJson(0,"课程ID 不存在"));

            List<PerClassCourse> perClassCourseList=perClassCourseService.selectByCourseIdAndTermAndGradeAndClass(courseId,term,grade,classes);

            if (perClassCourseList != null && perClassCourseList.size() > 0)
                return JsonUtils.objectToJson(new WxResultJson(0,"ID "+perClassCourseList.get(0).getId()+" 已经有类似效果，请确保选课是唯一的"));

            //插入PerClassCourse
            PerClassCourse perClassCourse = new PerClassCourse(null, Integer.valueOf(courseId), grade, term, classes, totalNeed, 0);
            if (perClassCourseService.insert(perClassCourse) != 1)
                throw new Exception("mapping-course-rule-add-perClassCourseService插入不成功");
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("mapping-course-rule-add : "+e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0,"添加失败，请注意参数是否正确或联系管理员"));
        }
        return JsonUtils.objectToJson(new WxResultJson(1,""));

    }

    @RequestMapping(value = {"/mapping-course-rule-update"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String Update(HttpServletRequest request ) throws Exception {
        try {
            PerClassCourse perClassCourse = perClassCourseService.selectByPrimaryId(Integer.valueOf(request.getParameter("id")));
            if (perClassCourse != null) {
                perClassCourse.setTerm(request.getParameter("term"));
                perClassCourse.setGrade(request.getParameter("grade"));
                perClassCourse.setToClass(request.getParameter("classes"));
                perClassCourse.setTotalNeedStuAmount(Integer.valueOf(request.getParameter("total_need")));
                perClassCourse.setHaveStuAmount(Integer.valueOf(request.getParameter("have")));
                perClassCourseService.update(perClassCourse);
            }else
                return JsonUtils.objectToJson(new WxResultJson(0,"perClassCourse不存在，无法更新"));
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("------mapping-course-rule-update-"+e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0,"更新错误"));
        }
        return JsonUtils.objectToJson(new WxResultJson(1,"success"));

    }

    @RequestMapping(value = {"/mapping-course-rule-del"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String del(String perId, String[] ids) {
        try {
            int result = 0;
            if (perId != null) {
                result += perClassCourseService.delById(Integer.valueOf(perId));
            } else {
                for (int i = 0; i < ids.length; i++) {
                    result += perClassCourseService.delById(Integer.valueOf(ids[i]));
                }
            }
            if (result == 1 || ids == null || result == ids.length)
                return JsonUtils.objectToJson(new WxResultJson(1,""));

            else
                return JsonUtils.objectToJson(new WxResultJson(0,"删除错误"));

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("------mapping-course-rule-del-"+e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0,"删除错误"));
        }

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
            if ("全".equals(search)||"部".equals(search)||"全部".equals(search))
                search="0";
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

                if ("0".equals(courseShow.getToClass()))
                    courseShow.setToClass("全部");
                if ("0".equals(courseShow.getGrade()))
                    courseShow.setGrade("全部");
                if ("0".equals(courseShow.getTerm()))
                    courseShow.setTerm("全部");

                courseShowList.add(courseShow);
            }//end while

//            过滤后的总记录数
            data.setRecordsFiltered(perClassCourseService.countAll());
//            总记录数
//            data.setRecordsTotal(perClassCourseService.countAll()-1);
        } catch (Exception e) {
            logger.error("------loadClass-"+e.getMessage());
//            e.printStackTrace();
        }

//        draw: 表示请求次数
        data.setDraw(Integer.valueOf(request.getParameter("draw")) + 1);

//        data: 具体的数据对象数组
        data.setData(courseShowList);
        return JsonUtils.objectToJson(data);

    }



}
