package com.sysoiev.developers_db.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtil {
    public static Date getCurrentDate() {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }
}
