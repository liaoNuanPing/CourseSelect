package consts;

import controller.FileuploadController;

public class Path {
    private static String imagesPath=null;
    private static String tempPath=null;

    public static String getImagesPath() {
        try {
            if (imagesPath == null) {
                String classesPath = Path.class.getClassLoader().getResource("/").getPath();
                String[] rootPath = classesPath.split("WEB-INF");
                imagesPath = rootPath[0] + "static/images";
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return imagesPath;
    }

    public static String getTempPath() {
        try {
            if (tempPath == null) {
                String classesPath = Path.class.getClassLoader().getResource("/").getPath();
                String[] rootPath = classesPath.split("WEB-INF");
                tempPath = rootPath[0] + "static/temp";
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return tempPath;
    }
}
