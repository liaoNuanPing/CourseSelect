package thread;

import org.junit.Test;
import utils.PropertiesUtilsTest;

import java.io.File;

public class DeleteTempThreadTest {

    @Test
    public void testDel(){
        Runnable runnable=new Runnable() {
            public void run() {
                while (true) {
                    try {

//                temp文件夹路径
                        String profilepath = PropertiesUtilsTest.class.getResource("/").getPath() + "properties/config.properties";//我的配置文件在src根目录下
                        profilepath = profilepath.substring(0, profilepath.lastIndexOf("target"));
                        String tempPath = profilepath + "src/test/resources/img";

//                遍历temp，时间大于规定时间就删除
                        File dir = new File(tempPath);
                        if (dir.exists() && dir.isDirectory()) {
                            File[] files = dir.listFiles();
                            for (File file : files) {
                                String split =file.getName().substring(0,file.getName().lastIndexOf("."));
                                if (System.currentTimeMillis() - Long.valueOf(split) > 30000)
                                    System.out.println(file.delete());
                            }
                        }
                        Thread.sleep(5000);
                        System.out.println("5秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        };

        Thread thread=new Thread(runnable);
        thread.run();
    }

    @Test
    public void testCur(){

        System.out.println( System.currentTimeMillis());
    }
}
