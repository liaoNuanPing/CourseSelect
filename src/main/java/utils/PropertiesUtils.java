package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesUtils {

    public static void modifyProperties(String fileName,String key,String value){
        String profilepath = PropertiesUtils.class.getResource("/").getPath()+"properties/"+fileName;//我的配置文件在src根目录下
        System.out.println(profilepath);
        try {
            Properties props=new Properties();
            props.load(new FileInputStream(profilepath));
            OutputStream fos = new FileOutputStream(profilepath);
            props.setProperty(key, value);
            props.store(fos, "Update value");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("属性文件更新错误");
        }
    }
}

