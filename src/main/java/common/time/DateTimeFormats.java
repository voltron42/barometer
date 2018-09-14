package common.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public enum DateTimeFormats {
    DATE_TIME_STAMP_EXPLICIT("yyyyMMddHHmmss"),
    DATE_TIME_STAMP("yyyyMMddHHmm"),
    DATE_HOUR_STAMP("yyyyMMddHH"),
    DATE_STAMP("yyyyMMdd"),
    TIME_STAMP("HHmm"),
    DATE_ROBUST("yyyy-MM-dd"),
    DATE_TIME_ROBUST("yyyy-MM-dd HH:mm:ss.SSS"),
    ;

    private final SimpleDateFormat format;

    DateTimeFormats(String format) {
        this.format = new SimpleDateFormat(format);
    }

    public String format(Date date) {
        return format.format(date);
    }

    public String format(Calendar calendar) {
        return format.format(new Date(calendar.getTimeInMillis()));
    }

    public Date parse(String datetime) throws ParseException {
        return format.parse(datetime);
    }
}
