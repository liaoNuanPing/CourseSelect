package listener;

import consts.Path;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import utils.PropertiesUtils;
import java.io.File;

/**
 * 清理临时图片文件夹的线程，不会停止
 */
@Component
public class DeleteTempListener implements InitializingBean {
    //    5分钟执行一次
    private final long sleepTime=300000;

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(()->{{
            String tempPath = Path.getTempPath();
            File dir = new File(tempPath);

            while (true) {
                try {
//                temp文件夹路径
//                遍历temp，时间大于规定时间就删除
                    if (dir.exists() && dir.isDirectory()) {
                        File[] files = dir.listFiles();
                        for (File file : files) {
                            String createTime =file.getName().substring(0,13);
                            if (System.currentTimeMillis() - Long.valueOf(createTime) > Long.valueOf(PropertiesUtils.getPropertiesValue("config.properties", "del_time"))) {
                                file.delete();
                            }
                        }
                    }
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    if (dir.exists() && dir.isDirectory()) {
                        File[] files = dir.listFiles();
                        for (File file : files)
                            file.delete();

                    }
                }
            }
        }});
    }
}
