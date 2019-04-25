package controller;

import enums.StuSelectEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
import utils.LoggerUtlis;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional(rollbackFor = { Exception.class })
public class StuSelectController {
    Logger logger=LoggerUtlis.getLogger(StuSelectController.class);

    @Autowired
    StuSelectService stuSelectService;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    PerClassCourseService perClassCourseService;

    /**
     * 手动添加学生选课
     * @return 成功与否
     * @throws Exception
     */
    @RequestMapping(value = {"/mapping-select-add"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String stuSelectAdd(HttpServletRequest request) throws Exception {
        try {
//            TODO 重复选课，管理员的问题，看要不要改

            String stuId = request.getParameter("student_id");
            String perCourseId = request.getParameter("per_course_id");

            Student student = studentService.selectById(Integer.valueOf(stuId));
            if(student==null)
                return JsonUtils.objectToJson(new WxResultJson(0,"学生ID无效"));

            PerClassCourse perClassCourse = perClassCourseService.selectByPrimaryId(Integer.valueOf(perCourseId));
            if(perClassCourseService==null)
                return JsonUtils.objectToJson(new WxResultJson(0,"选课ID无效"));

            //如果课程已满
            if (perClassCourse.getTotalNeedStuAmount() <= perClassCourse.getHaveStuAmount()) {
                return JsonUtils.objectToJson(new WxResultJson(0, "选课失败，课程已满"));
            }

            Course course = courseService.selectById(perClassCourse.getCourseId());
            if(stuSelectService.insert(new StuSelect(student, course))==0)
                return JsonUtils.objectToJson(new WxResultJson(0,"选课添加失败，请联系管理员"));

            perClassCourse.setHaveStuAmount(perClassCourse.getHaveStuAmount() + 1);
            if (perClassCourseService.update(perClassCourse) == 0)
                return JsonUtils.objectToJson(new WxResultJson(0, "选课添加失败，请联系管理员"));

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("------mapping-select-add-"+e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0,"添加错误"));
        }
        return JsonUtils.objectToJson(new WxResultJson(1,""));
    }

    /**
     * 更新
     * @param id 主键
     * @param course 要更新的课程名
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/mapping-select-update"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String stuSelectUpdate(String id,String course) throws Exception {
        try {
            StuSelect stuSelect = stuSelectService.selectByPrimaryId(Integer.valueOf(id));
            if (stuSelect != null) {
                stuSelect.setcName(course);
                stuSelectService.update(stuSelect);
            } else
                return JsonUtils.objectToJson(new WxResultJson(0,"更新错误"));
            return JsonUtils.objectToJson(new WxResultJson(1,""));
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("------mapping-select-update-"+e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0,"更新错误"));
        }
    }

    @RequestMapping(value = {"/mapping-select-del"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String del(String perId, String[] ids) {
        try {
            int result = 0;
            if (perId != null)
                result += stuSelectService.delById(Integer.valueOf(perId));
            else
                for (int i = 0; i < ids.length; i++)
                    result += stuSelectService.delById(Integer.valueOf(ids[i]));
            if (result == 1 || ids == null || result == ids.length)
                return JsonUtils.objectToJson(new WxResultJson(1, ""));
            else
                return JsonUtils.objectToJson(new WxResultJson(0, "删除错误"));

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("------mapping-select-del-" + e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0, "删除错误"));
        }
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
            String where = "where CONCAT(student_card,stu_name, grade,class_now,parent_name,parent_phone,parent_code,c_name,select_time) LIKE '%" + search + "%'";

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
                        rs.getString("parent_code"),
                        rs.getString("head_img"),
                        rs.getInt("course_id"),
                        rs.getString("c_name"),
                        rs.getString("c_desc"),
                        rs.getDate("select_time")
                );
                stuSelectArrayList.add(stuSelect);
            }//end while

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
        return JsonUtils.objectToJson(data);

    }



}
