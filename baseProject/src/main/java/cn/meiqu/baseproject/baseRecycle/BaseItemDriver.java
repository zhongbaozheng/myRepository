package cn.meiqu.baseproject.baseRecycle;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.meiqu.baseproject.util.ScreenUtil;


/**
 * Created by Fatel on 15-11-5.
 */
public class BaseItemDriver extends RecyclerView.ItemDecoration {
    GradientDrawable colorDrawable;
    int margin = 10;

    public BaseItemDriver(int color) {
        colorDrawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{color, color});
    }

    public BaseItemDriver(int color, int margin) {
        this.margin = margin;
        colorDrawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{color, color});
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            colorDrawable.draw(c);
//            int top = parent.getPaddingTop();
//            int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            colorDrawable.setBounds(child.getLeft() + ScreenUtil.dip2px(child.getContext(), margin), child.getBottom(), child.getRight() - ScreenUtil.dip2px(child.getContext(), margin), child.getBottom() + 1);
            colorDrawable.draw(c);

        }
//            c.drawLine(0, 0, parent.getWidth(), 2, new Paint());
    }


    //        @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            int position = parent.getChildAdapterPosition(view);
        outRect.set(0, 0, 0, 0);
    }

}
