package pojo;

import java.util.Date;

public class WxStudent {
    private Integer id;

    private String openid;

    private Integer stuId;

    private Integer auditingId;

    private Date createTime;

    public WxStudent() {
    }

    public WxStudent(Integer id, String openid, Integer stuId, Integer auditingId, Date createTime) {
        this.id = id;
        this.openid = openid;
        this.stuId = stuId;
        this.auditingId = auditingId;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getAuditingId() {
        return auditingId;
    }

    public void setAuditingId(Integer auditingId) {
        this.auditingId = auditingId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}