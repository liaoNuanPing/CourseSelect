package controller;

import enums.TeacherEnum;
import org.apache.log4j.Logger;
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

    private WxResultJson wxResultJson=new WxResultJson();
    private final Logger logger = LoggerUtlis.getLogger(TeacherController.class);


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
            String where = "where CONCAT(teacher,teacher_id,sex,teacher_phone,teacher_mail) LIKE '%" + search + "%'";

            if ("".equals(search))
                where="";
            String sql3 =
                    "select * " +
                            "from teacher " +
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
                        rs.getString("name"),
                        rs.getString("worker_id"),
                        rs.getString("sex"),
                        rs.getString("phone"),
                        rs.getString("email")
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


    @RequestMapping(value = {"/mapping-teacher-add"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String add(HttpServletRequest request) throws Exception {
        try {
            String workerId = request.getParameter("worker_id");
            String name = request.getParameter("name");
            String sex = request.getParameter("sex");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            if (workerId==null)
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0, "教师id不为空"));
            if (name==null)
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0, "姓名不为空"));
            if (sex==null)
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0, "性别不为空"));
            if (phone==null)
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0, "电话不为空"));
            if (email==null)
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0, "邮箱不为空"));

            Teacher teacher = new Teacher(null,name,workerId, sex, phone, email);
            TeacherService.insert(teacher);
            return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(1, ""));

        } catch (Exception e) {
            LoggerUtlis.getLogger(this.getClass()).error("------mapping-teacher-add-----" + e.getMessage());
            return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "添加错误"));
        }
    }

    @RequestMapping(value = {"/mapping-teacher-update"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String update(HttpServletRequest request) throws Exception {
        try {
            String id = request.getParameter("id");
            String workerId = request.getParameter("worker_id");
            String name = request.getParameter("name");
            String sex = request.getParameter("sex");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            if (workerId==null)
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0, "教师id不为空"));
            if (name==null)
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0, "姓名不为空"));
            if (sex==null)
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0, "性别不为空"));
            if (phone==null)
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0, "电话不为空"));
            if (email==null)
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0, "邮箱不为空"));

            Teacher teacher = TeacherService.selectById(Integer.valueOf(id));
            teacher.setName(name);
            teacher.setWorkerId(workerId);
            teacher.setSex(sex);
            teacher.setPhone(phone);
            teacher.setEmail(email);
            TeacherService.update(teacher);
            return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(1, ""));


        } catch (Exception e) {
            LoggerUtlis.getLogger(this.getClass()).error("------mapping-teacher-update------" + e.getMessage());
            return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "更新异常："+e.getMessage()));
        }
    }

    @RequestMapping("/mapping-teacher-del")
    @ResponseBody
    public String del(String perId, String[] ids) throws Exception {
        try {
            int result = 0;
            if (perId != null) {
                result += TeacherService.delById(Integer.valueOf(perId));
            }
            else
                for (int i = 0; i < ids.length; i++) {
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
