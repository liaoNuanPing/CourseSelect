package pojo;

public class RegisterCode {
    private Integer id;

    private String code;

    private String disable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable == null ? null : disable.trim();
    }

    public RegisterCode(){}

    public RegisterCode(Integer id, String code, String disable) {
        this.id = id;
        this.code = code;
        this.disable = disable;
    }
}