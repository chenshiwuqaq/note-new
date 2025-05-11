package org.com.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DateUtils {
    // Date 转 LocalDateTime（使用系统默认时区）
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    // LocalDate 转 Date（当天零点）
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    // 获取当天时间范围 [当天00:00, 次日00:00)
    public static List<LocalDateTime> getDayRange(LocalDateTime date) {
        LocalDateTime startOfDay = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = date.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
        return Arrays.asList(startOfDay, endOfDay);
    }
}
