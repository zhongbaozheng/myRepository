package cn.meiqu.baseproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import cn.meiqu.baseproject.R;

/**
 * Created by Fatel on 16-2-25.
 */
public class PercentImageView extends ImageView {
    int control = 0;//width
    float precent = 1f;//比例

    public PercentImageView(Context context) {
        super(context);
        init(context, null);
    }

    public PercentImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PercentImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PercentImageView);
        control = typedArray.getInt(R.styleable.PercentImageView_control, 0);
        precent = typedArray.getFloat(R.styleable.PercentImageView_precent, 1.0f);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (control == 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (MeasureSpec.getSize(widthMeasureSpec) / precent), MeasureSpec.EXACTLY);
        } else {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (MeasureSpec.getSize(heightMeasureSpec) / precent), MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
