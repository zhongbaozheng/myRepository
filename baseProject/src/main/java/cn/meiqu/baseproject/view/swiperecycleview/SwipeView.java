package cn.meiqu.baseproject.view.swiperecycleview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SwipeView extends FrameLayout {

    private View bottomView;
    private View topView;
    private int animationDuration = 800;
    private int currentDx = 0;
    private int bottomViewWidth = 0;
    private VelocityTracker mVelocityTracker;// 速度追踪
    private int minVelocityX = 400;// 手滑动的速度，一秒钟400px
    private boolean isOpen = false;
    private int allowX = 8;// 水平滑动超过20px，拦截touch事件
    private int allowY = 20;// 水平滑动超过20px，不拦截touch事件
    private float touchX = 0;
    private float touchY = 0;
    Scroller mScroller = null;
    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnSwipeListener {
        public void onOpen();

        public void onClose();
    }

    public interface OnItemClickListener {
        public void onClick();
    }

    public SwipeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        // TODO Auto-generated constructor stub
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        // TODO Auto-generated constructor stub
    }

    public void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        if (getChildCount() == 2) {
            if (bottomView == null) {
                bottomView = getChildAt(0);
                bottomViewWidth = bottomView.getMeasuredWidth();
                bottomView.setVisibility(View.INVISIBLE);
            }
            if (topView == null) {
                topView = getChildAt(1);
            }
        }
        // System.out.println(bottomViewWidth);
    }

    OnSwipeListener onSwipeListener;

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.onSwipeListener = onSwipeListener;
    }

    public void upDateTopViewMargin(int currentDx2) {
        // System.out.println("upDateTopViewMargin");
        if (currentDx2 > 6) {
//			topView.setBackgroundResource(R.drawable.body_bg);
            bottomView.setVisibility(View.VISIBLE);
        }
        if (currentDx2 <= 0) {
            currentDx2 = 0;
        }
        LayoutParams params = (LayoutParams) topView.getLayoutParams();
        params.rightMargin = (int) currentDx2;
        params.leftMargin = (int) -currentDx2;
        topView.setLayoutParams(params);
        // topView.setTranslationX(-currentDx2);
    }

    public float getUpDateTopViewocation() {
        return currentDx;
    }

    public void open() {
//		topView.setBackgroundResource(R.drawable.body_bg);
        bottomView.setVisibility(View.VISIBLE);
        mScroller.startScroll(currentDx, 0, bottomViewWidth - currentDx, 0,
                animationDuration);
        postInvalidate();
    }

    public void close() {
        mScroller.startScroll(currentDx, 0, -currentDx, 0, animationDuration);
        postInvalidate();
    }

    public void close(int duration) {
        mScroller.startScroll(currentDx, 0, -currentDx, 0, duration);
        postInvalidate();
    }

    public void onOpen() {
        // System.out.println("onOpen");
        isOpen = true;
        if (onSwipeListener != null) {
            onSwipeListener.onOpen();
        }
    }

    public void onClose() {
        // System.out.println("onClose");
        bottomView.setVisibility(View.INVISIBLE);
//		topView.setBackgroundResource(R.drawable.body_bg_translate);
        isOpen = false;
        if (onSwipeListener != null) {
            onSwipeListener.onClose();
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        if (mScroller.computeScrollOffset()) {
            currentDx = mScroller.getCurrX();
            upDateTopViewMargin(currentDx);
            postInvalidate();
            if (currentDx == 0) {
                if (isOpen)
                    onClose();
            } else if (currentDx == bottomViewWidth) {
                if (!isOpen) {
                    onOpen();
                }
            }
        }
        // super.computeScroll();
    }

    public boolean isClose(float x, float y) {
        return x < getWidth() - bottomViewWidth || y < 0 || y > getHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        // System.out.println("dispatchTouchEvent");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            touchX = ev.getX();
            touchY = ev.getY();
            tempX = ev.getX();
            if (isOpen) {
                if (isClose(ev.getX(), ev.getY())) {
                    close();
                    return false;
                }
            }
        } else if (Math.abs((ev.getX() - touchX)) >= allowX
                && Math.abs(ev.getY() - touchY) <= allowY) {
            System.out.println("requestDisallowInterceptTouchEvent");
            requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    private float tempX = 0;
    private MotionEvent downEvent;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        // System.out.println("onTouchEvent");

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downEvent = MotionEvent.obtain(event);
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (Math.abs(event.getX() - tempX) >= 2) {
                if (currentDx < bottomViewWidth) {
                    currentDx += (tempX - event.getX());
                } else {
                    currentDx += (tempX - event.getX()) / 2;
                }
                upDateTopViewMargin(currentDx);
                tempX = event.getX();
                return false;
            }
            createVelocityMotion(event);
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                || event.getAction() == MotionEvent.ACTION_UP) {
            System.out.println("actionUp");
            if (getVelocityX() < -minVelocityX) {
                close();
            } else if (currentDx != 0 && currentDx != bottomViewWidth) {
                if (currentDx <= 0.1 * bottomViewWidth) {
                    close();
                } else {
                    open();
                }
            }
            checkEvent(event);
        }
        return true;
    }

    public void createVelocityMotion(MotionEvent event) {
        if (mVelocityTracker == null)
            mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(event);
    }

    public int getVelocityX() {
        if (mVelocityTracker != null) {
            mVelocityTracker.computeCurrentVelocity(1000);
            int velocityX = (int) mVelocityTracker.getXVelocity();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
            return velocityX;
        } else {
            return 0;
        }
    }

    public void checkEvent(MotionEvent event) {
        // System.out.println("checkEvent");
        if (event.getEventTime() - downEvent.getDownTime() < 500) {
            if (Math.abs(event.getX() - downEvent.getX()) < 5
                    && Math.abs(event.getY() - downEvent.getY()) < 5) {
                downEvent = MotionEvent.obtain(downEvent.getDownTime(),
                        downEvent.getEventTime(), downEvent.getAction(),
                        downEvent.getX() + getLeft(), downEvent.getY()
                                + getTop(), downEvent.getMetaState());
                event = MotionEvent.obtain(event.getDownTime(),
                        event.getEventTime(), event.getAction(), event.getX()
                                + getLeft(), event.getY() + getTop(),
                        event.getMetaState());
//                ((View) getParent()).onTouchEvent(downEvent);
//                ((View) getParent()).onTouchEvent(event);
                if (getOnItemClickListener() != null) {
                    onItemClickListener.onClick();
                }
                downEvent.recycle();
            }
        }
    }
}
