package cn.meiqu.baseproject.baseUi;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

import cn.meiqu.baseproject.Sdcard;


/**
 * Created by Administrator on 2015/9/17.
 */
public class BaseApp extends Application {
    public static boolean hasSend;
    public static Context mContext = null;
    public static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mHandler = new Handler();
        initImageLoader(this);
//        initJpush();
//        createFolder();
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

//    private void initJpush() {
//        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);            // 初始化 JPush
//    }

//    public void createFolder() {
//        FileUtil.makeFolders(Sdcard.AppRootDir);
//    }

    /**
     * @param @param context
     * @return void
     * @throws
     * @Title: initImageLoader
     * @Description: 配置Universal Image Loader for Android （异步图片加载缓存组件）
     */
    public void initImageLoader(Context context) {
        File cacheDir = new File(Sdcard.Image_cache_Dir);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 1)//设置线程优先级,越低越高优先权
                .denyCacheImageMultipleSizesInMemory() //当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .discCache(new UnlimitedDiscCache(cacheDir))// 设置缓存的目录
                .discCacheFileNameGenerator(new Md5FileNameGenerator())// 缓存命名规则,MD5FIleName是url的md5
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .tasksProcessingOrder(QueueProcessingType.FIFO) //设置读图为先进先出
                .memoryCacheExtraOptions(800, 800)
                .imageDownloader(new BaseImageDownloader(BaseApp.this, 3000, 8000))// 设置超时 connectTimeout 和 readTimeout
                .threadPoolSize(4)//下载线程数量
                .build();
        // Initialize ImageLoader with confguration.
        ImageLoader.getInstance().init(config);
    }

}
