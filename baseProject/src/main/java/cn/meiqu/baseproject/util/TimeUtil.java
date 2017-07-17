package cn.meiqu.baseproject.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Fatel on 15-6-19.
 */
public class TimeUtil {
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat MIN_DATE_FORMAT = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat Month_FORMAT_DATE = new SimpleDateFormat("MM-dd");
    public static final SimpleDateFormat DEVICE_DATE = new SimpleDateFormat("HH:mm\n MM月dd日 ");

    public static String getChatTime(long time) {
        String timeFormat = "";
        long currentTime = System.currentTimeMillis() / 1000;
        time = time / 1000;
        long dTime = currentTime - time;
        if (dTime < 60) {
            timeFormat = "1分钟前";
        } else if (dTime < 60 * 60) {
            timeFormat = (dTime) / 60 + "分钟前";
        } else if (dTime < 24 * 60 * 60) {
            timeFormat = (dTime) / 60 / 60 + "小时前";
        } else {
            //timeFormat = (dTime) / 24 / 60 / 60 + "天前";
            timeFormat = getCurrentTimeInString(Month_FORMAT_DATE);
        }
        return timeFormat;
    }


    public static long getTimeMills(String dateFormat){

        long timeMins = 0;

        try {
           timeMins = DEFAULT_DATE_FORMAT.parse(dateFormat).getTime();
        }catch (Exception e){
            e.printStackTrace();
        }

        return timeMins;
    }


    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }
}
