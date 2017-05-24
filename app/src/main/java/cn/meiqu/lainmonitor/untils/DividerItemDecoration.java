package cn.meiqu.lainmonitor.untils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.meiqu.lainmonitor.adapter.RecycleAlertType1Adapter;

/**
 * Created by Administrator on 2017/5/2.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;

    private Context mContext;

    /**
     * Default divider will be used
     */
    public DividerItemDecoration(Context context) {
        mContext = context;
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        mDivider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();
    }

    /**
     * Custom divider will be used
     */
    public DividerItemDecoration(Context context, int resId) {
        mDivider = ContextCompat.getDrawable(context, resId);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int dividerWidth = DensityUtils.dp2px(mContext, 0.8f);
            mDivider.setBounds(child.getLeft(),
                        child.getBottom(),
                        child.getRight(),
                        child.getBottom() + dividerWidth);
            mDivider.draw(c);


//            //右分割线
//            mDivider.setBounds(child.getRight(),
//                    child.getTop(),
//                    child.getRight()+ dividerWidth,
//                    child.getBottom());
//            mDivider.draw(c);
        }
    }
}
