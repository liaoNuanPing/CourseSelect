package pojo;

import java.io.Serializable;

public class StudentShow implements Serializable {
    String id ;
    String student_card ;
    String stu_name;
    String grade ;
    String class_now ;
    String parent_name ;
    String parent_phone ;
    String head_img ;
    String course_id;
    String c_name ;
    String c_desc;
    String select_time;

    public StudentShow() {
    }

    public StudentShow(String id, String student_card, String stu_name, String grade, String class_now, String parent_name, String parent_phone, String head_img, String course_id, String c_name, String c_desc, String select_time) {
        this.id = id;
        this.student_card = student_card;
        this.stu_name = stu_name;
        this.grade = grade;
        this.class_now = class_now;
        this.parent_name = parent_name;
        this.parent_phone = parent_phone;
        this.head_img = head_img;
        this.course_id = course_id;
        this.c_name = c_name;
        this.c_desc = c_desc;
        this.select_time = select_time;
    }

    public StudentShow(Student student, Course course) {
        this.id = null;
        this.student_card = String.valueOf(student.getId());
        this.stu_name = student.getStuName();
        this.grade = student.getGrade();
        this.class_now = student.getClassNow();
        this.parent_name = student.getParentName();
        this.parent_phone = student.getParentPhone();
        this.head_img = student.getHeadImg();
        this.course_id = String.valueOf(course.getId());
        this.c_name = course.getcName();
        this.c_desc = course.getcDesc();
        this.select_time = null;
    }


    public String getStudent_card() {
        return student_card;
    }

    public void setStudent_card(String student_card) {
        this.student_card = student_card;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClass_now() {
        return class_now;
    }

    public void setClass_now(String class_now) {
        this.class_now = class_now;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(String parent_phone) {
        this.parent_phone = parent_phone;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getId() {
        return id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_desc() {
        return c_desc;
    }

    public void setC_desc(String c_desc) {
        this.c_desc = c_desc;
    }

    public String getSelect_time() {
        return select_time;
    }

    public void setSelect_time(String select_time) {
        this.select_time = select_time;
    }
}
