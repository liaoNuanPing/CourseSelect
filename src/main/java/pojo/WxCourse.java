package pojo;

import java.util.List;

public class WxCourse {

    Integer courseId;
    String course_name;
    String course_desc;
    Integer id;
    Integer totalNeedStuAmount;
    Integer haveStuAmount;
    String grade;
    String toClass;
    String term;
    List<String> pic;

    public WxCourse() {
    }

    public WxCourse(Course course,PerClassCourse perClassCourse,List<String> pic) {
        this.courseId = course.getId();
        this.course_name = course.getcName();
        this.course_desc = course.getcDesc();
        this.id = perClassCourse.getId();
        this.totalNeedStuAmount = perClassCourse.getTotalNeedStuAmount();
        this.haveStuAmount = perClassCourse.getHaveStuAmount();
        this.grade = perClassCourse.getGrade();
        this.toClass = perClassCourse.getToClass();
        this.term = perClassCourse.getTerm();
        this.pic = pic;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalNeedStuAmount() {
        return totalNeedStuAmount;
    }

    public void setTotalNeedStuAmount(Integer totalNeedStuAmount) {
        this.totalNeedStuAmount = totalNeedStuAmount;
    }

    public Integer getHaveStuAmount() {
        return haveStuAmount;
    }

    public void setHaveStuAmount(Integer haveStuAmount) {
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

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }
}
