package cn.meiqu.baseproject.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUtil {

    private static byte[] ToByte(Bitmap b, int quality) {
        if (b == null) {
            return null;
        }
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, quality, o);
        return o.toByteArray();
    }

    /**
     * convert Bitmap to byte array
     *
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        return ToByte(b, 100);
    }

    /*
 * 
 */
    public static byte[] bitmapToByte(Bitmap b, int quality) {
        return ToByte(b, quality);
    }

    /**
     * convert byte array to Bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory
                .decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     *
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     *
     * @param b
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     *
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     *
     * @param b
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }

    /**
     * get input stream from network by imageurl, you need to close inputStream
     * yourself
     *
     * @param imageUrl
     * @param readTimeOutMillis read time out, if less than 0, not set, in mills
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static InputStream getInputStreamFromUrl(String imageUrl,
                                                    int readTimeOutMillis) {
        InputStream stream = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (readTimeOutMillis > 0) {
                con.setReadTimeout(readTimeOutMillis);
            }
            stream = con.getInputStream();
        } catch (MalformedURLException e) {
            closeInputStream(stream);
            throw new RuntimeException("MalformedURLException occurred. ", e);
        } catch (IOException e) {
            closeInputStream(stream);
            throw new RuntimeException("IOException occurred. ", e);
        }
        return stream;
    }

    /**
     * get drawable by imageUrl
     *
     * @param imageUrl
     * @param readTimeOutMillis read time out, if less than 0, not set, in mills
     * @return
     */
    public static Drawable getDrawableFromUrl(String imageUrl,
                                              int readTimeOutMillis) {
        InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOutMillis);
        Drawable d = Drawable.createFromStream(stream, "src");
        closeInputStream(stream);
        return d;
    }

    /**
     * get Bitmap by imageUrl
     *
     * @param imageUrl
     * @return
     */
    public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut) {
        InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOut);
        Bitmap b = BitmapFactory.decodeStream(stream);
        closeInputStream(stream);
        return b;
    }

    /*
     * Uri转化为绝对路径
     */
    public static String getImageAbsolutePathFromUri(Activity activity, Uri uri) {
        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /*
     * 从Sd卡上得到图片，不压缩
     */
    public static Bitmap getBitmapFromSDcard(String url) {
        return BitmapFactory.decodeFile(url);
    }

    /*
     * 根据uri从Sd卡上得到图片吗，不压缩
     */
    public static Bitmap getBitmapFromSDcard(Uri uri, Activity activity) {
        return BitmapFactory.decodeFile(getImageAbsolutePathFromUri(activity,
                uri));
    }

    /**
     * @param @param  image
     * @param @param  max
     * @param @return
     * @return Bitmap
     * @throws
     * @Title: compressImageByMaxSize
     * @Description: 压缩图片到maxKB一下
     */
    public static byte[] compressImageByMaxSize(Bitmap image, int max) {
        // Bitmap image=BitmapFactory.decodeFile(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (!image.isRecycled()) {
            max = 100;
            ////
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//            if (baos.toByteArray().length / 1024 > max) {
//                baos.reset();
//                image = compressBitmap(image, 0.5f, 0.5f);
//                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            }
            int options = 100;
            while (baos.toByteArray().length / 1024 > max) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                options -= 8;// 每次都减少10
            }
            Log.e("fatel", "baos.toByteArray().length=" + baos.toByteArray().length);
//            image.recycle();
//            image = null;
            return baos.toByteArray();
        }
        return ToByte(image, 50);
    }

    /*
     * 质量压缩
     */
    public static Bitmap compressBitmapByQuality(Bitmap oldBitmap, int quality) {
        return byteToBitmap(bitmapToByte(oldBitmap, quality));
    }

    /*
     * 大小压缩(预加载)
     */
    public static Bitmap compressBitmapByPreLoad(String srcPath, int newWidth) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        float ww = newWidth;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        be = (int) (w / ww);
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        newOpts.inPreferredConfig = Config.ARGB_8888;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        int angel = readPictureDegree(srcPath);
        if (angel != 0) {
            bitmap = rotaingImageView(angel, bitmap);
        }
        return bitmap;// 压缩好比例大小后再进行质量压缩
    }

    /*
     * 缩放图片，原来的几倍
     */
    public static Bitmap compressBitmap(Bitmap org, float scaleWidth,
                                        float scaleHeight) {
        if (org == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        try {
            return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return org;
        }

    }

    /*
     * 缩放图片,目标大小
     */
    public static Bitmap compressBitmap(Bitmap org, int newWidth) {
        return compressBitmap(org, (float) newWidth / org.getWidth(),
                (float) newWidth / org.getWidth());
    }

    /*
     * 根据宽度等比例缩放图片
     */
    public static Bitmap compressBitmapByWidth(Bitmap defaultBitmap,
                                               float scaleWidth) {
        return compressBitmap(defaultBitmap, scaleWidth, 1.0f);
    }

    /**
     * 根据宽度等比例缩放图片
     */
    public static Bitmap compressBitmapByWidth(Bitmap defaultBitmap,
                                               int targetWidth) {
        return compressBitmap(defaultBitmap, targetWidth);
    }

    /**
     * @Title: toRoundCorner
     * @Description: 图片圆角设置
     */
    public static Bitmap toRoundCorner(Context context, Bitmap bitmap,
                                       int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        // 设置指定的背景
        // Drawable drawable = (context.getResources()
        // .getDrawable(R.drawable.corner_bg));
        // drawable.setBounds(rect);
        // drawable.draw(canvas);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 将图片变成圆形
     */
    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        int halfWidth = bitmap.getWidth() / 2;
        int halfHeight = bitmap.getHeight() / 2;
        canvas.drawCircle(halfWidth, halfHeight,
                Math.max(halfWidth, halfHeight), paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 将彩色图转换为黑白图
     *
     * @param 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertToBlackWhite(Bitmap bmp) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高

        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];
                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);
                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBmp;
    }

    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();

        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        try {

            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return resizedBitmap;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return bitmap;
        }

    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * close inputStream
     */
    private static void closeInputStream(InputStream s) {
        if (s == null) {
            return;
        }
        try {
            s.close();
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        }
    }
}
