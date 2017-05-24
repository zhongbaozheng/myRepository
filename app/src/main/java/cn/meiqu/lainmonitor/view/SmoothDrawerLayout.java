package cn.meiqu.lainmonitor.view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Fatel on 16-5-10.
 */
public class SmoothDrawerLayout extends DrawerLayout {


    public SmoothDrawerLayout(Context context) {
        super(context);
    }

    public SmoothDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmoothDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            super.onInterceptTouchEvent(ev);
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }

    }
}
