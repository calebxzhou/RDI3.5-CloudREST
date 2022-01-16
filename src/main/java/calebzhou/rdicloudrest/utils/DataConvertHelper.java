package calebzhou.rdicloudrest.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class DataConvertHelper {
    public static boolean toBoolean(Object object) {
        if (isNullOrEmpty(object)) {
            return false;
        } else {
            Class<?> cls = object.getClass();
            if (cls.equals(String.class)) {
                return Boolean.parseBoolean((String) object);
            } else if (cls.equals(byte.class) || cls.equals(short.class) || cls.equals(int.class)
                    || cls.equals(long.class) || cls.equals(float.class) || cls.equals(double.class)) {
                return ((int) object > 0);
            } else {
                return (boolean) object;
            }
        }
    }

    public static BigDecimal toBigDecimal(Object object) {
        if (isNullOrEmpty(object)) {
            return null;
        } else {
            try {
                return new BigDecimal(object.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    public static byte toByte(Object object) {
        return (byte) toLong(object);
    }

    public static short toShort(Object object) {
        return (short) toLong(object);
    }

    public static int toInt(Object object) {
        return (int) toLong(object);
    }

    public static long toLong(Object object) {
        BigDecimal decimal = toBigDecimal(object);
        return decimal == null ? 0 : decimal.longValue();
    }

    public static float toFloat(Object object) {
        return (float) toDouble(object);
    }

    public static double toDouble(Object object) {
        BigDecimal decimal = toBigDecimal(object);
        return decimal == null ? 0 : decimal.doubleValue();
    }

    public static Integer toInteger(Object object) {
        BigDecimal decimal = toBigDecimal(object);
        return decimal == null ? null : decimal.intValue();
    }

    public static String toString(Object object) {
        return (object == null) ? "" : object.toString();
    }

    public static String formatDateByDate(Date date) {
        return formatDate(date, "yyyy/MM/dd");
    }

    public static String formatDateByTime(Date date) {
        return formatDate(date, "HH:mm:ss");
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy/MM/dd HH:mm:ss");
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static boolean isNullOrEmpty(Object string) {
        return string == null || isNullOrEmpty(string.toString());
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.equals("");
    }

    public static boolean isNotNullOrEmpty(Object string) {
        return !isNullOrEmpty(string);
    }

    public static boolean isNotNullOrEmpty(String string) {
        return !isNullOrEmpty(string);
    }

    public static <T> boolean isCollectionEmpty(Collection<T> col){
        return (col == null || col.isEmpty());
    }

    public static <T> boolean isCollectionNotEmpty(Collection<T> col){
        return !isCollectionEmpty(col);
    }

    public static Date parseDateByString(String text, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(text);
    }

    public static Date parseDateByString(String text) throws ParseException{
        return parseDateByString(text, "yyyy/MM/dd HH:mm:ss");
    }
}
