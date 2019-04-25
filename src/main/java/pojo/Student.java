package pojo;

import java.io.Serializable;

public class Student implements Serializable{
    private Integer id;

    private String stuName;

    private String grade;

    private String classNow;

    private String parentName;

    private String parentPhone;

    private String parentCode;

    private String headImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getParentCode() { return parentCode; }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public Student() {
    }

    public Student(Integer id, String stuName, String grade, String classNow, String parentName, String parentPhone,String parentCode, String headImg) {
        this.id = id;
        this.stuName = stuName;
        this.grade = grade;
        this.classNow = classNow;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentCode = parentCode;
        this.headImg = headImg;
    }
}