package pojo;

import java.io.Serializable;
import java.util.List;

public class WxResultJson implements Serializable {
//    0失败，1成功
    int isSuccessful;
    String msg;

    public WxResultJson() {
        this.isSuccessful=0;
        this.msg=null;
    }

    public WxResultJson(int isSuccessful, String msg ) {
        this.isSuccessful = isSuccessful;
        this.msg = msg;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WxResultJson setIsSuccessfulAndMsg(int isSuccessful,String msg){
        this.msg = msg;
        this.isSuccessful = isSuccessful;
        return this;
    }

}
