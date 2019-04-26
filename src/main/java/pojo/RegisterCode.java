package pojo;

public class RegisterCode {
    private String code;

    private String disable;

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

    public RegisterCode() {
    }

    public RegisterCode(String code, String disable) {
        this.code = code;
        this.disable = disable;
    }
}