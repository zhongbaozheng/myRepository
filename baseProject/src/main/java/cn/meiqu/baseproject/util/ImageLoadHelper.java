package cn.meiqu.baseproject.util;//package cn.meiqu.mich.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//
//
///**
// * @author gccd
// *         1,加载圆角,圆形
// *         2,加载带动画
// *         3,加载完后返回缓存路径
// */
public class ImageLoadHelper {
    private static DisplayImageOptions baseDisplayImageOptions;
    private static DisplayImageOptions avatarImageOptions;
    private static DisplayImageOptions roundImageOptions;
    private static ImageLoader imageLoader;
    private static ImageLoadingListener animateFirstListener;

    public static void displayRoundAvatar(String url, ImageView imageView, int rpx) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(rpx)).build();
        getImageLoader().displayImage(url, imageView, options);
    }

    public static void displayAvatar(String url, ImageView imageView) {
        getImageLoader().displayImage(url, imageView, getAvatarOptions(), getAnimateFirstListener());
    }

    public static void displayRoundAvatar(String url, ImageView imageView) {
        getImageLoader().displayImage(url, imageView, getRoundImageOptions(), getAnimateFirstListener());
    }


    public static void displayImage(String url, ImageView imageView) {
        if (!StringUtil.isEmpty(url)) {
            if (imageView.getTag(imageView.getId()) == null || !imageView.getTag(imageView.getId()).equals(url)) {
                imageView.setTag(imageView.getId(), url);
                getImageLoader().displayImage(url, imageView, getBaseDisplayImageOptions(), getAnimateFirstListener());
            }
        }
    }

    public static void displayImage(String url, ImageSize imageSize, ImageLoadingListener imageLoadingListener) {
        getImageLoader().loadImage(url, imageSize, getCacheDisplayImageOptions(), imageLoadingListener);
    }

    public static void displayImage(String url, ImageView imageView, OnCompleteListener onCompleteListener) {
        getImageLoader().displayImage(url, imageView, getBaseDisplayImageOptions(), new AnimateFirstDisplayListener(onCompleteListener));
    }

    public static DisplayImageOptions getBaseDisplayImageOptions() {
        if (baseDisplayImageOptions == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            baseDisplayImageOptions = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.bg_imgloading)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .decodingOptions(options)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(new SimpleBitmapDisplayer()).build();
        }
        return baseDisplayImageOptions;
    }

    static DisplayImageOptions cacheOption;

    public static DisplayImageOptions getCacheDisplayImageOptions() {
        if (cacheOption == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            cacheOption = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .decodingOptions(options)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(new SimpleBitmapDisplayer()).build();
        }
        return cacheOption;
    }

    public static DisplayImageOptions getRoundImageOptions() {
        if (roundImageOptions == null) {
            roundImageOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .displayer(new RoundDisplayer())
                    .build();
        }
        return roundImageOptions;
    }

    public static DisplayImageOptions getAvatarOptions() {
        if (avatarImageOptions == null) {
            avatarImageOptions = new DisplayImageOptions.Builder()
                    //.showImageOnFail(R.drawable.def_face)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .displayer(new SimpleBitmapDisplayer())
                    .build();
        }
        return avatarImageOptions;
    }

    public static ImageLoadingListener getAnimateFirstListener() {
        if (animateFirstListener == null) {
            animateFirstListener = new AnimateFirstDisplayListener();
        }
        return animateFirstListener;
    }

    /**
     * @param @return
     * @return ImageLoader
     * @throws
     * @Title: getImageLoader
     * @Description: 获取imageloader 实例
     */
    public static ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = ImageLoader.getInstance();
        }
        return imageLoader;
    }

    public static String translateLoaclImageDirFormat(String path) {
        String s = "file://" + path;
        return s;
    }

    public static String translateResouceFormat(int resouceId) {
        String s = "drawable://" + resouceId;
        return s;
    }

    /**
     * 图片加载第一次显示监听器
     *
     * @author Administrator
     */
    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        public AnimateFirstDisplayListener() {
        }

        public AnimateFirstDisplayListener(OnCompleteListener onCompleteListener) {
            this.onCompleteListener = onCompleteListener;
        }

        OnCompleteListener onCompleteListener;

        public OnCompleteListener getOnCompleteListener() {
            return onCompleteListener;
        }

        public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
            this.onCompleteListener = onCompleteListener;
        }

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 600);
                    displayedImages.add(imageUri);
                }
            }
            if (onCompleteListener != null) {
                onCompleteListener.onLoadingComplete(view, loadedImage);
            }
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            super.onLoadingFailed(imageUri, view, failReason);
            if (view != null)
                view.setTag(view.getId(), "-1");
////            ((ImageView) view).setImageBitmap(null);
            if (onCompleteListener != null) {
                onCompleteListener.onLoadingComplete(view, null);
            }
        }
    }


    private static class RoundDisplayer implements BitmapDisplayer {
        @Override
        public Bitmap display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
            if (!(imageAware instanceof ImageViewAware)) {
                throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
            }
            //  imageAware.setImageBitmap(PicturesChamfer.getCroppedBitmap(bitmap));
            return bitmap;
        }
    }

    public interface OnCompleteListener {
        public void onLoadingComplete(View view, Bitmap loadedImage);
    }


}
