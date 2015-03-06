package beakers.system.utils

import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat

public class DateUtils {

    static public Date now() {
        return new GregorianCalendar().getTime()
    }

    static public Date clearTime(Date date) {
        GregorianCalendar calendar = new GregorianCalendar()
        calendar.setTime(date)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.getTime()
    }

    static months = ["января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"]

    static public String format(Date date, String format = "dd MMMM yyyy 'г.'") {
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"))
        dfs.setMonths(months as String[])
        SimpleDateFormat sdf = new SimpleDateFormat(format, dfs)
        return sdf.format(date)
    }

    static public String formatToJson(Date date) {
        def formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        return formatter.format(date)
    }

    static public String formatDateTime(Date date) {
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"))
        dfs.setMonths(months as String[])
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd MMMM yyyy 'г.'", dfs)
        return sdf.format(date)
    }

    static public String formatDateShort(Date date) {
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"))
        dfs.setMonths(months as String[])
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy", dfs)
        return sdf.format(date)
    }

    static public String formatForZone(Date date, String zone) {
        DateFormat formatter = new SimpleDateFormat("HH:mm dd.MM.yyyy zzz")
        TimeZone central = TimeZone.getTimeZone(zone)
        formatter.setTimeZone(central)
        return formatter.format(date)
    }

    static public Date parseDate(String date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy")
        return formatter.parse(date)
    }

    static public Date parseDateTime(String date, String time) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy")
        return formatter.parse(time + " " + date)
    }

    static public Date parseTime(String time) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss")
        return formatter.parse(time)
    }

    static public Date parseFromJson(String date) {
        def formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        return formatter.parse(date)
    }

    public static boolean timeIsBefore(Date d1, Date d2) {
        DateFormat f = new SimpleDateFormat("HH:mm:ss.SSS")
        return f.format(d1).compareTo(f.format(d2)) < 0
    }

    static public Integer fullDaysBetween(Date from, Date to) {
        return (to - from) - (timeIsBefore(to, from) ? 1 : 0)
    }

    static public String formatMonthPeriod(Integer period) {
        Integer years = (Integer)(period / 12)
        Integer months = period % 12

        def yearNames = ["год", "года", "лет"]
        def monthNames = ["месяц", "месяца", "месяцев"]

        def getType = { i ->
            def r = i % 10
            if (r == 1) {
                return 0
            } else if (1 < r && r < 5) {
                return 1
            } else {
                return 2
            }
        }
        def res = ""
        if (years > 0) {
            res = "${years} ${yearNames[getType(years)]}"

        }
        if (months > 0 || (months == 0 && years == 0)) {
            if (years > 0) {
                res += " "
            }
            res += "${months} ${monthNames[getType(months)]}"
        }
        return res
    }

    static public String formatDayPeriod(Integer period) {
        if (!period) {
            return ""
        }
        if (period % 10 == 1 && period != 11) {
            return "$period день"
        }
        if (period % 10 in 2..4) {
            return "$period дня"
        }
        return "$period дней"
    }

    static public daysInMonth(Date date) {
        Calendar cal = new GregorianCalendar()
        cal.setTime(date)
        int dayInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        return dayInMonth
    }

    static public daysInMonth() {
        return daysInMonth(now())
    }
}
