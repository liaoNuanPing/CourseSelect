package controller;

import consts.Url;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Controller
@Transactional(rollbackFor = { Exception.class })
public class WxController {

    private final Logger logger = LoggerUtlis.getLogger(WxController.class);
    private WxResultJson wxResultJson=new WxResultJson();


    private boolean isLock=false;
    private boolean isLock2Cancel=false;

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

    @Autowired
    RegisterCodeService registerCodeService;

    @RequestMapping(value = {"/wx-check-code"}, produces = "text/plain;charset=utf-8")
    public String checkRegisterCode(HttpServletRequest request) {
        String registerCode = request.getParameter("register_code");
        RegisterCode registerCodeList = registerCodeService.selectByCode(registerCode);
        System.out.println(registerCodeList.getCode());
        if (registerCodeList==null)
            System.out.println("null");
        else if (Boolean.valueOf( registerCodeList.getCode()))
            System.out.println("注册码无效");
        else if (!Boolean.valueOf(registerCodeList.getCode()))
            System.out.println("注册码有效");
        else
            System.out.println("意外情况");
        return null;
    }

    /**
     * 微信提交：
     * 身份审核提交
     */
    @RequestMapping(value = {"/wx-Audit-add"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String identityAuditAddWithTrans(HttpServletRequest request) {
        try {

            String openId = request.getParameter("openId").equals("underfined") ? null : request.getParameter("openId");
            String sn = request.getParameter("student_name").equals("underfined") ? null : request.getParameter("student_name");
            String parentName = request.getParameter("parent_name").equals("underfined") ? null : request.getParameter("parent_name");
            String mobile = request.getParameter("mobile").equals("underfined") ? null : request.getParameter("mobile");
            String parentCode = request.getParameter("parent_code").equals("underfined") ? null : request.getParameter("parent_code");
            String grade = request.getParameter("grade").equals("underfined") ? null : request.getParameter("grade");
            String classNow = request.getParameter("class_now").equals("underfined") ? null : request.getParameter("class_now");
            String registerCode = request.getParameter("register_code").equals("underfined") ? null : request.getParameter("register_code");

            String stuName=new String(sn.getBytes(),"UTF-8");
            System.out.println(stuName);

            if (openId == null || stuName == null || parentName == null || mobile == null ||parentCode==null|| grade == null || classNow == null ||
                    "".equals(openId) || "".equals(stuName) || "".equals(parentName) || "".equals(mobile) ||  "".equals(parentCode)||"".equals(grade) || "".equals(classNow)) {
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0,"学生信息不能有空"));
            }

            if (wxStudentService.selectByOpenId(openId) != null)
                return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0,"openId重复了"));


