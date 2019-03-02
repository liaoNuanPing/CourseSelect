package utils;

import org.junit.Test;
import pojo.StuSelect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

public class Time {

    @Test
    public void t() throws SQLException {
        StuSelect stuSelect=new StuSelect();
        Connection conn = ConnectDB.getConnection();
        String sql="select select_time from stu_select where id=2";
        Statement pstm = conn.createStatement();
        ResultSet rs = pstm.executeQuery(sql);
        while (rs.next()) {
             stuSelect = new StuSelect(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    rs.getTimestamp("select_time")
                    );//end CourseShow
        }//end while

        int m=stuSelect.getSelectTime().getMonth()+1;
        // 9-1 第一学期 2-6 第二学期
//        1|2|3456|78|9 10 11 12 1|2|3456|78| 9 10 11 12
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH,-6);

        System.out.println(cal.getTimeInMillis());
    }
}
