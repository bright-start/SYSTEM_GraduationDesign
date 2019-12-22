package com.cys.system.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date StringToDate(String time) throws ParseException {
        return FORMAT.parse(time);
    }

    public static String DateToString(Date date){
        return FORMAT.format(date);
    }
}
