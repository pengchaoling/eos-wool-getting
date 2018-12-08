package com.eos.wool.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    protected static Logger log                  = LoggerFactory.getLogger(DateUtil.class);
    public static final String PATTEM_DATE          = "yyyy-MM-dd";
    public static final String PATTEM_TIME          = "HH:mm:ss";
    public static final String PATTEM_DATE_TIME     = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTEM_TIMESTAMP     = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTEM_DAY_BEGIN     = "yyyy-MM-dd 00:00:00";
    public static final String PATTEM_DAY_END       = "yyyy-MM-dd 23:59:59";
    public static final long   ONEDAY_MILLISECONDS  = 86400000L;
    public static final int    DATE_COMPARE_BEFORE  = -1;
    public static final int    DATE_COMPARE_BETWEEN = 0;
    public static final int    DATE_COMPARE_AFTER   = 1;

    public static Date getDate(String value, String pattem) {
        SimpleDateFormat format = null;
        if (pattem != null)
            format = new SimpleDateFormat(pattem);
        else
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(value);
        } catch (Exception e) {
            log.error("com.xunlei.game.activity.utils.DateUtil.getDate:", e);
        }
        return null;
    }

    public static Date getDate(String value) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.parse(value);
        } catch (Exception e) {
            log.error("com.xunlei.game.activity.utils.DateUtil.getDate:", e);
        }
        return null;
    }

    public static String formatDate(Date date, String pattem) {
        if (date != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(pattem);
                return format.format(date);
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    public static String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(date);
        }
        return null;
    }

    public static int getAnimalSign(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(1);
        return getAnimalSign(year);
    }

    public static int getAnimalSign(int year) {
        year -= 1899;
        year %= 12;
        return Integer.valueOf(year != 0 ? year : 12).shortValue();
    }

    public static int getConstellation(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(1);
        int day  = cal.get(6);

        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd   = Calendar.getInstance();

        calBegin.set(year, 2, 21);
        calEnd.set(year, 3, 20);
        if ((day >= calBegin.get(6)) && (day <= calEnd.get(6))) {
            return 1;
        }

        calBegin.set(year, 3, 21);
        calEnd.set(year, 4, 20);
        if ((day >= calBegin.get(6)) && (day <= calEnd.get(6))) {
            return 2;
        }

        calBegin.set(year, 4, 21);
        calEnd.set(year, 5, 21);
        if ((day >= calBegin.get(6)) && (day <= calEnd.get(6))) {
            return 3;
        }

        calBegin.set(year, 5, 22);
        calEnd.set(year, 6, 22);
        if ((day >= calBegin.get(6)) && (day <= calEnd.get(6))) {
            return 4;
        }

        calBegin.set(year, 6, 23);
        calEnd.set(year, 7, 22);
        if ((day >= calBegin.get(6)) && (day <= calEnd.get(6))) {
            return 5;
        }

        calBegin.set(year, 7, 23);
        calEnd.set(year, 8, 22);
        if ((day >= calBegin.get(6)) && (day <= calEnd.get(6))) {
            return 6;
        }

        calBegin.set(year, 8, 23);
        calEnd.set(year, 9, 22);
        if ((day >= calBegin.get(6)) && (day <= calEnd.get(6))) {
            return 7;
        }

        calBegin.set(year, 9, 23);
        calEnd.set(year, 10, 21);
        if ((day >= calBegin.get(6)) && (day <= calEnd.get(6))) {
            return 8;
        }

        calBegin.set(year, 10, 22);
        calEnd.set(year, 11, 21);
        if ((day >= calBegin.get(6)) && (day <= calEnd.get(6))) {
            return 9;
        }

        calBegin.set(year, 11, 22);
        calEnd.set(year, 0, 19);
        if ((day >= calBegin.get(6)) || (day <= calEnd.get(6))) {
            return 10;
        }

        calBegin.set(year, 0, 20);
        calEnd.set(year, 1, 19);
        if ((day >= calBegin.get(6)) && (day <= calEnd.get(6))) {
            return 11;
        }

        calBegin.set(year, 1, 20);
        calEnd.set(year, 2, 20);
        if ((day >= calBegin.get(6)) && (day <= calEnd.get(6))) {
            return 12;
        }
        return 0;
    }

    public static int computeAge(Date birthday) {
        if (birthday == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        Calendar now = Calendar.getInstance();
        int      age = now.get(1) - cal.get(1);
        cal.add(1, age);
        if ((cal.get(2) > now.get(2)) || ((cal.get(2) == now.get(2)) && (cal.get(5) > now.get(5)))) {
            age--;
        }

        return age;
    }

    public static String now() {
        return formatDate(new Date());
    }

    public static String getToday() {
        return formatDate(new Date(), "yyyy-MM-dd");
    }

    public static String getTomorrow() {
        long time = new Date().getTime() + 86400000L;
        return formatDate(new Date(time), "yyyy-MM-dd");
    }

    public static String getYesterday() {
        long time = new Date().getTime() - 86400000L;
        return formatDate(new Date(time), "yyyy-MM-dd");
    }

    public static String getTodayBegin() {
        return formatDate(new Date(), "yyyy-MM-dd 00:00:00");
    }

    public static String getTodayEnd() {
        return formatDate(new Date(), "yyyy-MM-dd 23:59:59");
    }

    public static String getDayBegin(String dateStr) {
        if ((RegUtil.isEmptyString(dateStr)) || (dateStr.length() < 10))
            return null;
        return dateStr.substring(0, 10) + " 00:00:00";
    }

    public static String getDayBegin(Date date) {
        if (date == null)
            return null;
        return formatDate(date, "yyyy-MM-dd 00:00:00");
    }

    public static String getDayEnd(String dateStr) {
        if ((RegUtil.isEmptyString(dateStr)) || (dateStr.length() < 10))
            return null;
        return dateStr.substring(0, 10) + " 23:59:59";
    }

    public static String getDayEnd(Date date) {
        if (date == null)
            return null;
        return formatDate(date, "yyyy-MM-dd 23:59:59");
    }

    public static int compareTimeByNow(Date begin, Date end) {
        long now = new Date().getTime();
        if (now < begin.getTime())
            return -1;
        if (now > end.getTime())
            return 1;

        return 0;
    }

    public static int compareTimeByNow(String begin, String end) {
        long now = new Date().getTime();
        if (now < getDate(begin).getTime())
            return -1;
        if (now > getDate(end).getTime())
            return 1;

        return 0;
    }

    public static boolean isBetweenTime(Date begin, Date end) {
        return compareTimeByNow(begin, end) == 0;
    }

    public static boolean isBetweenTime(String begin, String end) {
        return compareTimeByNow(begin, end) == 0;
    }

    public static long calTimeByNow(Date date) {
        return new Date().getTime() - date.getTime();
    }

    public static long calTimeByNow(String date) {
        return Math.abs(new Date().getTime() - getDate(date).getTime());
    }

    public static Date addDays(int days) {
        return new Date(new Date().getTime() + days * 86400000L);
    }

    public static String addDaysForStr(int days) {
        return formatDate(addDays(days));
    }

    public static Date addMilliseconds(int milliseconds) {
        return new Date(new Date().getTime() + milliseconds);
    }

    public static String addMillisecondsForStr(int milliseconds) {
        return formatDate(addMilliseconds(milliseconds));
    }

    public static Date addSeconds(int seconds) {
        return new Date(new Date().getTime() + 1000 * seconds);
    }

    public static String addSecondsForStr(int seconds) {
        return formatDate(addSeconds(seconds));
    }

    public static int getWeek() {
        return getWeek(new Date());
    }

    public static int getWeek(String time) {
        Date t = null;
        if ((time != null) && (time.length() >= 10))
            t = getDate(time.substring(0, 10), "yyyy-MM-dd");
        else {
            return -1;
        }
        return getWeek(t);
    }

    public static int getWeek(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(7) - 1;
    }

    /**
     * ����ʱ��֮�������������
     *
     * @param one ʱ����� 1��
     * @param two ʱ����� 2��
     * @return �������
     */
    public static long getDistanceDays(Date one, Date two) {

        long days  = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = diff / (1000 * 60 * 60 * 24);
        return days;
    }

    public static long getDistanceHours(Date one, Date two) {

        long days  = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = diff / (1000 * 60 * 60);
        return days;
    }

    public static long getDistanceMin(Date one, Date two, int min) {

        long days  = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = diff / (1000 * 60 * min);
        return days;
    }

    public static String getAndroidTime(Date date) {
        String timeStr = "";
        try {
            Calendar calendar = Calendar.getInstance();

            Calendar today = Calendar.getInstance(); // 今天
            today.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            today.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            today.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.clear(Calendar.MILLISECOND);

            Calendar yesterday = Calendar.getInstance(); // 昨天
            yesterday.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            yesterday.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            yesterday.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
            yesterday.set(Calendar.HOUR_OF_DAY, 0);
            yesterday.set(Calendar.MINUTE, 0);
            yesterday.set(Calendar.SECOND, 0);
            yesterday.clear(Calendar.MILLISECOND);

            Calendar beforeYesterday = Calendar.getInstance(); // 前天
            beforeYesterday.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            beforeYesterday.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            beforeYesterday.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 2);
            beforeYesterday.set(Calendar.HOUR_OF_DAY, 0);
            beforeYesterday.set(Calendar.MINUTE, 0);
            beforeYesterday.set(Calendar.SECOND, 0);
            beforeYesterday.clear(Calendar.MILLISECOND);

            int      day_of_week = 0;
            Calendar monday      = Calendar.getInstance(); // 周一
            day_of_week = monday.get(Calendar.DAY_OF_WEEK) - 1;
            if (day_of_week == 0) {
                day_of_week = 7;
            }
            monday.add(Calendar.DATE, -day_of_week + 1);
            monday.set(Calendar.HOUR_OF_DAY, 0);
            monday.set(Calendar.MINUTE, 0);
            monday.set(Calendar.SECOND, 0);
            monday.clear(Calendar.MILLISECOND);

            Calendar sunday = Calendar.getInstance(); // 周日
            sunday.add(Calendar.DATE, -day_of_week + 7);
            sunday.set(Calendar.HOUR_OF_DAY, 0);
            sunday.set(Calendar.MINUTE, 0);
            sunday.set(Calendar.SECOND, 0);
            sunday.clear(Calendar.MILLISECOND);

            Calendar year = Calendar.getInstance(); // 今年的1月1日
            year.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            year.set(Calendar.MONTH, 0);
            year.set(Calendar.DAY_OF_MONTH, 1);
            year.set(Calendar.HOUR_OF_DAY, 0);
            year.set(Calendar.MINUTE, 0);
            year.set(Calendar.SECOND, 0);
            year.clear(Calendar.MILLISECOND);

            String hourZoneCH = getHourZoneCH(date);
            if (calendar.equals(today) || calendar.after(today)) {
                SimpleDateFormat todayFormat = new SimpleDateFormat("HH:mm");
                timeStr = hourZoneCH + " " + todayFormat.format(date);
            } else if (calendar.after(yesterday)) {
                timeStr = "昨天" + hourZoneCH;
            } else if (calendar.after(beforeYesterday)) {
                timeStr = "前天" + hourZoneCH;
            } else if (calendar.after(monday) && calendar.before(sunday)) {
                int    dayOfWeek   = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                String dayOfWeekCH = "";
                if (dayOfWeek == 1) {
                    dayOfWeekCH = "一";
                } else if (dayOfWeek == 2) {
                    dayOfWeekCH = "二";
                } else if (dayOfWeek == 3) {
                    dayOfWeekCH = "三";
                } else if (dayOfWeek == 4) {
                    dayOfWeekCH = "四";
                } else if (dayOfWeek == 5) {
                    dayOfWeekCH = "五";
                } else if (dayOfWeek == 6) {
                    dayOfWeekCH = "六";
                } else {
                    dayOfWeekCH = "日";
                }
                timeStr = "周" + dayOfWeekCH + hourZoneCH;
            } else if (calendar.after(year)) {
                SimpleDateFormat formatYear = new SimpleDateFormat("M月d日 HH:mm");
                timeStr = formatYear.format(date);
            } else {
                SimpleDateFormat formatNotYear = new SimpleDateFormat("yyyy年M月d日 HH:mm");
                timeStr = formatNotYear.format(date);
            }

        } catch (Exception e) {
            timeStr = "时间格式不正确";
            e.printStackTrace();
        }
        return timeStr;
    }

    public static String getAndroidTimeRuleOne(Date time) {
        String timeStr = "";
        try {
            Calendar calendar = Calendar.getInstance();

            Calendar today = Calendar.getInstance(); // 今天
            today.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            today.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            today.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.clear(Calendar.MILLISECOND);

            Calendar yesterday = Calendar.getInstance(); // 昨天
            yesterday.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            yesterday.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            yesterday.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
            yesterday.set(Calendar.HOUR_OF_DAY, 0);
            yesterday.set(Calendar.MINUTE, 0);
            yesterday.set(Calendar.SECOND, 0);
            yesterday.clear(Calendar.MILLISECOND);

            Calendar beforeYesterday = Calendar.getInstance(); // 前天
            beforeYesterday.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            beforeYesterday.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            beforeYesterday.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 2);
            beforeYesterday.set(Calendar.HOUR_OF_DAY, 0);
            beforeYesterday.set(Calendar.MINUTE, 0);
            beforeYesterday.set(Calendar.SECOND, 0);
            beforeYesterday.clear(Calendar.MILLISECOND);

            int      day_of_week = 0;
            Calendar monday      = Calendar.getInstance();  //周一
            day_of_week = monday.get(Calendar.DAY_OF_WEEK) - 1;
            if (day_of_week == 0) {
                day_of_week = 7;
            }
            monday.add(Calendar.DATE, -day_of_week + 1);
            monday.set(Calendar.HOUR_OF_DAY, 0);
            monday.set(Calendar.MINUTE, 0);
            monday.set(Calendar.SECOND, 0);
            monday.clear(Calendar.MILLISECOND);

            Calendar sunday = Calendar.getInstance();  //周日
            sunday.add(Calendar.DATE, -day_of_week + 7);
            sunday.set(Calendar.HOUR_OF_DAY, 0);
            sunday.set(Calendar.MINUTE, 0);
            sunday.set(Calendar.SECOND, 0);
            sunday.clear(Calendar.MILLISECOND);

            Calendar year = Calendar.getInstance();  //今年的1月1日
            year.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            year.set(Calendar.MONTH, 0);
            year.set(Calendar.DAY_OF_MONTH, 1);
            year.set(Calendar.HOUR_OF_DAY, 0);
            year.set(Calendar.MINUTE, 0);
            year.set(Calendar.SECOND, 0);
            year.clear(Calendar.MILLISECOND);

            Date date = time;
            calendar.setTime(date);
            String hourZoneCH = getHourZoneCH(date);
            String hourStr    = "";
            String minuteStr  = "";
            int    hour       = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 12) {
                hour = hour - 12;
            }
            if (hour < 10) {
                hourStr = "0" + hour;
            } else {
                hourStr = hour + "";
            }
            int minute = calendar.get(Calendar.MINUTE);
            if (minute < 10) {
                minuteStr = "0" + minute;
            } else {
                minuteStr = minute + "";
            }
            if (calendar.equals(today) || calendar.after(today)) {
                SimpleDateFormat todayFormat = new SimpleDateFormat("HH:mm");
                timeStr = hourZoneCH + " " + todayFormat.format(date);
            } else if (calendar.after(yesterday)) {
                timeStr = "昨天" + hourZoneCH + " " + hourStr + ":" + minuteStr;
            } else if (calendar.after(beforeYesterday)) {
                timeStr = "前天" + hourZoneCH + " " + hourStr + ":" + minuteStr;
            } else if (calendar.after(monday) && calendar.before(sunday)) {
                int    dayOfWeek   = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                String dayOfWeekCH = "";
                if (dayOfWeek == 1) {
                    dayOfWeekCH = "一";
                } else if (dayOfWeek == 2) {
                    dayOfWeekCH = "二";
                } else if (dayOfWeek == 3) {
                    dayOfWeekCH = "三";
                } else if (dayOfWeek == 4) {
                    dayOfWeekCH = "四";
                } else if (dayOfWeek == 5) {
                    dayOfWeekCH = "五";
                } else if (dayOfWeek == 6) {
                    dayOfWeekCH = "六";
                } else {
                    dayOfWeekCH = "日";
                }
                timeStr = "周" + dayOfWeekCH + hourZoneCH + " " + hour + ":" + calendar.get(Calendar.MINUTE);
            } else if (calendar.after(year)) {
                SimpleDateFormat formatYear = new SimpleDateFormat("M月d日 HH:mm");
                timeStr = formatYear.format(date);
            } else {
                SimpleDateFormat formatNotYear = new SimpleDateFormat("yyyy年M月d日");
                timeStr = formatNotYear.format(date);
            }

        } catch (Exception e) {
            timeStr = "时间格式不正确";
            e.printStackTrace();
        }
        return timeStr;
    }

    public static String getHourZoneCH(Date date) {
        if (date == null) {
            return "时间为空";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Calendar oneHour = Calendar.getInstance();
        oneHour.setTime(date);
        oneHour.set(Calendar.HOUR_OF_DAY, 1);
        oneHour.set(Calendar.MINUTE, 0);
        oneHour.set(Calendar.SECOND, 0);

        Calendar sixHour = Calendar.getInstance();
        sixHour.setTime(date);
        sixHour.set(Calendar.HOUR_OF_DAY, 6);
        sixHour.set(Calendar.MINUTE, 0);
        sixHour.set(Calendar.SECOND, 0);

        Calendar elevenHour = Calendar.getInstance();
        elevenHour.setTime(date);
        elevenHour.set(Calendar.HOUR_OF_DAY, 11);
        elevenHour.set(Calendar.MINUTE, 0);
        elevenHour.set(Calendar.SECOND, 0);

        Calendar thirteenHour = Calendar.getInstance();
        thirteenHour.setTime(date);
        thirteenHour.set(Calendar.HOUR_OF_DAY, 13);
        thirteenHour.set(Calendar.MINUTE, 0);
        thirteenHour.set(Calendar.SECOND, 0);

        Calendar eighteenHour = Calendar.getInstance();
        eighteenHour.setTime(date);
        eighteenHour.set(Calendar.HOUR_OF_DAY, 18);
        eighteenHour.set(Calendar.MINUTE, 0);
        eighteenHour.set(Calendar.SECOND, 0);

        Calendar nineteenHour = Calendar.getInstance();
        nineteenHour.setTime(date);
        nineteenHour.set(Calendar.HOUR_OF_DAY, 19);
        nineteenHour.set(Calendar.MINUTE, 0);
        nineteenHour.set(Calendar.SECOND, 0);

        String hour = "傍晚";
        if (calendar.before(oneHour)) {
            hour = "深夜";
        } else if ((calendar.before(sixHour) && calendar.after(oneHour)) || calendar.equals(oneHour)) {
            hour = "凌晨";
        } else if ((calendar.before(elevenHour) && calendar.after(sixHour)) || calendar.equals(sixHour)) {
            hour = "上午";
        } else if (calendar.before(thirteenHour) && calendar.after(elevenHour) || calendar.equals(elevenHour)) {
            hour = "中午";
        } else if (calendar.before(eighteenHour) && calendar.after(thirteenHour) || calendar.equals(thirteenHour)) {
            hour = "下午";
        } else if (calendar.before(nineteenHour) && calendar.after(eighteenHour) || calendar.equals(eighteenHour)) {
            hour = "傍晚";
        } else {
            hour = "晚上";
        }
        return hour;
    }

    public static void main(String[] args) {
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-28 15:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-28 10:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-26 15:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-26 10:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-25 15:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-25 11:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-24 15:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-24 22:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-27 15:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-27 15:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-27 15:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-27 15:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-27 15:17:10")));
        System.out.println(getAndroidTimeRuleOne(getDate("2016-09-27 15:17:10")));

    }
}
