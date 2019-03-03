package controller;

import consts.Path;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.*;
import utils.JsonUtils;
import utils.LoggerUtlis;
import utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@Transactional(rollbackFor = { Exception.class })
public class WxController {

    @Autowired
    WxStudentService wxStudentService;

    @Autowired
    StudentService studentService;

    @Autowired
    PerClassCourseService perClassCourseService;

    @Autowired
    CoursePicService coursePicService;

    @Autowired
    CourseService courseService;

    @Autowired
    IdentityAuditingService identityAuditingService;

    @Autowired
    StuSelectService stuSelectService;


    @RequestMapping("/wx-getOpenId")
    @ResponseBody
    public String getOpenId(HttpServletRequest request){
        String openId = request.getParameter("openId");
        System.out.println(openId);
        return openId;
    }

    /**
     * 微信提交：
     * 身份审核提交
     * */
    @RequestMapping(value = {"/wx-Audit-add"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String identityAuditAddWithTrans(HttpServletRequest request) {
        Map<String, String> map=new HashMap<String, String>();
        try {
            String openId = request.getParameter("openId").equals("underfined")?null:request.getParameter("openId");
            String stuName = request.getParameter("student_name").equals("underfined")?null:request.getParameter("student_name");
            String parentName = request.getParameter("parent_name").equals("underfined")?null:request.getParameter("parent_name");
            String mobile = request.getParameter("mobile").equals("underfined")?null:request.getParameter("mobile");
            String grade = request.getParameter("grade").equals("underfined")?null:request.getParameter("grade");
            String classNow = request.getParameter("class_now").equals("underfined")?null:request.getParameter("class_now");

            if (openId==null||stuName==null||parentName==null||mobile==null||grade==null||classNow==null||
            "".equals(openId)||"".equals(stuName)||"".equals(parentName)||"".equals(mobile)||"".equals(grade)||"".equals(classNow)) {
                map.put("isSuccessful", "0");
                map.put("message", "学生信息不能有空");
                return JsonUtils.objectToJson(map);
            }

            if (wxStudentService.selectByOpenId(openId)!=null){
                map.put("isSuccessful", "0");
                map.put("message", "openId重复了");
                return JsonUtils.objectToJson(map);
            }


            String headImg="";
            String newHeadImg=String.valueOf(System.currentTimeMillis())+"["+ stuName+"And"+parentName+"]."+FileuploadController.studentHeadImg.substring(FileuploadController.studentHeadImg.lastIndexOf("."),FileuploadController.studentHeadImg.length());
            File dir = new File(Path.getTempPath()+"/");

            if (!dir.exists())
                dir.mkdir();

            if (dir.exists() && dir.isDirectory()) {
                File[] files = dir.listFiles();
                Long biggest= Long.valueOf(10000);
                for (File file : files) {
                    if(file.getName().contains(openId)){
                        Long t=Long.valueOf( file.getName().substring(0,file.getName().indexOf("[")));
                        if(t>biggest){
                            biggest=t;
                            headImg=file.getName();
                        }
                    }

                }
            }

            if ("".equals(headImg)){
                newHeadImg=null;
            }else
                if(!new File(Path.getTempPath()+"/"+headImg).renameTo(new File(Path.getImagesPath()+"/"+newHeadImg)))
                    throw new Exception("移动图片从temp到images不成功");

            IdentityAuditing identityAuditing = new IdentityAuditing(
                    null,
                    stuName,
                    parentName,
                    mobile, grade,
                    classNow,
                    newHeadImg==null?null:"static/images/"+newHeadImg,
                    null,
                    "0");

                identityAuditingService.insert(identityAuditing);
                wxStudentService.insert(new WxStudent(null,openId,null,identityAuditing.getId(),null));

            map.put("isSuccessful", "1");
            map.put("msg", "");
        } catch (Exception e) {
            map.put("isSuccessful", "0");
            map.put("msg", "");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return JsonUtils.objectToJson(map);
    }


    /**
     *  微信提交：
     *  审核状态查询
     * @return
     */
    @RequestMapping(value = {"/wx-Audit-status"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String AuditStatus(HttpServletRequest request){
        String openId = request.getParameter("openId");
        Map<String, String> map=new HashMap<String, String>();
        String status;
        String result="正在审核中";
        try {
            WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
            Integer id=wxStudent.getAuditingId();
            IdentityAuditing identityAuditing = identityAuditingService.selectById(id);
            status = identityAuditing.getAuditingStatus();
        }catch (Exception e){
            e.printStackTrace();
            status="3";
        }

        if ("0".equals(status))
            result="正在审核中";
        else if ("1".equals(status))
            result="审核通过";
        else if ("2".equals(status))
            result="审核不通过";
        else if ("3".equals(status))
            result="未提交审核";
        else
            result="未知错误";

        map.put("msg",result);
        map.put("status",status);
        return JsonUtils.objectToJson(map);
    }


    /**
     * HTTP请求	2000	264	161	615	873	1630	33	4471	0.0	345.0655624568668	56.038276667097996	55.601384575569355
     * 微信端加载学生可选课信息
     * @param request
     * @return 可选课json
     */
    @RequestMapping(value = {"/wx-loadSelectedCourse"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String sentToWxStudentSelectedCourse(HttpServletRequest request){
//        获取openi确认学生年级班级

        String openId = request.getParameter("openId");
        WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
        if (wxStudent==null)
            return "{isSuccessful:false}";

        Student student=studentService.selectById(wxStudent.getStuId());
        String term= PropertiesUtils.getPropertiesValue("config.properties","term");
        String clases= String.valueOf(student.getClassNow());
        String grade=student.getGrade();

        List<WxCourse> list=new ArrayList<WxCourse>();

        List<PerClassCourse> perClassCourseList=perClassCourseService.selectByTermAndGradeAndClass(term,grade,clases);
        for (PerClassCourse perClassCourse :perClassCourseList) {
            List<CoursePic> coursePics = coursePicService.selectCoursePicByCourseId(perClassCourse.getCourseId());
            List<String> picList =new ArrayList<String>();
            for (int i=0;i<coursePics.size();i++)
                picList.add("http://47.107.128.33:8080/CourseSelect/"+coursePics.get(i).getPic());

            list.add(new WxCourse(courseService.selectById(perClassCourse.getCourseId()),perClassCourse,picList));
        }

        System.out.println(JsonUtils.objectToJson(list));
        return JsonUtils.objectToJson(list);
    }

    /**
//     * TODO 重复选课bug
     * 微信端学生选课结果上传
     * @param request
     * @return 选课结果消息
     */
    @RequestMapping(value = {"/wx-upload-selection"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String uploadStudentSelection(HttpServletRequest request) throws IOException {
//        TODO 考虑做成多线程，课程有余就返回选课成功
        Student student=null;
        PerClassCourse perClassCourse=null;
        Course course=null;
        WxStudent wxStudent=null;
        int a=-1,b=-1;
        try {
            String openId = request.getParameter("openId");
            String perCourseId = request.getParameter("id");



            if (openId == null || "".equals(openId))
                return JsonUtils.objectToJson(new WxResultJson(0, "没有上传openId"));

            if (perCourseId == null || "".equals(perCourseId))
                return JsonUtils.objectToJson(new WxResultJson(0, "没有上传选课的ID"));


            wxStudent = wxStudentService.selectByOpenId(openId);
            if (wxStudent == null)
                return JsonUtils.objectToJson(new WxResultJson(0, "此openId不存在"));

            WxResultJson wxResultJson = JsonUtils.jsonToPojo(isHasSelectedStatus(openId), WxResultJson.class);
            if (1== wxResultJson.getIsSuccessful())
                return JsonUtils.objectToJson(new WxResultJson(0, "不能重复选课"));

            student = studentService.selectById(wxStudent.getStuId());
            if (student == null)
                return JsonUtils.objectToJson(new WxResultJson(0, "此学生不存在"));

            perClassCourse = perClassCourseService.selectByPrimaryId(Integer.valueOf(perCourseId));

            if (perClassCourse == null)
                return JsonUtils.objectToJson(new WxResultJson(0, "选课id无效"));

            course = courseService.selectById(perClassCourse.getCourseId());
            if (course == null)
                return JsonUtils.objectToJson(new WxResultJson(0, "课程不存在"));


//            如果课程已满
            if (perClassCourse.getTotalNeedStuAmount() <= perClassCourse.getHaveStuAmount()) {
                return JsonUtils.objectToJson(new WxResultJson(0, "选课失败，课程已满"));
            }


            a = stuSelectService.insert(new StuSelect(student, course));
            if (a == 0)
                return JsonUtils.objectToJson(new WxResultJson(0, "选课失败，请联系管理员,stuSelectService"));

            perClassCourse.setHaveStuAmount(perClassCourse.getHaveStuAmount() + 1);
            b = perClassCourseService.update(perClassCourse);
            if (b == 0)
                return JsonUtils.objectToJson(new WxResultJson(0, "选课失败，请联系管理员,perClassCourseService"));
        }catch (Exception e){

            Logger logger = LoggerUtlis.getWxControllerLogger();
            logger.error(e.getMessage());

            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonUtils.objectToJson(new WxResultJson(0, "选课失败,回滚"));

        }

        return JsonUtils.objectToJson(new WxResultJson(1, "选课成功"));
    }


    /**
     * 学生本学期已选课列表
     * @param request
     * @return
     */
    @RequestMapping(value={"wx-get-selection"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String showStudentCourseAlreadySelectedSuccessful(HttpServletRequest request) {
        Logger logger = LoggerUtlis.getWxControllerLogger();
        Map<String, Object> map = new HashMap<String, Object>();

//        没有查询到这个人报错
        String openId = request.getParameter("openId");
        WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
        if (wxStudent == null)
            return JsonUtils.objectToJson(new WxResultJson(0, "此openId不存在"));

        Student student = studentService.selectById(wxStudent.getStuId());
        if (student == null)
            return JsonUtils.objectToJson(new WxResultJson(0, "此学生不存在"));

        List<StuSelect> stuSelects = stuSelectService.selectByStudentIdAndTerm(student.getId(), PropertiesUtils.getPropertiesValue("config.properties", "term"));
        if (stuSelects == null || stuSelects.size() == 0)
            return JsonUtils.objectToJson(new WxResultJson(0, "未选课"));

        ArrayList<Object> courseList = new ArrayList<Object>();

        StuSelect s = stuSelects.get(0);

        List<PerClassCourse> perClassCourseList = perClassCourseService.selectByCourseIdAndTermAndGradeAndClass(String.valueOf(s.getCourseId()), s.getTerm(), s.getGrade(), s.getClassNow());

        logger.debug("---------" + String.valueOf(s.getCourseId()));
        logger.debug("---------" + s.getTerm());
        logger.debug("---------" + s.getGrade());
        logger.debug("---------" + s.getClassNow());
        if (perClassCourseList == null || perClassCourseList.size() == 0)
            return JsonUtils.objectToJson(new WxResultJson(0, "课程不存在了"));

        logger.debug(JsonUtils.objectToJson(perClassCourseList));
        logger.debug(perClassCourseList.get(0).getId());

        map.put("id", perClassCourseList.get(0).getId());
        map.put("cName", s.getcName());
        map.put("selectTime", s.getSelectTime().toString());
        map.put("cDesc", s.getcDesc());

        List<CoursePic> coursePicList = coursePicService.selectCoursePicByCourseId(s.getCourseId());
        for (CoursePic coursePic : coursePicList)
            courseList.add(coursePic.getPic());
        map.put("pics", courseList);

        map.put("isSuccessful", "1");
        map.put("msg", "");
        return JsonUtils.objectToJson(map);
    }

    /**
     * 撤销选课
     * @param request
     * @return
     */
    @RequestMapping(value={"wx-cancel-selection"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String cancelSelect(HttpServletRequest request){
        try {
            String openId = request.getParameter("openId");
            String perCourseId = request.getParameter("id");
            if (openId == null || "".equals(openId)) {
                return JsonUtils.objectToJson(new WxResultJson(0, "没有上传openId"));
            }

            WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
            if (wxStudent == null)
                return JsonUtils.objectToJson(new WxResultJson(0, "此openId不存在"));

            Student student = studentService.selectById(wxStudent.getStuId());
            if (student == null)
                return JsonUtils.objectToJson(new WxResultJson(0, "此学生不存在"));

            PerClassCourse perClassCourse = perClassCourseService.selectByPrimaryId(Integer.valueOf(perCourseId));

            if (perClassCourse == null)
                return JsonUtils.objectToJson(new WxResultJson(0, "选课id无效"));

            Course course = courseService.selectById(perClassCourse.getCourseId());
            if (course == null)
                return JsonUtils.objectToJson(new WxResultJson(0, "课程不存在"));

//            如果没选过
            if (0 == perClassCourse.getHaveStuAmount()) {
                return JsonUtils.objectToJson(new WxResultJson(0, "撤销选课失败，你未曾选过该课程"));
            }

            perClassCourse.setHaveStuAmount(perClassCourse.getHaveStuAmount() - 1);
            if (perClassCourseService.update(perClassCourse) == 0)
                return JsonUtils.objectToJson(new WxResultJson(0, "撤销选课失败，请联系管理员"));

            stuSelectService.delByPerCourseIdAndStudentIdAndYear(course.getId(), String.valueOf(student.getId()));
            return JsonUtils.objectToJson(new WxResultJson(1, "撤销成功"));
        }catch (Exception e){

            Logger logger = LoggerUtlis.getWxControllerLogger();
            logger.error(e.getMessage());

            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonUtils.objectToJson(new WxResultJson(0, "撤销失败，事务回滚"));

        }

    }

    /**
     * 选课状态，是否已选
     * @param openId 微信openId
     * @return
     */
    @RequestMapping(value={"wx-is-selected-status"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String isHasSelectedStatus(String openId){
        try {

            if (openId == null || "".equals(openId)) {
                return JsonUtils.objectToJson(new WxResultJson(0, "没有上传openId"));
            }
            WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
            if (wxStudent == null)
                return JsonUtils.objectToJson(new WxResultJson(0, "此openId不存在"));

            Student student = studentService.selectById(wxStudent.getStuId());
            if (student == null)
                return JsonUtils.objectToJson(new WxResultJson(0, "此学生不存在"));

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, -6);
            List<StuSelect> stuSelectList = stuSelectService.selectByStudentIdAndTerm(student.getId(), PropertiesUtils.getPropertiesValue("config.properties", "term"));
            if (stuSelectList.size() > 0)
                return JsonUtils.objectToJson(new WxResultJson(1, String.valueOf(stuSelectList.size())));
        }catch (Exception e){
            Logger logger = LoggerUtlis.getWxControllerLogger();
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return JsonUtils.objectToJson(new WxResultJson(2, "本学期未选过课"));

    }

    /**
     * 微信端学生获取个人填写信息
     * @param request
     * @return
     */
    @RequestMapping(value = {"/wx-get-self-info"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getStudentInfo(HttpServletRequest request){
        String openId = request.getParameter("openId");
        if (openId==null)
            return JsonUtils.objectToJson(new WxResultJson(0,"未找到该学生"));
        WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
        Student student = studentService.selectById(wxStudent.getStuId());
        if(student==null)
            return JsonUtils.objectToJson(new WxResultJson(0,"未找到该学生"));
//        TODO 相对路径
        student.setHeadImg("http://47.107.128.33:8080/CourseSelect/"+ student.getHeadImg());
        return JsonUtils.objectToJson(student);

    }


}
