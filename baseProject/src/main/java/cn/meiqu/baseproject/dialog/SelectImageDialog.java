package cn.meiqu.baseproject.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;


import java.io.File;

import cn.meiqu.baseproject.Sdcard;


public class SelectImageDialog extends ComonSelectDialog implements ComonSelectDialog.OnDialogItemCLickListener {
    public static String imagePath;
    public final static int ablumResult = 1;
    public final static int photographResult = 2;
    boolean isCustomAblum = false;
    public static long takePhotoId = 0;

    public SelectImageDialog(Context context) {
        super(context);
        initSelectImage();
    }

    public void show(boolean isCustomAblum) {
        this.isCustomAblum = isCustomAblum;
        super.show();
    }

    public void initSelectImage() {
        setOnDialogItemCLickListener(this);
        setData("请选择获取图片方式", new String[]{"拍照", "从相册中选择"});
    }

    /**
     * 本地相册
     */
    public void localLibrary() {
        if (!isCustomAblum) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");
            ((Activity) mContext).startActivityForResult(intent, ablumResult);
        } else {
//            Intent intent = new Intent(mContext, PhotoAlbumActivity.class);
//            ((Activity) mContext).startActivityForResult(intent, ablumResult);
        }
    }

    /**
     * 手机拍照
     */
    public void photograph() {
//        if (!isCustomAblum) {
        takePhotoId = System.currentTimeMillis();
        imagePath = Sdcard.AppRootDir + "/"
                + "" + takePhotoId + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(imagePath)));
        ((Activity) mContext).startActivityForResult(intent, photographResult);
//        } else {
//            Intent intent = new Intent(mContext, CameraActivity.class);
//            intent.putExtra(CameraActivity.PATH, Common.User_Root_Dir);
//            intent.putExtra(CameraActivity.OPEN_PHOTO_PREVIEW, false);
//            // intent.putExtra(CameraActivity.LAYOUT_ID, R.layout.fragment_camera_custom);
//            intent.putExtra(CameraActivity.USE_FRONT_CAMERA, false);
//            mContext.startActivity(intent);
        //      }
    }


    @Override
    public void onDialogItemClick(int position) {
        if (position == 0) {
            photograph();
        } else {
            localLibrary();
        }
    }
}
