package pojo;

import consts.Path;
import utils.PropertiesUtils;

import java.util.Date;

public class StuSelect {
    private Integer id;

    private String studentCard;

    private String stuName;

    private String grade;

    private String classNow;

    private String parentName;

    private String parentPhone;

    private String headImg;

    private Integer courseId;

    private String cName;

    private String cDesc;

    private Date selectTime;

    private String term;


    public StuSelect() {
    }

    public StuSelect(Integer id, String studentCard, String stuName, String grade, String classNow, String parentName, String parentPhone, String headImg, Integer courseId, String cName, String cDesc, Date selectTime) {
        this.id = id;
        this.studentCard = studentCard;
        this.stuName = stuName;
        this.grade = grade;
        this.classNow = classNow;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.headImg = headImg;
        this.courseId = courseId;
        this.cName = cName;
        this.cDesc = cDesc;
        this.selectTime = selectTime;
        this.term= PropertiesUtils.getPropertiesValue("config.properties","term");

    }

    public StuSelect(Student student, Course course) {
        this.id = null;
        this.studentCard = String.valueOf(student.getId());
        this.stuName = student.getStuName();
        this.grade = student.getGrade();
        this.classNow = student.getClassNow();
        this.parentName = student.getParentName();
        this.parentPhone = student.getParentPhone();
        this.headImg = student.getHeadImg();
        this.courseId = course.getId();
        this.cName = course.getcName();
        this.cDesc = course.getcDesc();
        this.selectTime = null;
        this.term= PropertiesUtils.getPropertiesValue("config.properties","term");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentCard() {
        return studentCard;
    }

    public void setStudentCard(String studentCard) {
        this.studentCard = studentCard == null ? null : studentCard.trim();
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName == null ? null : stuName.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getClassNow() {
        return classNow;
    }

    public void setClassNow(String classNow) {
        this.classNow = classNow == null ? null : classNow.trim();
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName == null ? null : parentName.trim();
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone == null ? null : parentPhone.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName == null ? null : cName.trim();
    }

    public String getcDesc() {
        return cDesc;
    }

    public void setcDesc(String cDesc) {
        this.cDesc = cDesc == null ? null : cDesc.trim();
    }

    public Date getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(Date selectTime) {
        this.selectTime = selectTime;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}