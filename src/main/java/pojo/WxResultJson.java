package pojo;

import java.util.List;

public class WxResultJson {
//    0失败，1成功，2成功一半
    int isSuccessful;
    String msg;
    List<String> courseNameList;

    public WxResultJson() {
        isSuccessful=0;
        msg=null;
        courseNameList=null;
    }

    public WxResultJson(int isSuccessful, String msg,List<String> list) {
        this.isSuccessful = isSuccessful;
        this.msg = msg;
        courseNameList=list;
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
}
