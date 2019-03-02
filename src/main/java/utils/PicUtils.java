package utils;

import consts.Path;

import java.io.File;

public class PicUtils {

    public static String getNewCourseImageName(String oldName){
        return String.valueOf(System.currentTimeMillis())+"course"+oldName.substring(oldName.lastIndexOf("."),oldName.length());
    }

    public static String getNewStuentImageName(String oldName){
        return String.valueOf(System.currentTimeMillis())+"student"+oldName.substring(oldName.lastIndexOf("."),oldName.length());
    }

    public static boolean rename(String oldName,String newImgName) throws Exception {
        if (!"".equals(oldName)&&!"".equals(newImgName)){
            if(!new File(Path.getTempPath()+"/"+oldName).renameTo(new File(Path.getImagesPath()+"/"+newImgName)))
                throw new Exception("移动图片从temp到images不成功");
            return true;
        }
        return false;
    }
}
