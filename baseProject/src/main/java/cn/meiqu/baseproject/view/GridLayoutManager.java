package cn.meiqu.baseproject.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.meiqu.baseproject.util.ScreenUtil;

/**
 * Created by Fatel on 15-7-6.
 */
public class GridLayoutManager extends android.support.v7.widget.GridLayoutManager {
    int padding = 24;

    public GridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public GridLayoutManager(Context context, int spanCount, int padding) {
        super(context, spanCount);
        this.padding = padding;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
//        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        if (getItemCount() > 0) {
            View view = recycler.getViewForPosition(0);
            if (view != null) {
                int measuredHeight;
                measureChild(view, widthSpec, heightSpec);
                measuredHeight = view.getMeasuredHeight();
                int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                int height = measuredHeight * (getItemCount() / getSpanCount());
                height = height + (getItemCount() % getSpanCount() > 0 ? measuredHeight : 0) + ScreenUtil.dip2px(view.getContext(), padding);
                setMeasuredDimension(measuredWidth, height);
            }
        } else {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
        }
    }
}