            RegisterCode checkCode=registerCodeService.selectByCode(registerCode);
            if (checkCode==null)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "注册码不存在"));
            else if (Boolean.valueOf( checkCode.getCode()))
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "注册码无效"));
            else if (!Boolean.valueOf( checkCode.getCode())) {

                checkCode.setDisable("true");
                registerCodeService.update(checkCode);


//            String headImg = "";
//            String newHeadImg = String.valueOf(System.currentTimeMillis()) + "[" + stuName + "And" + parentName + "]." + FileuploadController.studentHeadImg.substring(FileuploadController.studentHeadImg.lastIndexOf("."), FileuploadController.studentHeadImg.length());
//            File dir = new File(Path.getTempPath() + "/");

//            if (!dir.exists())
//                dir.mkdir();
//
//            if (dir.exists() && dir.isDirectory()) {
//                File[] files = dir.listFiles();
//                Long biggest = Long.valueOf(10000);
//                for (File file : files) {
//                    if (file.getName().contains(openId)) {
//                        Long t = Long.valueOf(file.getName().substring(0, file.getName().indexOf("[")));
//                        if (t > biggest) {
//                            biggest = t;
//                            headImg = file.getName();
//                        }
//                    }
//
//                }
//            }
//            if ("".equals(headImg)) {
//                newHeadImg = null;
//            } else if (!new File(Path.getTempPath() + "/" + headImg).renameTo(new File(Path.getImagesPath() + "/" + newHeadImg)))
//                throw new Exception("移动图片从temp到images不成功");
//            IdentityAuditing identityAuditing = new IdentityAuditing(
//                    null,
//                    stuName,
//                    parentName,
//                    mobile,
//                    grade,
//                    classNow,
//                    null,
//                    null,
//                    "0");

                IdentityAuditing identityAuditing = new IdentityAuditing(
                        null,
                        stuName,
                        grade,
                        classNow,
                        parentName,
                        mobile,
                        parentCode,
                        null,
                        null,
                        "0"

                );

                identityAuditingService.insert(identityAuditing);
                wxStudentService.insert(new WxStudent(null, openId, null, identityAuditing.getId(), null));
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(1, ""));
            }else {
                System.out.println("注册码意外情况");
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "注册码意外情况"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.out.println("发生异常");
            return JsonUtils.objectToJson( wxResultJson.setIsSuccessfulAndMsg(0,"发生异常"));
        }
    }


    /**
     * 微信提交：
     * 审核状态查询
     *
     * @return
     */
    @RequestMapping(value = {"/wx-Audit-status"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String AuditStatus(HttpServletRequest request) {
        String openId = request.getParameter("openId");
        Map<String, String> map = new HashMap<String, String>();
        String status;
        String result = "正在审核中";

        WxStudent wxStudent = wxStudentService.selectByOpenId(openId);

        if (wxStudent == null) {
            map.put("msg", "未提交审核");
            map.put("status", "3");
            return JsonUtils.objectToJson(map);
        }
        if (wxStudent.getStuId()!=null) {
            map.put("msg", "审核通过");
            map.put("status", "1");
            return JsonUtils.objectToJson(map);
        }
        Integer id = wxStudent.getAuditingId();
        System.out.println(wxStudent.getOpenid());
        IdentityAuditing identityAuditing = identityAuditingService.selectById(id);
        if (identityAuditing==null){
            map.put("msg", "未提交审核");
            map.put("status", "3");
            return JsonUtils.objectToJson(map);
        }
        status = identityAuditing.getAuditingStatus();

        if ("0".equals(status))
            result = "正在审核中";
        else if ("1".equals(status))
            result = "审核通过";
        else if ("2".equals(status))
            result = "审核不通过";
        else if ("3".equals(status))
            result = "未提交审核";
        else
            result = "未知错误";
        System.out.println(result);

        map.put("msg", result);
        map.put("status", status);
        return JsonUtils.objectToJson(map);
    }


//    TODO 返回值

    /**
     * HTTP请求140
     * 微信端加载学生可选课信息
     *
     * @param request
     * @return 可选课json
     */
    @RequestMapping(value = {"/wx-loadSelectedCourse"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String sentToWxStudentSelectedCourse(HttpServletRequest request) {
        try {

            String openId = request.getParameter("openId");
            WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
            if (wxStudent == null)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "此openId不存在"));

            Student student = studentService.selectById(wxStudent.getStuId());
            String term = PropertiesUtils.getPropertiesValue("config.properties", "term");
            String clases = String.valueOf(student.getClassNow());
            String grade = student.getGrade();

            List<WxCourse> list = new ArrayList<WxCourse>();

            List<PerClassCourse> perClassCourseList = perClassCourseService.selectByTermAndGradeAndClass(term, grade, clases);
            for (PerClassCourse perClassCourse : perClassCourseList) {
                List<CoursePic> coursePics = coursePicService.selectCoursePicByCourseId(perClassCourse.getCourseId());
                List<String> picList = new ArrayList<String>();
//            TODO ip相对地址0
                for (int i = 0; i < coursePics.size(); i++)
                    picList.add(Url.getUrl(request) + "/" + coursePics.get(i).getPic());

                list.add(new WxCourse(courseService.selectById(perClassCourse.getCourseId()), perClassCourse, picList));
            }
            return JsonUtils.objectToJson(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, ""));

        }
    }

    /**
     * 微信端学生选课结果上传
     *
     * @param request
     * @return 选课结果消息
     */
    @RequestMapping(value = {"/wx-upload-selection"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    synchronized  public String uploadStudentSelection(HttpServletRequest request) throws IOException {
//        TODO 考虑做成多线程，课程有余就返回选课成功
            try {
                String openId = request.getParameter("openId");
                String perCourseId = request.getParameter("id");

                if (openId == null || "".equals(openId))
                    return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "没有上传openId"));
                if (perCourseId == null || "".equals(perCourseId))
                    return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "没有上传选课的ID"));

                WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
                if (wxStudent == null)
                    return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "此openId不存在"));
