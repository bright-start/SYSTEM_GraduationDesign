package com.cys.system.common.util;

import java.text.ParseException;
import java.util.Date;

public abstract class TimeFormat {
    public static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public static final String Y_M = "yyyy.MM";

    abstract Date StringToDate(String time,String format) throws ParseException;
    abstract String DateToString(Date date,String format);

}
