package cn.meiqu.baseproject.util;

import android.util.Log;


/**
 * Created by Fatel on 15-4-3.
 */
public class LogUtil {
    // private  static final boolean DEBUG = true;

    public static String tag = "fatel";

    public static void log(String text) {
        log(tag, text);
    }

    public static void log(String tag, String text) {
        Log.e(tag, text);
    }
}
