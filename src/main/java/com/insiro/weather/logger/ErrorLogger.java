package com.insiro.weather.logger;


import java.util.logging.*;


public class ErrorLogger {
    private final Logger logger;
    public ErrorLogger(String logPath) throws Exception{
        FileHandler errorLogHandler = new FileHandler(logPath + "error.log", true);
        errorLogHandler.setFormatter(new LogFormatter());
        errorLogHandler.setLevel(Level.WARNING);
        logger = Logger.getLogger("ErrorLogger");
        logger.addHandler(errorLogHandler);
    }
    public void log(Level level, String message, String accessLog){
        logger.log(level ,accessLog+message+"\n\n");
    }
}
