package consts;

import org.junit.Test;

public class UrlTest {
    static String Scheme;
    static String ServerName;
    static String Port;
    static String ContextPath;
    static String url;


    public static String getScheme() {
        return Scheme;
    }

    public static String getServerName() {
        return ServerName;
    }

    public static String getPort() {
        return Port;
    }

    public static String getContextPath() {
        return ContextPath;
    }

    @Test
    public String testGetUrl() {
        if (url==null){
            url=getScheme()+"://"+getServerName()+":"+getPort()+"/";
        }
        return url;
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

    public static void setUrl(String url) {
        UrlTest.url = url;
    }
}
