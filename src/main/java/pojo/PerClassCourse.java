package pojo;

public class PerClassCourse {
    private Integer id;

    private Integer courseId;

    private String grade;

    private String term;

    private String toClass;

    private Integer totalNeedStuAmount;

    private Integer haveStuAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    public String getToClass() {
        return toClass;
    }

    public void setToClass(String toClass) {
        this.toClass = toClass == null ? null : toClass.trim();
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

    public PerClassCourse() {
    }

    public PerClassCourse(Integer id, Integer courseId, String grade, String term, String toClass, Integer totalNeedStuAmount, Integer haveStuAmount) {
        this.id = id;
        this.courseId = courseId;
        this.grade = grade;
        this.term = term;
        this.toClass = toClass;
        this.totalNeedStuAmount = totalNeedStuAmount;
        this.haveStuAmount = haveStuAmount;
    }
}