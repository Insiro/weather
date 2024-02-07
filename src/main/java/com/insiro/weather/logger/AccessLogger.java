package com.insiro.weather.logger;

import java.util.logging.*;


public class AccessLogger {
    private final Logger logger;
    public AccessLogger(String logPath) throws Exception{
        FileHandler accessLogHandler = new FileHandler(logPath + "access.log", true);
        accessLogHandler.setFormatter(new LogFormatter());
        accessLogHandler.setLevel(Level.INFO);
        logger = Logger.getLogger("AccessLogger");
        logger.addHandler(accessLogHandler);
    }
    public String log(String url, int responseStatus, String method){
        return this.log(Level.INFO, url, responseStatus, method);
    }
    public String log(Level level,String url, int responseStatus, String method){
        String msg = String.format("%s : %s %d\n",method, url, responseStatus);
        logger.log(level, msg);
        return msg;
    }
}
