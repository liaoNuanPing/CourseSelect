package utils;

import org.junit.Test;

import java.io.*;
import java.util.Properties;

public class PropertiesUtilsTest {

    @Test
    public void testModifyProperties(){
        String profilepath = PropertiesUtilsTest.class.getResource("/").getPath()+"properties/config.properties";//我的配置文件在src根目录下
        System.out.println(profilepath);

        try {
            Properties props=new Properties();
            props.load(new FileInputStream(profilepath));
            OutputStream fos = new FileOutputStream(profilepath);
            props.setProperty("term", "666");
            props.store(fos, "Update value");
            System.out.println( props.getProperty("term"));
            fos.close();
            throw new Exception("2333");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("属性文件更新错误");
        }
    }

    @Test
    public void testGetPropertiesValue(){

        String profilepath = PropertiesUtilsTest.class.getResource("/").getPath()+"properties/config.properties";//我的配置文件在src根目录下
        Properties props=new Properties();

        try {
            props.load(new FileInputStream(profilepath));
            System.out.println( props.getProperty("term"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("属性文件读取错误");
        }
    }





}

