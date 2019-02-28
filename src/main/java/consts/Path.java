package consts;

public class Path {
    private static String imagesPath=null;
    private static String tempPath=null;

    public static String getImagesPath() {
        if (imagesPath==null){
            String classesPath = Path.class.getClassLoader().getResource("/").getPath();
            String[] rootPath = classesPath.split("WEB-INF");
            imagesPath = rootPath[0] + "static/images";
        }
        return imagesPath;
    }

    public static String getTempPath() {
        if (tempPath==null){
            String classesPath = Path.class.getClass().getClassLoader().getResource("/").getPath();
            String[] rootPath = classesPath.split("WEB-INF");
            tempPath = rootPath[0] + "static/temp";
        }
        return tempPath;
    }
}
