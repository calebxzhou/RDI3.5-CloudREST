package calebzhou.rdi.microservice.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {
    fun toReadableChineseDate(date: Date): String {
        return toReadableChineseDate(LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC+8")))
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
    fun toReadableChineseDate(date: LocalDateTime): String {
        //当前日期
        val nowDate = LocalDateTime.now()

        //非今年，返回完整记录
        if (date.year != nowDate.year) {
            return getFormattedDateTime(date)
        }
        //今年，非今月，返回X月X日 xx:xx记录
        if (date.month.value != nowDate.month.value) {
            return getFormattedDateTime(date, "MM月dd日 HH:mm:ss")
        }

        //今年，今月，但是日期是 前天 或更早（），显示周几/上周几
        var datePrefix: String? = null
        val dateDayOfYear = date.dayOfYear
        val nowDateDayOfYear = nowDate.dayOfYear
        if (nowDateDayOfYear - dateDayOfYear >= 2) {
            val dt2Week = nowDate.dayOfWeek.value
            val dt1Week = date.dayOfWeek.value
            //早周X>=晚周X说明是上周
            datePrefix =
                if (dt1Week >= dt2Week) "上周" + getWeekByInt(dt1Week) else "周" + getWeekByInt(
                    dt2Week
                )
        }
        //如果是昨天
        if (dateDayOfYear == nowDateDayOfYear - 1) {
            datePrefix = "昨天"
        }
        //如果是今天
        if (dateDayOfYear == nowDateDayOfYear) {
            datePrefix = "今天"
        }
        val dateMinute = date.minute
        return (datePrefix + date.hour + ":"
                + if (dateMinute < 10) "0$dateMinute" else dateMinute)
    }

    fun getFormattedDateTime(dateTime: LocalDateTime): String {
        return getFormattedDateTime(dateTime, "yyyy年MM月dd日(E)HH:mm:ss")
    }

    fun getFormattedDateTime(dateTime: LocalDateTime, pattern: String?): String {
        val myFormatObj = DateTimeFormatter.ofPattern(pattern)
        return dateTime.format(myFormatObj).replace("Mon", "周一")
            .replace("Tue", "周二").replace("Wed", "周三")
            .replace("Thu", "周四").replace("Fri", "周五")
            .replace("Sat", "周六").replace("Sun", "周日")
    }

    fun getWeekByInt(day: Int): String {
        return when (day) {
            1 -> "一"
            2 -> "二"
            3 -> "三"
            4 -> "四"
            5 -> "五"
            6 -> "六"
            7 -> "日"
            else -> "?"
        }
    }

    val timePeriod: String
        get() {
            val dateTime = LocalDateTime.now()
            val hour = dateTime.hour
            var charTime = ""
            if (hour <= 5) charTime = "凌晨"
            if (hour >= 6 && hour <= 8) charTime = "早上"
            if (hour >= 9 && hour <= 10) charTime = "上午"
            if (hour == 11 || hour == 12) charTime = "中午"
            if (hour >= 13 && hour <= 17) charTime = "下午"
            if (hour >= 18) charTime = "晚上"
            return charTime
        }
}