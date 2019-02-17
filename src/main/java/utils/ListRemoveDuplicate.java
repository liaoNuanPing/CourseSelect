package utils;

import pojo.CourseShow;

import java.util.HashSet;
import java.util.List;

public class ListRemoveDuplicate {

    //去重
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    //查重
    public static boolean isCourseShowDuplicate(List<CourseShow> list, CourseShow courseShow) {
        for (int i=0;i<list.size();i++ ){
            if (list.get(i).getId().equals(courseShow.getId()))
                return true;
        }
        return false;
    }

    //查有
    public static boolean isCourseShowContainSearch(CourseShow courseShow, String search) {
            if (
                    courseShow.getId().contains(search)||
                    courseShow.getCourseId().contains(search)||
                    courseShow.getCourse_desc().contains(search)||
                    courseShow.getCourse_name().contains(search)||
                    courseShow.getGrade().contains(search)||
                    courseShow.getTerm().contains(search)||
                    courseShow.getToClass().contains(search)||
                    courseShow.getTotalNeedStuAmount().contains(search)||
                    courseShow.getHaveStuAmount().contains(search)
                    )
                return true;
        
        return false;
    }
    
}
