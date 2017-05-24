package cn.meiqu.baseproject.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Fatel on 15-7-6.
 */
public class LinLayoutManager extends android.support.v7.widget.LinearLayoutManager {


    public LinLayoutManager(Context context) {
        super(context);
    }

    public LinLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
                int height = measuredHeight * (getItemCount());
                if (height >= View.MeasureSpec.getSize(heightSpec)) {
                    super.onMeasure(recycler, state, widthSpec, heightSpec);
                } else {
                    setMeasuredDimension(measuredWidth, height);
                }
            }
        } else {
            super.onMeasure(recycler, state, widthSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST));
        }
    }
}
