package calebzhou.rdi.microservice.utils

import java.sql.Timestamp
import java.time.LocalDateTime

object TimeUtils {
    val now: Timestamp
        get() = Timestamp(System.currentTimeMillis())
    val timeChineseString: String
        get() {
            val dateTime = LocalDateTime.now()
            val hour = dateTime.hour
            var charTime = ""
            if (hour >= 0 && hour <= 5) charTime = "凌晨"
            if (hour >= 6 && hour <= 8) charTime = "早上"
            if (hour >= 9 && hour <= 10) charTime = "上午"
            if (hour == 11 || hour == 12) charTime = "中午"
            if (hour >= 13 && hour <= 17) charTime = "下午"
            if (hour >= 18 && hour <= 23) charTime = "晚上"
            return charTime
        }

    fun secondsToMinute(seconds: Int, split1: String?, split2: String?): String {
        val min = seconds / 60
        val sec = seconds % min
        return String.format("%s%s%s%s", min, split1, sec, split2)
    }
}