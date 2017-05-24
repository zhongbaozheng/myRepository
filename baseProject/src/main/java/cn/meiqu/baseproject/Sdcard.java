package cn.meiqu.baseproject;

import android.os.Environment;

import java.io.File;

/**
 * Created by Fatel on 15-6-5.
 */
public class Sdcard {
    /**
     * 程序文件夹名称
     */
    public final static String AppName = "Fatel";
    /**
     * 主文件夹目录
     */
    public final static String AppRootDir = Environment
            .getExternalStorageDirectory() + File.separator + AppName;
    /**
     * 缓存文件夹
     */
    public final static String Image_cache_Dir = AppRootDir + File.separator
            + ".cache" + File.separator + "image";

    /**
     * 用户的文件夹
     */
    public final static String User_Root_Dir = AppRootDir + File.separator
            + "User";
}
