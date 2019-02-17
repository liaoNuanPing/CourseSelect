package pojo;

public class CoursePic {
    private Integer id;

    private Integer courseId;

    private String pic;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public CoursePic() {
    }

    public CoursePic(Integer id, Integer courseId, String pic) {
        this.id = id;
        this.courseId = courseId;
        this.pic = pic;
    }
}