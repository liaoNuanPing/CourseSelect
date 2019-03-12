package pojo;

import org.springframework.stereotype.Repository;

public class Course {
    private Integer id;

    private String cName;

    private String cDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Course() {
    }

    public Course(Integer id, String cName, String cDesc) {
        this.id = id;
        this.cName = cName;
        this.cDesc = cDesc;
    }
}