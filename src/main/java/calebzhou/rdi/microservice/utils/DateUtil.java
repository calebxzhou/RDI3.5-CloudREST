package calebzhou.rdi.microservice.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static String toReadableChineseDate(Date date){
        return toReadableChineseDate(LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC+8")));
    }
    /**
     * 1.day相同 - 今天
     * 2.day差1  - 昨天
     * 3.day差2~7 - 周123/上周456日
     *
     *
     * @param date 旧日期
     * @return 类似于 今天9：20，昨天2：40，周二2：49，上周六22：45，4月4日12：45，20xx年x月x日 xx:xx
     */
    public static String toReadableChineseDate(LocalDateTime date){
        //当前日期
        LocalDateTime nowDate = LocalDateTime.now();

        //非今年，返回完整记录
        if(date.getYear() != nowDate.getYear()){
            return getFormattedDateTime(date);
        }
        //今年，非今月，返回X月X日 xx:xx记录
        if(date.getMonth().getValue() != nowDate.getMonth().getValue()){
            return getFormattedDateTime(date,"MM月dd日 HH:mm:ss");
        }

        //今年，今月，但是日期是 前天 或更早（），显示周几/上周几
        String datePrefix = null;
        int dateDayOfYear = date.getDayOfYear();
        int nowDateDayOfYear = nowDate.getDayOfYear();
        if(nowDateDayOfYear - dateDayOfYear >= 2){
            int dt2Week=nowDate.getDayOfWeek().getValue();
            int dt1Week=date.getDayOfWeek().getValue();
            //早周X>=晚周X说明是上周
            if(dt1Week>=dt2Week)
                datePrefix="上周"+getWeekByInt(dt1Week);
            else
                datePrefix="周"+getWeekByInt(dt2Week);
        }
        //如果是昨天
        if(dateDayOfYear == nowDateDayOfYear -1){
            datePrefix="昨天";
        }
        //如果是今天
        if(dateDayOfYear == nowDateDayOfYear){
            datePrefix="今天";
        }


        int dateMinute = date.getMinute();
        return datePrefix+date.getHour()+":"
                +  (dateMinute <10? "0"+ dateMinute : dateMinute);

    }
    public static String getFormattedDateTime(LocalDateTime dateTime){
        return getFormattedDateTime(dateTime,"yyyy年MM月dd日(E)HH:mm:ss");
    }
    public static String getFormattedDateTime(LocalDateTime dateTime,String pattern){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(myFormatObj).replace("Mon", "周一")
                .replace("Tue", "周二").replace("Wed", "周三")
                .replace("Thu", "周四").replace("Fri", "周五")
                .replace("Sat", "周六").replace("Sun", "周日");
    }
    public static String getWeekByInt(int day){
        return switch (day) {
            case 1 -> "一";
            case 2 -> "二";
            case 3 -> "三";
            case 4 -> "四";
            case 5 -> "五";
            case 6 -> "六";
            case 7 -> "日";
            default -> "?";
        };
    }
    public static String getTimePeriod(){
        LocalDateTime dateTime = LocalDateTime.now();
        int hour = dateTime.getHour();
        String charTime = "";
        if (hour <= 5)
            charTime = "凌晨";
        if (hour >= 6 && hour <= 8)
            charTime = "早上";
        if (hour >= 9 && hour <= 10)
            charTime = "上午";
        if (hour == 11 || hour == 12)
            charTime = "中午";
        if (hour >= 13 && hour <= 17)
            charTime = "下午";
        if (hour >= 18)
            charTime = "晚上";
        return charTime;
    }
}
