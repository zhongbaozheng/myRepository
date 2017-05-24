package cn.meiqu.baseproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.meiqu.baseproject.R;
import cn.meiqu.baseproject.util.ImageLoadHelper;
import cn.meiqu.baseproject.view.photoview.PhotoViewAttacher;


public class PagerBigImageAdapter extends PagerAdapter {
    Context mContext;
    ArrayList<String> imageBigs;
    ArrayList<String> imageSmalls;

    ViewGroup imageViews[];
    boolean isRefresh = false;

    public PagerBigImageAdapter(Context mContext, ArrayList<String> imageBigs,
                                ArrayList<String> imageSmalls) {
        this.mContext = mContext;
        this.imageBigs = imageBigs;
        this.imageSmalls = imageSmalls;
        initView();
    }

    public void initView() {
        imageViews = new ViewGroup[imageBigs.size()];
        for (int i = 0; i < imageViews.length; i++) {
            ViewGroup vGroup = (ViewGroup) LayoutInflater.from(mContext)
                    .inflate(R.layout.imagebig, null);
            imageViews[i] = vGroup;
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        // System.out.println("PagerImageAdapter--getCount-"+images.size());
        return imageBigs.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        System.out.println("instantiateItem---" + position);
        ViewGroup vGroup = imageViews[position];
        vGroup.setId(position);
        if (imageSmalls != null && imageSmalls.size() != 0) {
            ImageLoadHelper.displayImage(imageSmalls.get(position),
                    (ImageView) vGroup.getChildAt(0));
        } else {
            vGroup.getChildAt(0).setVisibility(View.INVISIBLE);
        }
        ImageLoadHelper.displayImage(imageBigs.get(position),
                (ImageView) vGroup.getChildAt(2),
                new MyBigImageLoadListener(vGroup));
        ((ViewPager) container).addView(vGroup);
        return vGroup;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        System.out.println("destroyItem----" + position);
        ((ViewPager) container).removeView((View) object);
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        if (isRefresh) {
            return POSITION_NONE;
        } else {
            return super.getItemPosition(object);
        }
    }

    public ViewGroup getCurrentView(int position) {
        return imageViews[position];
    }

    class MyBigImageLoadListener implements ImageLoadHelper.OnCompleteListener {
        ViewGroup vGroup;

        public MyBigImageLoadListener(ViewGroup vGroup) {
            this.vGroup = vGroup;
        }

        @Override
        public void onLoadingComplete(View view, Bitmap loadedImage) {
            vGroup.getChildAt(0).setVisibility(View.GONE);
            vGroup.getChildAt(1).setVisibility(View.GONE);
            view.setTag("loadFinish");
//            view.startAnimation(AnimationUtils.loadAnimation(mContext,
//                    R.anim.start_fade_in));
            PhotoViewAttacher mAttacher = new PhotoViewAttacher(
                    (ImageView) view);
            mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    ((Activity) mContext).finish();
                }
            });
            //  Log.e("oo", "loadedImage.getWidth=" + loadedImage.getWidth());

        }
    }
}
