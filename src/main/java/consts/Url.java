package consts;

import javax.servlet.http.HttpServletRequest;

public class Url {
    static String Scheme;
    static String ServerName;
    static String Port;
    static String ContextPath;
    static String url;


    public static String getScheme(HttpServletRequest request) {
        if (Scheme==null)
            Scheme=setScheme(request);
        return Scheme;
    }

    public static String getServerName(HttpServletRequest request) {
        if (ServerName==null)
            ServerName=setServerName(request);
        return ServerName;
    }

    public static String getPort(HttpServletRequest request) {
        if (Port==null)
            Port=setPort(request);
        return Port;
    }

    public static String getContextPath(HttpServletRequest request) {
        if (ContextPath==null)
            ContextPath=setContextPath(request);
        return ContextPath;
    }

    public static String getUrl(HttpServletRequest request) {
        if (url==null){
            url=getScheme(request)+"://"+getServerName(request)+":"+getPort(request)+getContextPath(request);
        }
        return url;
    }

    public static String setScheme(HttpServletRequest request) {
        Scheme = request.getScheme();
        return Scheme;
    }

    public static String setServerName(HttpServletRequest request) {
        ServerName = request.getServerName();
        return ServerName;
    }

    public static String setPort(HttpServletRequest request) {
        Port= String.valueOf( request.getServerPort());
        return Port;

    }

    public static String setContextPath(HttpServletRequest request) {
        ContextPath = request.getContextPath();
        return ContextPath;
    }

    public static String setUrl(String url) {
        Url.url = url;
        return Url.url;
    }

    public static void setScheme(String scheme) {
        Scheme = scheme;
    }

    public static void setServerName(String serverName) {
        ServerName = serverName;
    }

    public static void setPort(String port) {
        Port = port;
    }

    public static void setContextPath(String contextPath) {
        ContextPath = contextPath;
    }
}
