package cn.meiqu.baseproject.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.meiqu.baseproject.R;


public class ToastUtil {
    static String netWorkFailure = "网络出错了";
    static Toast toast;
    static TextView tv;
    static TextView tv2;

    public static void show(Context mContext, String text) {
        if (toast == null) {
            toast = new Toast(mContext);
            toast.setDuration(Toast.LENGTH_LONG);
            ViewGroup v = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.toast, null);
            tv = (TextView) v.getChildAt(0);
            tv2 = (TextView) v.getChildAt(1);
            toast.setView(v);
        }
        tv.setText(text);
        tv2.setText(text);
        toast.show();
    }

    public static void showNetWorkFailure(Context mContext) {
        show(mContext, netWorkFailure);
    }

    public static void log(String text) {
        System.out.println(text);
    }
}
