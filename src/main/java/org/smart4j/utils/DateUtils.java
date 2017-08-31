package org.smart4j.utils;

import org.smart4j.ConfigConstant;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ithink on 17-8-28.
 */
public class DateUtils {

    /**
     * 计算起始日期到结束日期的天数
     * @param start inclusive
     * @param end inclusive
     * @return
     */
    public static long getDays(String start, String end) throws ParseException{

        Date startDate = ConfigConstant.DATE_FORMAT.parse(start);
        Date endDate = ConfigConstant.DATE_FORMAT.parse(end);

        long days = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24) + 1;

        return days;
    }

    /**
     * 计算起始日期到结束日期的天数
     * @param startDate inclusive
     * @param endDate inclusive
     * @return
     */
    public static long getDays(Date startDate, Date endDate){

        long days = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24) + 1;

        return days;
    }

    /**
     * 用于日期递增
     */
    public static Calendar getCalendar(Date startDate){
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(startDate);

        return calendar;
    }

}
