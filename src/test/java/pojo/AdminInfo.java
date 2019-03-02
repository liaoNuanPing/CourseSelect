package pojo;

import java.util.Date;

public class AdminInfo {
    private Integer id;

    private String loginName;

    private String psw;

    private String personInCharge;

    private String email;

    private String phone;

    private Date thisLoginTime;

    private Date lastLoginTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw == null ? null : psw.trim();
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge == null ? null : personInCharge.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getThisLoginTime() {
        return thisLoginTime;
    }

    public void setThisLoginTime(Date thisLoginTime) {
        this.thisLoginTime = thisLoginTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public AdminInfo() {
    }

    public AdminInfo(Integer id, String loginName, String psw, String personInCharge, String email, String phone, Date thisLoginTime, Date lastLoginTime) {
        this.id = id;
        this.loginName = loginName;
        this.psw = psw;
        this.personInCharge = personInCharge;
        this.email = email;
        this.phone = phone;
        this.thisLoginTime = thisLoginTime;
        this.lastLoginTime = lastLoginTime;
    }
}