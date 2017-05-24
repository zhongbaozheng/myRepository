package cn.meiqu.lainmonitor.untils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DensityUtils {

    /**
     * dp转换成px
     * @param dp
     * @param context
     * @return
     */
    public static int dp2px(Context context, float dp){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

    /**
     * px转换成dp
     * @param px
     * @param context
     * @return
     */
    public static int px2dp(Context context, float px){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) dp;
    }
}
