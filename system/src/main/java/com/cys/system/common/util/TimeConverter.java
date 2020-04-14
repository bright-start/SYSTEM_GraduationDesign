package com.cys.system.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter extends TimeFormat{

    private static final TimeConverter timeConverter = new TimeConverter();
    private TimeConverter(){

    }

    public static TimeConverter getInstance(){
        return timeConverter;
    }
    public Date StringToDate(String time, String format) throws ParseException {
        SimpleDateFormat FORMAT = new SimpleDateFormat(format);
        return FORMAT.parse(time);
    }

    public String DateToString(Date date, String format) {
        SimpleDateFormat FORMAT = new SimpleDateFormat(format);
        return FORMAT.format(date);
    }

}
