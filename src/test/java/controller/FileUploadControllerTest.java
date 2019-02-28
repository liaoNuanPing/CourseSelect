package controller;

import org.junit.Test;
import utils.PropertiesUtilsTest;

import java.io.File;

public class FileUploadControllerTest {
    @Test
    public void testRename(){
        String profilepath = PropertiesUtilsTest.class.getResource("/").getPath()+"properties/config.properties";//我的配置文件在src根目录下
        profilepath=profilepath.substring(0,profilepath.lastIndexOf("target"));
        profilepath+="src/main/resources";
        File file=new File(profilepath+"/properties/config.properties");
        File file1=new File(profilepath + "/img");
        boolean mkdir = file1.mkdir();
        System.out.println(mkdir);
        boolean b = file.renameTo(new File(profilepath + "/img/new.properties"));
        System.out.println(b);
        System.out.println(profilepath+"/img/new.properties");

    }
}
