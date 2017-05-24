package cn.meiqu.baseproject.view.swiperecycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Fatel on 16-2-2.
 */
public class SwipeRecycleView extends RecyclerView {
    public SwipeRecycleView(Context context) {
        super(context);
    }

    public SwipeRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        boolean hasOpen = false;
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (getChildCount() > 0) {
                for (int i = 0; i < getLayoutManager().getChildCount(); i++) {
                    View child = getLayoutManager().getChildAt(i);
                    if (child instanceof SwipeView) {
                        if (((SwipeView) child).isOpen()) {
                            if (((SwipeView) child).isClose(ev.getX(),
                                    ev.getY() - child.getTop())) {
                                ((SwipeView) child).close();
                                hasOpen = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (hasOpen) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }
}
