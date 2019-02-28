package controller;

import consts.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.*;
import utils.JsonUtils;
import utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
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
    public String identityAuditAdd(HttpServletRequest request) {
        Map<String, String> map=new HashMap<String, String>();
        try {
//        TODO 数据名称校验 和微信图片上传

            String openId = request.getParameter("openId");
            String stuId = request.getParameter("student_id");
            String stuName = request.getParameter("student_name");
            String parentName = request.getParameter("parent_name");
            String mobile = request.getParameter("mobile");
            String grade = request.getParameter("grade");
            String classNow = request.getParameter("class_now");

            if (stuName==null||parentName==null)
                throw new Exception("学生信息不能为空");

            String headImg="";
            String newHeadImg=String.valueOf(System.currentTimeMillis())+"["+ stuName+"And"+parentName+"]";
            File dir = new File(Path.getTempPath());
            if (dir.exists() && dir.isDirectory()) {
                File[] files = dir.listFiles();
                Long biggest= Long.valueOf(1000000);
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
                if(new File(Path.getTempPath()+"/"+headImg).renameTo(new File(Path.getImagesPath()+"/"+newHeadImg)))
                    throw new Exception("移动图片从temp到images不成功");

            IdentityAuditing identityAuditing = new IdentityAuditing(
                    null,
                    stuName,
                    parentName,
                    mobile, grade,
                    classNow,
                    newHeadImg==null?null:"static/images/"+newHeadImg,
                    null,
                    null);

            identityAuditingService.insert(identityAuditing);

//            插入WxStudent
            wxStudentService.insert(new WxStudent(null,openId,null,identityAuditing.getId(),null));

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
    @RequestMapping(value = {"/wx-Audit-status"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String AuditStatus(HttpServletRequest request){
        String openId = request.getParameter("openId");
        WxStudent wxStudent = wxStudentService.selectByOpenId(openId);

        Map<String, String> map=new HashMap<String, String>();
        Integer id=wxStudent.getAuditingId();
        String status;
        String result="正在审核中";
        try {
            IdentityAuditing identityAuditing = identityAuditingService.selectById(id);
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


    /**
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
            List<CoursePic> coursePics = coursePicService.selectCoursePic(perClassCourse.getCourseId());
            List<String> picList =new ArrayList<String>();
            for (int i=0;i<coursePics.size();i++)
                picList.add("http://47.107.128.33:8080/CourseSelect/"+coursePics.get(i).getPic());

            list.add(new WxCourse(courseService.selectById(perClassCourse.getCourseId()),perClassCourse,picList));
        }

        System.out.println(JsonUtils.objectToJson(list));
        return JsonUtils.objectToJson(list);
    }

    /**
     * 微信端学生选课结果上传
     * @param request
     * @return 选课结果消息
     */
    @RequestMapping(value = {"/wx-upload-selection"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String uploadStudentSelection(HttpServletRequest request){
//        TODO 考虑做成多线程，课程有余就返回选课成功
        String openId = request.getParameter("openId");
        WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
        if (wxStudent==null)
            return JsonUtils.objectToJson(new WxResultJson());

        List<String> failClassName=new ArrayList<String>();
        Student student = studentService.selectById(wxStudent.getStuId());
        String[] courseIds = request.getParameterValues("courseId");
        for (String id:courseIds){
            List<PerClassCourse> perClassCourseList = perClassCourseService.selectByCourseIdAndTermAndGradeAndClass(
                    Integer.valueOf(id),
                    PropertiesUtils.getPropertiesValue("config.properties", "term"),
                    student.getGrade(),
                    student.getClassNow());

            Course course = courseService.selectById(Integer.valueOf(id));
            if (course==null)
                return JsonUtils.objectToJson(new WxResultJson());

//            如果课程大于
            if (perClassCourseList==null||
                    perClassCourseList.get(0).getTotalNeedStuAmount()>=perClassCourseList.get(0).getHaveStuAmount()
                    ||stuSelectService.insert(new StuSelect(student, course))==0){
                failClassName.add(course.getcName());
            }
        }

        if (failClassName.size()>0)
            return JsonUtils.objectToJson(new WxResultJson(2,"部分选课失败，请检查再选过",failClassName));
        return JsonUtils.objectToJson(new WxResultJson(1,null,null));

    }


    /**
     * 学生本学期已选课列表
     * @param request
     * @return
     */
    @RequestMapping("wx-get-selection")
    @ResponseBody
    public String showStudentCourseAlreadySelectedSuccessful(HttpServletRequest request){
//        没有查询到这个人报错
        String openId = request.getParameter("openId");
        WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
        if (wxStudent==null)
            return JsonUtils.objectToJson(new WxResultJson());

        List<StuSelect> stuSelects = stuSelectService.selectByStudentIdAndTerm(wxStudent.getStuId(),PropertiesUtils.getPropertiesValue("config.properties","term"));
        if (stuSelects==null||stuSelects.size()==0)
            return JsonUtils.objectToJson(new WxResultJson(1,"未选课",null));
        List<String> courseList=new ArrayList<String>();
        for (StuSelect s: stuSelects){
            courseList.add(s.getcName());
        }
//        TODO 只返回了课程名
        return JsonUtils.objectToJson(new WxResultJson(1,null,courseList));
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
            return JsonUtils.objectToJson(new WxResultJson(0,"未找到该学生",null));
        WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
        Student student = studentService.selectById(wxStudent.getStuId());
        if(student==null)
            return JsonUtils.objectToJson(new WxResultJson(0,"未找到该学生",null));
        student.setHeadImg("http://47.107.128.33:8080/CourseSelect/"+ student.getHeadImg());
        return JsonUtils.objectToJson(student);

    }

}
