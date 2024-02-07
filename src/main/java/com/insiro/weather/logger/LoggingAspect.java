package com.insiro.weather.logger;


import com.insiro.weather.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

@Aspect
@Component
public class LoggingAspect {
    private AccessLogger accessLogger;
    private  ErrorLogger errorLogger;
    final private static String logPath = "./weather/log/";

    public LoggingAspect() {

        try {
            Path path = Paths.get(logPath);
            if (!Files.isDirectory(path))
                Files.createDirectories(path);
            accessLogger = new AccessLogger(logPath);
            errorLogger = new ErrorLogger(logPath);
        } catch (Exception e) {
            e.fillInStackTrace();
            System.exit(-1);
        }

    }

    @Pointcut("within(com.insiro.weather.controller.*)")
    public void logController() {
    }

    @AfterReturning("logController()")
    public void afterRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) attributes).getResponse();
        String method = request.getMethod();
        accessLogger.log(request.getRequestURL().toString(), response.getStatus(), method);
    }
    @AfterThrowing(value = "logController()", throwing = "ex")
    public void afterException( Exception ex) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        int status ;
        Level logLevel;
        String message;

        if (ex instanceof ApplicationException){
            status = ((ApplicationException) ex).getStatus().value() ;
            logLevel = Level.WARNING;
            message = ex.getMessage();
        }
        else{
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            logLevel = Level.WARNING;
            message = ex.getMessage()+"\n";
            StringWriter stringWriter = new StringWriter();
            PrintWriter pr = new PrintWriter(stringWriter);

            ex.printStackTrace(pr);
            message+=stringWriter.toString();
        }

        String accessLog = accessLogger.log(logLevel,url, status, method);
        errorLogger.log(logLevel,message , accessLog);
    }
}
