package pojo;

import java.io.Serializable;

public class CourseShow implements Serializable{

    String courseId;
    String course_name;
    String course_desc;
    String id;
    String totalNeedStuAmount;
    String haveStuAmount;
    String grade;
    String toClass;
    String term;

    public CourseShow() {
    }

    public CourseShow(String courseId, String course_name, String course_desc, String id, String grade, String term, String toClass, String totalNeedStuAmount, String haveStuAmount ) {
        this.courseId = courseId;
        this.course_name = course_name;
        this.course_desc = course_desc;
        this.id = id;
        this.totalNeedStuAmount = totalNeedStuAmount;
        this.haveStuAmount = haveStuAmount;
        this.grade = grade;
        this.toClass = toClass;
        this.term = term;
    }

    public CourseShow(Course course,PerClassCourse per) {
        this.courseId = String.valueOf(per.getCourseId());
        this.course_name = course.getcName();
        this.course_desc = course.getcDesc();
        this.id = String.valueOf(per.getId());
        this.totalNeedStuAmount = String.valueOf(per.getTotalNeedStuAmount());
        this.haveStuAmount = String.valueOf(per.getHaveStuAmount());
        this.grade = per.getGrade();
        this.toClass = per.getToClass();
        this.term = per.getTerm();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_desc() {
        return course_desc;
    }

    public void setCourse_desc(String course_desc) {
        this.course_desc = course_desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotalNeedStuAmount() {
        return totalNeedStuAmount;
    }

    public void setTotalNeedStuAmount(String totalNeedStuAmount) {
        this.totalNeedStuAmount = totalNeedStuAmount;
    }

    public String getHaveStuAmount() {
        return haveStuAmount;
    }

    public void setHaveStuAmount(String haveStuAmount) {
        this.haveStuAmount = haveStuAmount;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getToClass() {
        return toClass;
    }

    public void setToClass(String toClass) {
        this.toClass = toClass;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
