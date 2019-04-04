package handler;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 拦截验证登录
 */
public class LoginHandler  {
//    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object o) throws Exception {
//        String servletPath = req.getServletPath();
//
//        if (servletPath.contains("wx")) {
//            if (servletPath.contains("wx-Audit-add") || servletPath.contains("wx-Audit-status")|| servletPath.contains("time"))
//                return true;
//            else{
//
//                String min_date = PropertiesUtils.getPropertiesValue("config.properties", "min_date");
//                String max_date = PropertiesUtils.getPropertiesValue("config.properties", "max_date");
//
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                long min= simpleDateFormat.parse(min_date).getTime();
//                long max= simpleDateFormat.parse(max_date).getTime();
//                long now = System.currentTimeMillis();
//                if (min>now||max<now){
////                    登录时间限制
//                    req.getRequestDispatcher("wx-out-time").forward(req, resp);
//                }
//
//            }
//
//        }
//
////        请求
//        if (servletPath.contains("AdminLogin"))
//            return true;
//        else if (servletPath.contains("SystemLogin"))
//            return true;
//        else if (servletPath.contains("Image"))
//            return true;
//        else if (servletPath.contains("static") || servletPath.contains("lib"))
//            return true;
//        else {
//            HttpSession session = req.getSession();
//            if (session != null && session.getAttribute("isLogin") != null && session.getAttribute("isLogin").equals("true"))
//                return true;
//        }
//        req.getRequestDispatcher("SystemLogin").forward(req, resp);
//        return false;
//    }

    public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
