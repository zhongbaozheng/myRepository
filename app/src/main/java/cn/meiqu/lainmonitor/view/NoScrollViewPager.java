package cn.meiqu.lainmonitor.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/5/31.
 *
 * ViewPager非常好用，但有时候需要在ViewPager的里面再嵌入ViewPager，那么就有冲突了，简单粗暴的方法就是直接把一个ViewPager禁止滑动。

 注意：禁止滑动的同时不能禁止 setCurrentItem 方法。

 实现思路：重写ViewPager，覆盖 onTouchEvent 和 onInterceptTouchEvent 方法，使其返回false，这样就等于禁止了ViewPager上的滑动事件。
 */

public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
}

