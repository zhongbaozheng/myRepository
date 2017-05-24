package cn.meiqu.baseproject.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by Fatel on 15-7-9.
 */
public class AutoViewPager extends ViewPager {
    Handler mHandler;
    AutoRunable autoRunable;
    private int duration = 8 * 1000;
    int currentIndex = 0;
    boolean isStart = false;

    public AutoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mHandler = new Handler();
        autoRunable = new AutoRunable();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void startAutoScroll() {
        if (!isStart) {
            mHandler.removeCallbacks(autoRunable);
            mHandler.postDelayed(autoRunable, duration);
        }
        isStart = true;
    }

    public void stopAutoScroll() {
        isStart = false;
        mHandler.removeCallbacks(autoRunable);
    }

    class AutoRunable implements Runnable {

        @Override
        public void run() {
            if (getAdapter().getCount() != 0) {
                currentIndex = getCurrentItem() + 1;
                setCurrentItem(currentIndex, true);
            }
            mHandler.postDelayed(this, duration);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mHandler.removeCallbacks(autoRunable);
        mHandler.postDelayed(autoRunable, duration);
        return super.onTouchEvent(ev);
    }

}