//                加锁
                if (isLock)
                    wait();
                isLock = true;

                WxResultJson wxResultJson = JsonUtils.jsonToPojo(isHasSelectedStatus(openId), WxResultJson.class);
            if (1 == wxResultJson.getIsSuccessful())
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "不能重复选课"));
                Student student = studentService.selectById(wxStudent.getStuId());
                if (student == null)
                    return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "此学生不存在"));


                PerClassCourse perClassCourse = perClassCourseService.selectByPrimaryId(Integer.valueOf(perCourseId));
                if (perClassCourse == null)
                    return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "选课id无效"));
//              如果课程已满
                if (perClassCourse.getTotalNeedStuAmount() <= perClassCourse.getHaveStuAmount())
                    return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "选课失败，课程已满"));

                 perClassCourseService.updateHaveStuAmountAddOne(perClassCourse.getId());

                Course course = courseService.selectById(perClassCourse.getCourseId());
                if (course == null)
                    return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "课程不存在"));

                if (stuSelectService.insert(new StuSelect(student, course)) == 0)
                    return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "添加选课失败，请联系管理员"));

                isLock=false;
                notify();
            } catch (Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                logger.error(e.getMessage());
                isLock = false;
                notify();
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "选课失败,回滚"));

            } finally {
                isLock = false;
            }
            return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(1, "选课成功"));
    }
    /**
     * 学生本学期已选课列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"wx-get-selection"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String showStudentCourseAlreadySelectedSuccessful(HttpServletRequest request) {
        try {
            Logger logger = LoggerUtlis.getWxControllerLogger();
            Map<String, Object> map = new HashMap<String, Object>();

//        没有查询到这个人报错
            String openId = request.getParameter("openId");
            WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
            if (wxStudent == null)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "此openId不存在"));

            Student student = studentService.selectById(wxStudent.getStuId());
            if (student == null)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "此学生不存在"));

            List<StuSelect> stuSelects = stuSelectService.selectByStudentIdAndTerm(student.getId(), PropertiesUtils.getPropertiesValue("config.properties", "term"));
            if (stuSelects == null || stuSelects.size() == 0)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "未选课"));

            ArrayList<Object> courseList = new ArrayList<Object>();

            StuSelect s = stuSelects.get(0);

            List<PerClassCourse> perClassCourseList = perClassCourseService.selectByCourseIdAndTermAndGradeAndClass(String.valueOf(s.getCourseId()), s.getTerm(), s.getGrade(), s.getClassNow());

            if (perClassCourseList == null || perClassCourseList.size() == 0)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "课程不存在了"));

            logger.debug(JsonUtils.objectToJson(perClassCourseList));
            logger.debug(perClassCourseList.get(0).getId());

            map.put("id", perClassCourseList.get(0).getId());
            map.put("cName", s.getcName());
            map.put("selectTime", s.getSelectTime().toString());
            map.put("cDesc", s.getcDesc());

            List<CoursePic> coursePicList = coursePicService.selectCoursePicByCourseId(s.getCourseId());
            for (CoursePic coursePic : coursePicList)
                courseList.add(Url.getUrl(request)+"/"+ coursePic.getPic());
            map.put("pics", courseList);

            map.put("isSuccessful", "1");
            map.put("msg", "");
            return JsonUtils.objectToJson(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    /**
     * 撤销选课
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"wx-cancel-selection"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    synchronized public String cancelSelect(HttpServletRequest request) {
        try {

            String openId = request.getParameter("openId");
            String perCourseId = request.getParameter("id");
            if (openId == null || "".equals(openId)) {
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "没有上传openId"));
            }

            WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
            if (wxStudent == null)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "此openId不存在"));

            Student student = studentService.selectById(wxStudent.getStuId());
            if (student == null)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "此学生不存在"));

            PerClassCourse perClassCourse = perClassCourseService.selectByPrimaryId(Integer.valueOf(perCourseId));

            if (perClassCourse == null)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "选课id无效"));

            Course course = courseService.selectById(perClassCourse.getCourseId());
            if (course == null)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "课程不存在"));

//            如果没选过
            if (0 == perClassCourse.getHaveStuAmount()) {
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "撤销选课失败，你未曾选过该课程"));
            }
//            加锁
            if (isLock2Cancel)
                wait();
            isLock2Cancel = true;

            int dels = stuSelectService.delByPerCourseIdAndStudentIdAndYear(course.getId(), String.valueOf(student.getId()));
            perClassCourse.setHaveStuAmount(perClassCourse.getHaveStuAmount() - dels >= 0 ? perClassCourse.getHaveStuAmount() - dels : 0);
            if (perClassCourseService.update(perClassCourse) == 0)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "撤销选课失败，请联系管理员"));
            isLock = false;
            notify();
        } catch (Exception e) {
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            isLock2Cancel = false;
            notify();
            return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "撤销失败，事务回滚"));
        } finally {
            isLock2Cancel = false;
        }
        return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(1, "撤销成功"));
    }

    /**
     * 选课状态，是否已选
     *
     * @param openId 微信openId
     * @return
     */
    @RequestMapping(value = {"wx-is-selected-status"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String isHasSelectedStatus(String openId) {
        try {

            if (openId == null || "".equals(openId)) {
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "没有上传openId"));
            }
            WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
            if (wxStudent == null)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "此openId不存在"));

            Student student = studentService.selectById(wxStudent.getStuId());
            if (student == null)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "此学生不存在"));

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, -6);
            List<StuSelect> stuSelectList = stuSelectService.selectByStudentIdAndTerm(student.getId(), PropertiesUtils.getPropertiesValue("config.properties", "term"));
            if (stuSelectList.size() > 0)
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(1, String.valueOf(stuSelectList.size())));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(2, "本学期未选过课"));

    }

    /**
     * 微信端学生获取个人填写信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/wx-get-self-info"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getStudentInfo(HttpServletRequest request) {
        String openId = request.getParameter("openId");
        if (openId == null)
            return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "未找到该学生"));
        WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
        Student student = studentService.selectById(wxStudent.getStuId());
        if (student == null)
            return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "未找到该学生"));
        student.setHeadImg(Url.getUrl(request) + "/" + student.getHeadImg());
        return JsonUtils.objectToJson(student);
    }

    /**
     * 未在选课时间
     * @return
     */
    @RequestMapping(value = {"/wx-out-time"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String outTime() {
        return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "未在选课时间"));
    }

    /**
     * 查询是否在选课时间
     * @return
     */
    @RequestMapping(value = {"/wx-is-in-time"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String isInTime() {
        try {
            String min_date = PropertiesUtils.getPropertiesValue("config.properties", "min_date");
            String max_date = PropertiesUtils.getPropertiesValue("config.properties", "max_date");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long min = simpleDateFormat.parse(min_date).getTime();
            long max = simpleDateFormat.parse(max_date).getTime();
            long now = System.currentTimeMillis();
            if (min < now && max > now) {
                return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(1, "在选课时间"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return JsonUtils.objectToJson(wxResultJson.setIsSuccessfulAndMsg(0, "未在选课时间"));
    }


}

