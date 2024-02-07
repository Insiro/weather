package com.insiro.weather.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(record.getMillis());
        return String.format("%s [%s] %s", dateFormat.format(date), record.getLevel(), record.getMessage());
    }
}
