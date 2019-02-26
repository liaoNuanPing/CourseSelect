package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesUtils {

    /**
     * 修改或设置资源文件里的properties文件的key value
     * @param fileName properties的文件名
     * @param key 键
     * @param value 值
     */
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

            profilepath=profilepath.substring(0,profilepath.lastIndexOf("out"));
            profilepath+="src/main/resources/properties/"+fileName;
            props.load(new FileInputStream(profilepath));
            fos = new FileOutputStream(profilepath);
            props.setProperty(key, value);
            props.store(fos, "Update value");

            fos.close();


        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("属性文件更新错误");
        }
    }

    /**
     * 获取资源文件里的properties文件的 value
     * @param fileName properties的文件名
     * @param key 键
     * @return 值
     */
    public static String getPropertiesValue(String fileName,String key){
        String profilepath = PropertiesUtils.class.getResource("/").getPath()+"properties/"+fileName;//我的配置文件在src根目录下
        Properties props=new Properties();
        try {

            props.load(new FileInputStream(profilepath));
            return props.getProperty(key);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("属性文件读取错误");
            return "0";
        }
    }
}

