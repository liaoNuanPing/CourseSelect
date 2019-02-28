package thread;

import utils.PropertiesUtils;

import java.io.File;

public class DeleteTempThread implements Runnable {

//    5分钟执行一次
    private final long sleepTime=300000;

    private static DeleteTempThread ourInstance = new DeleteTempThread();

    public static DeleteTempThread getInstance() {
        return ourInstance;
    }

    private DeleteTempThread() {
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(sleepTime);
//                temp文件夹路径
                String classesPath = this.getClass().getClassLoader().getResource("/").getPath();
                String[] rootPath = classesPath.split("WEB-INF");
                String tempPath = rootPath[0] + "static/temp";
//                遍历temp，时间大于规定时间就删除
                File dir = new File(tempPath);
                if (dir.exists() && dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        String createTime =file.getName().substring(0,file.getName().indexOf("["));
                        if (System.currentTimeMillis() - Long.valueOf(createTime) > Long.valueOf(PropertiesUtils.getPropertiesValue("config.properties", "del_time")))
                            file.delete();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
