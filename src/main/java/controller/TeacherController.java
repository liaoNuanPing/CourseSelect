package controller;

import enums.TeacherEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.DatatablePage;
import pojo.Teacher;
import pojo.WxResultJson;
import service.TeacherService;
import utils.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional(rollbackFor = { Exception.class })
public class TeacherController {

    @Autowired
    TeacherService TeacherService;

    /**
     * 点击姓名显示详情页面
     * @param  id 教师id
     * @return json
     */
    @RequestMapping(value = {"/mapping-teacher_info-page-show"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String teacherPageShow(String id) {
        try {
            String json= JsonUtils.objectToJson( TeacherService.selectById(Integer.valueOf(id)));
            return json;
        }catch (Exception e){
            return "";
        }
    }

    @RequestMapping(value = {"/loadTeacher"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String allTeacherShow(HttpServletRequest request) {
        List<Teacher> teacherList = new ArrayList<Teacher>();
        String orderColumn = request.getParameter("order[0][column]");
        String order = request.getParameter("order[0][dir]");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        DatatablePage<Teacher> data = new DatatablePage<Teacher>();
        //search中文编码处理
        String search = "";
        try {
            search = EncodingUtils.useToSearch(search,request);

            Connection conn = ConnectDB.getConnection();
            String orderString3 = "order by " + TeacherEnum.getNameByIndex(Integer.valueOf(orderColumn)) + " " + order + " ";
            String where = "where CONCAT(id,teacher, teacher_id,sex,teacher_phone,teacher_mail) LIKE '%" + search + "%'";

            if ("".equals(search))
                where="";
            String sql3 =
                    "select * " +
                            "from teacher_info " +
                            where +
                            " " +
                            orderString3 +
                            "LIMIT " + start + " ," + length;

//                拼凑出TeacherShow

//              查找数据库
            Statement pstm = conn.createStatement();
            ResultSet rs = pstm.executeQuery(sql3);
            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getInt("id"),
                        rs.getString("teacher"),
                        rs.getInt("teacher_id"),
                        rs.getString("sex"),
                        rs.getString("teacher_phone"),
                        rs.getString("teacher_email")
                );
                teacherList.add(teacher);
            }//end while

//            过滤后的总记录数
            data.setRecordsFiltered(TeacherService.countAll());
//            总记录数
//            data.setRecordsTotal(perClassCourseService.countAll()-1);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        draw: 表示请求次数
        data.setDraw(Integer.valueOf(request.getParameter("draw")) + 1);

//        data: 具体的数据对象数组
        data.setData(teacherList);
        return JsonUtils.objectToJson(data);

    }


    @RequestMapping(value = {"/mapping-teacher_info-hand-add"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String handMakeAdd(HttpServletRequest request) throws Exception {
        try {
            String teacherId = request.getParameter("teacher_id");
            String teacherName = request.getParameter("teacher") == null ? "" : request.getParameter("teacher");
            String sex = request.getParameter("sex") == null ? "" : request.getParameter("sex");
            String mobile = request.getParameter("teacher_phone");
            String teacherMail = request.getParameter("teacher_mail");

            Teacher teacher = new Teacher(null,teacherName,"".equals(teacherId) ? null : Integer.valueOf(teacherId), sex, mobile, teacherMail);
            TeacherService.insert(teacher);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LoggerUtlis.getLogger(this.getClass()).error("------mapping-teacher_info-hand-add-" + e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0, "添加错误"));
        }
        return JsonUtils.objectToJson(new WxResultJson(1, ""));
    }

//    @RequestMapping(value = {"/mapping-teacher_info-update"},produces = "text/plain;charset=utf-8")
//    @ResponseBody
//    public String update(HttpServletRequest request) throws Exception {
//        try {
//            //String oldId = request.getParameter("id");
//            //String newId = request.getParameter("teacher_id");
//            String teacherName = request.getParameter("teacher");
//            String sex = request.getParameter("sex");
//            String mobile = request.getParameter("teacher_phone");
//            String teacherMail = request.getParameter("teacher_mail");
//
//            Teacher oTeacher_info = TeacherService.selectById(Integer.valueOf(oldId));
//            Teacher nTeacher_info;
//
//            TeacherService.delById(Integer.valueOf(oldId));
//            TeacherService.insert(nTeacher_info);
//
//        } catch (Exception e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            LoggerUtlis.getLogger(this.getClass()).error("------mapping-teacher_info-update-" + e.getMessage());
//            return JsonUtils.objectToJson(new WxResultJson(0, "更新错误"));
//        }
//        return JsonUtils.objectToJson(new WxResultJson(1, ""));
//    }

    @RequestMapping("/mapping-teacher_info-del")
    @ResponseBody
    public String del(String perId, String[] ids) throws Exception {
        try {
            int result = 0;
            if (perId != null) {
                Teacher teacher = TeacherService.selectById(Integer.valueOf(perId));
                result += TeacherService.delById(Integer.valueOf(perId));
            }
            else
                for (int i = 0; i < ids.length; i++) {
                    Teacher teacher = TeacherService.selectById(Integer.valueOf(ids[i]));
                    result += TeacherService.delById(Integer.valueOf(ids[i]));
                }

            if (result == 1 || ids == null || result == ids.length)
                return JsonUtils.objectToJson(new WxResultJson(1, ""));
            else
                return JsonUtils.objectToJson(new WxResultJson(0, "删除错误"));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LoggerUtlis.getLogger(this.getClass()).error("------mapping-teacher_info-hand-add-" + e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0, "删除错误"));
        }
    }

}
