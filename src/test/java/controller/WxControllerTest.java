package controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.*;
import service.*;
import service.impl.*;
import utils.JsonUtils;
import utils.PropertiesUtils;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext-dao.xml"})
public class WxControllerTest {



    WxStudentService wxStudentService=new WxStudentServiceImpl();

    StudentService studentService=new StudentServiceImpl();

    PerClassCourseService perClassCourseService=new PerClassCourseServiceImpl();

    CoursePicService coursePicService=new CoursePicServiceImpl();

    CourseService courseService =new CourseServiceImpl();

    IdentityAuditingService identityAuditingService=new IdentityAuditingServiceImpl();

    StuSelectService stuSelectService =new StuSelectServiceImpl();


    @Autowired
    AdminInfoService adminInfoService;

    @Test
    public void testUploadStudentSelection() {

        String openId = "test";
        WxStudent wxStudent = wxStudentService.selectByOpenId(openId);
        if (wxStudent == null) {
            System.out.println(JsonUtils.objectToJson(new WxResultJson()));
            return;
        }

        List<String> failClassName = new ArrayList<String>();
        Student student = studentService.selectById(wxStudent.getStuId());
        String[] courseIds = {"1","37"};
        for (String id : courseIds) {
            List<PerClassCourse> perClassCourseList = perClassCourseService.selectByCourseIdAndTermAndGradeAndClass(
                    Integer.valueOf(id),
                    PropertiesUtils.getPropertiesValue("config.properties", "term"),
                    student.getGrade(),
                    student.getClassNow());

            Course course = courseService.selectById(Integer.valueOf(id));
            if (course == null) {
                System.out.println(JsonUtils.objectToJson(new WxResultJson()));
                return;
            }

//            如果课程大于
            if (perClassCourseList == null ||
                    perClassCourseList.get(0).getTotalNeedStuAmount() >= perClassCourseList.get(0).getHaveStuAmount()
                    || stuSelectService.insert(new StuSelect(student, course)) == 0) {
                failClassName.add(course.getcName());
            }
        }

        if (failClassName.size() > 0) {
            System.out.println(JsonUtils.objectToJson(new WxResultJson(2, "部分选课失败，请检查再选过", failClassName)));
            return;
        }
        System.out.println(JsonUtils.objectToJson(new WxResultJson(1, null, null)));



    }


    @Test
    public void testService(){
         adminInfoService.select();
    }
}
