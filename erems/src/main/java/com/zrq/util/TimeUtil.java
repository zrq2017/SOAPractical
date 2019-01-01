package com.zrq.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String formatDate(String patten,Date date){
        //创建日期格式化对象   因为DateFormat类为抽象类 所以不能new
        DateFormat bf = new SimpleDateFormat(patten);//多态
        String format = bf.format(date);//格式化 bf.format(date);
        return format;
    }
}
