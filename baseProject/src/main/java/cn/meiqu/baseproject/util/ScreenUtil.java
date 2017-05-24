package cn.meiqu.baseproject.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ScreenUtil {
    /**
     * 获取状态栏高度
     */
    public static int statusBarHeight(Context context) {
        Rect frame = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    public static int ScreenWidth(Context context) {
        return ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int ScreenHeight(Context context) {
        return ((Activity) context).getWindowManager().getDefaultDisplay().getHeight() - statusBarHeight(context);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void hideKeyBroad(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            ((InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    public static void showKeyBroad(EditText editText) {
        editText.setFocusable(true);

        editText.setFocusableInTouchMode(true);

        editText.requestFocus();
        InputMethodManager inputManager =

                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.showSoftInput(editText, 0);
    }

    private static long exitTime = 0;

    public static boolean onKeyDown(Activity activity, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.show(activity, "再按一次退出");
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                activity.finish();
                System.exit(0);
            }
        }

        return false;

    }

    public static void fullScreenCompat(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
    }
}