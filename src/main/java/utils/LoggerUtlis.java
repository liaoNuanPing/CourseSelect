package utils;

import consts.Path;
import controller.WxController;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerUtlis {

    static Logger wxLogger=null;

    public static Logger getWxControllerLogger(){
        if (wxLogger==null) {
            wxLogger = Logger.getLogger(WxController.class);
            String[] p = Path.getTempPath().split("static");
            String rp = p[0] + "WEB-INF/classes/properties/log4j.properties";
            PropertyConfigurator.configure(rp);
            wxLogger.setLevel(Level.WARN);
        }
        return wxLogger;
    }
}
