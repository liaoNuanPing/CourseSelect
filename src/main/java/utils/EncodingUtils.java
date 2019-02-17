package utils;

import javax.servlet.http.HttpServletRequest;

public class EncodingUtils {

    /**
     * 前端传来的中文解码
     * @param toUTF8
     * @return
     */
    public static String useToSearch(String toUTF8, HttpServletRequest request) throws Exception {

        String search = new String(request.getParameter("search[value]").getBytes("ISO-8859-1"), "utf-8");
        if (search.contains("?") || search.contains("è")) {
            search = new String(request.getParameter("search[value]").getBytes("ISO-8859-1"), "utf-8");
            if (search.contains("?") || search.contains("è")) {
                search = new String(request.getParameter("search[value]").getBytes("gbk"), "utf-8");
                if (search.contains("?") || search.contains("è")) {
                    search = "";
                }
            }
        }
        return search;
    }
}
