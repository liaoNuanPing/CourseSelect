package controller;

import consts.Path;
import enums.StudentEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.IdentityAuditingService;
import service.StudentService;
import service.WxStudentService;
import utils.ConnectDB;
import utils.EncodingUtils;
import utils.JsonUtils;
import utils.LoggerUtlis;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional(rollbackFor = { Exception.class })
public class IdentityAuditingController {
    Logger logger=LoggerUtlis.getLogger(IdentityAuditingController.class);

    @Autowired
    IdentityAuditingService identityAuditingService;

    @Autowired
    WxStudentService wxStudentService;

    @Autowired
    StudentService studentService;

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
            String where = "where CONCAT(id,stu_name, grade,class_now,parent_name,parent_phone,parent_code) LIKE '%" + search + "%'";

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
                        rs.getString("parent_code"),
                        rs.getString("head_img"),
                        rs.getDate("register_time"),
                        rs.getString("auditing_status")
                );//end CourseShow

//                    查有
                studentList.add(stuSelect);
            }//end while

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
        return JsonUtils.objectToJson(data);
    }

    @RequestMapping(value = {"/mapping-audit-del"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String del(String perId, String[] ids) throws Exception {
        try {
            int result = 0;
            if (perId != null) {
                IdentityAuditing identityAuditing = identityAuditingService.selectById(Integer.valueOf(perId));
                result += identityAuditingService.delById(Integer.valueOf(perId));
                File file = new File(Path.getImagesPath() + "/" + identityAuditing.getHeadImg());
                if (file.exists()&&file.isFile())
                    file.delete();
            } else
                for (int i = 0; i < ids.length; i++) {
                    IdentityAuditing identityAuditing = identityAuditingService.selectById(Integer.valueOf(ids[i]));
                    result += identityAuditingService.delById(Integer.valueOf(ids[i]));
                    result += identityAuditingService.delById(Integer.valueOf(perId));
                    File file = new File(Path.getImagesPath() + "/" + identityAuditing.getHeadImg());
                    if (file.exists()&&file.isFile())
                        file.delete();
                }

            if (result == 1 || ids == null || result == ids.length)
                return JsonUtils.objectToJson(new WxResultJson(1,""));
            else
                return JsonUtils.objectToJson(new WxResultJson(0,"图片删除失败"));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("------mapping-audit-del-"+e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0,""));
        }
    }


    /**
     * 管理员通过审核或淘汰
     * @param idea 操作状态
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/mapping-audit-handle"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String passOrNot(String idea,String id) throws Exception {
        try {
            IdentityAuditing auditing = identityAuditingService.selectById(Integer.valueOf(id));
            auditing.setAuditingStatus(idea);
            if ("2".equals(idea))
                identityAuditingService.update(auditing);
            else {
//            通过
                Student student = new Student(
                        null,
                        auditing.getStuName(),
                        auditing.getGrade(),
                        auditing.getClassNow(),
                        auditing.getParentName(),
                        auditing.getParentPhone(),
                        auditing.getParentCode(),
                        auditing.getHeadImg()
                );
//            更新identityAuditing并插入Student记录
                if( identityAuditingService.update(auditing)==0)
                    return JsonUtils.objectToJson(new WxResultJson(0, "审核更新失败"));

                if( studentService.insert(student)==0)
                    return JsonUtils.objectToJson(new WxResultJson(0, "更新到学生列表失败"));

//            更新wxStudent
                WxStudent wxStudent = wxStudentService.selectByAuditingId(Integer.valueOf(id));
                wxStudent.setStuId(student.getId());
                wxStudentService.update(wxStudent);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("------mapping-audit-handle-" + e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0, "操作失败"));
        }
        return JsonUtils.objectToJson(new WxResultJson(1, ""));
    }





}
