package controller;

import enums.StudentEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.DatatablePage;
import pojo.IdentityAuditing;
import pojo.Student;
import service.IdentityAuditingService;
import utils.ConnectDB;
import utils.EncodingUtils;
import utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IdentityAuditingController {

    @Autowired
    IdentityAuditingService identityAuditingService;

    @RequestMapping(value = {"/loadIdentityAudit"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String allIdentityAuditShow(HttpServletRequest request) {
        List<IdentityAuditing> studentList = new ArrayList<IdentityAuditing>();
        String orderColumn = request.getParameter("order[0][column]");
        String order = request.getParameter("order[0][dir]");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        DatatablePage<IdentityAuditing> data = new DatatablePage<IdentityAuditing>();
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
                            "from identity_auditing " +
                            where +
                            " " +
                            orderString3 +
                            "LIMIT " + start + " ," + length;

//                拼凑出StudentShow

//              查找数据库
            Statement pstm = conn.createStatement();
            ResultSet rs = pstm.executeQuery(sql3);
            while (rs.next()) {
                IdentityAuditing stuSelect = new IdentityAuditing(
                        rs.getInt("id"),
                        rs.getString("stu_name"),
                        rs.getString("grade"),
                        rs.getString("class_now"),
                        rs.getString("parent_name"),
                        rs.getString("parent_phone"),
                        rs.getString("head_img"),
                        rs.getDate("register_time"),
                        rs.getString("auditing_status")
                );//end CourseShow

//                    查有
                studentList.add(stuSelect);
            }//end while
            System.out.println(sql3);

//            过滤后的总记录数
            data.setRecordsFiltered(identityAuditingService.countAll());
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

    @RequestMapping("/mapping-audit-del")
    @ResponseBody
    public String del(String perId, String[] ids) throws Exception{
        int result = 0;
        if (perId != null)
            result += identityAuditingService.delById(Integer.valueOf(perId));
        else
            for (int i = 0; i < ids.length; i++)
                result += identityAuditingService.delById(Integer.valueOf(ids[i]));

        if (result == 1 || ids==null|| result == ids.length)
            return "success";
        else
            return "failure";
    }



    @RequestMapping("/mapping-audit-handle")
    @ResponseBody
    public String passOrNot(String idea,String id) throws Exception{
        IdentityAuditing auditing = identityAuditingService.selectById(Integer.valueOf(id));
        auditing.setAuditingStatus(idea);
        if ("2".equals(idea))
            identityAuditingService.update(auditing);
        else {
            Student student = new Student(null,
                    auditing.getStuName(),
                    auditing.getGrade(),
                    auditing.getClassNow(),
                    auditing.getParentName(),
                    auditing.getParentPhone(),
                    auditing.getHeadImg()
            );
            identityAuditingService.updateSelfAndInsertStudent(auditing, student);
        }
        return "success";
    }



    /**
     * 微信提交：
     * 身份审核提交
     * */
    @RequestMapping(value="/wx-Audit-submit")
    @ResponseBody
    public String identityAudit(HttpServletRequest request) {
        Map<String, String> map=new HashMap<String, String>();
        try {
//        TODO 数据名称校验
            String stuId = request.getParameter("student_id");
            String stuName = request.getParameter("student_name");
            String parentName = request.getParameter("parent_name");
            String mobile = request.getParameter("mobile");
            String grade = request.getParameter("grade");
            String classNow = request.getParameter("class_now");

            IdentityAuditing identityAuditing = new IdentityAuditing(
                    null,
                    stuName,
                    parentName,
                    mobile, grade,
                    classNow,
                    FileuploadController.studentHeadImg,
                    null,
                    null);

            FileuploadController.studentHeadImg = "";
            identityAuditingService.insert(identityAuditing);

            map.put("isSuccessful", "1");
            map.put("message", "");
        } catch (Exception e) {
            map.put("isSuccessful", "0");
            map.put("message", "");
        }
        return JsonUtils.objectToJson(map);
    }

    /**
     *  微信提交：
     *  审核状态查询
     * @return
     */
    @RequestMapping(value="/wx-Audit")
    @ResponseBody
    public String AuditStatus(){
        //        TODO 身份认证方式
        Map<String, String> map=new HashMap<String, String>();
        String id="根据身份认证方式找到id";
        String status;
        String result="正在审核中";
        try {
            IdentityAuditing identityAuditing = identityAuditingService.selectById(Integer.valueOf(id));
            status = identityAuditing.getAuditingStatus();
        }catch (Exception e){
            status="0";
        }

        if ("0".equals(status))
            result="正在审核中";
        else if ("1".equals(status))
            result="审核通过";
        else if ("2".equals(status))
            result="审核不通过";

        map.put("status",result);
        return JsonUtils.objectToJson(map);
    }

}
