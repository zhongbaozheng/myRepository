package cn.meiqu.lainmonitor.hkvideo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_JPEGPARA;

import java.io.File;
import java.util.ArrayList;

import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.util.Config;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.common.BaseRecycleAdapter;

/**
 * Created by Administrator on 2017/8/8.
 */

public class CameraMoveActivity extends BaseActivity {

    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
    private int m_iStartChan = 0; // start channel no
    private String TAG = "admin";
    private int mLoginId = -1;
    private int channel = 1;
    private ArrayList<DeviceBean> deviceBeanArrayList = new ArrayList<>();

    private RecyclerView recyclerView;
    private DeviceAdapter mAdapter;


    @Override
    public void onHttpHandle(String action, String data) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlylogin);
        Config.register(this);
        initTitle("视频列表二");


        if (!initeSdk()) {
            this.finish();
            return;
        }

        recyclerView = (RecyclerView)findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new DeviceAdapter(this,deviceBeanArrayList);
        mAdapter.setOnItemClickListner(new BaseRecycleAdapter.ItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                //点击监听
                Config.putInt("channel",position+1);
                startActivity(new Intent(CameraMoveActivity.this,CameraPlayActivity.class));
            }
        });
        recyclerView.setAdapter(mAdapter);

        String strIP = "192.168.1.64";
        int nPort = 8000;
        String strUser = "admin";
        String strPsd = "admin123";

        mLoginId = loginNormalDevice(strIP,nPort,strUser,strPsd,channel+32);



        if( mLoginId>=0){

            for(int i=0;i<1;i++){

                NET_DVR_JPEGPARA net_dvr_jpegpara = new NET_DVR_JPEGPARA();
                net_dvr_jpegpara.wPicSize = 1280*720;
                net_dvr_jpegpara.wPicQuality = 1;

                File file = new File("/mnt/sdcard/surface2/");
                if(!file.exists()){
                    file.mkdir();
                    Log.e("mkdir","make success");
                }
                //云台转动的频道并不在32以上，而是从1开始。。。。
                if (HCNetSDK.getInstance().NET_DVR_CaptureJPEGPicture(mLoginId, channel, net_dvr_jpegpara, "/mnt/sdcard/surface2/" + channel + ".jpg"))
                {
                    Log.e(TAG,"capture success!");
                    String myJpgPath = "/mnt/sdcard/surface2/" + channel + ".jpg";
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap bm = BitmapFactory.decodeFile(myJpgPath, options);

                    DeviceBean bean =  new DeviceBean();
                    bean.bitmap = bm;
                    deviceBeanArrayList.add(bean);
                    channel++;
                }else{
                    Log.e(TAG,"capture failed!");
                }

            }
            mAdapter.notifyDataSetChanged();

        }




//        if(mLoginId>=0 ){
//            NET_DVR_JPEGPARA net_dvr_jpegpara = new NET_DVR_JPEGPARA();
//            net_dvr_jpegpara.wPicSize = 1280*720;
//            net_dvr_jpegpara.wPicQuality = 1;
//            File file = new File("/mnt/sdcard/surface/");
//            if(!file.exists()){
//                file.mkdir();
//                Log.e("mkdir","make success");
//            }
//            if(file.exists()) {
//                if (HCNetSDK.getInstance().NET_DVR_CaptureJPEGPicture(mLoginId, channel + 32, net_dvr_jpegpara, "/mnt/sdcard/surface/" + channel + ".jpg")) {
//
//                    String myJpgPath = "/mnt/sdcard/surface/" + channel + ".jpg";
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 2;
//                    Bitmap bm = BitmapFactory.decodeFile(myJpgPath, options);
////                    mImage.setImageBitmap(bm);
//
//                    Log.e(TAG, "capture success");
//                } else {
//                    Log.e(TAG, "capture error!");
//                }
//
//            }
//        }



    }

    private boolean initeSdk() {
        // init net sdk
        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
            Log.e("lzc","HCNetSDK init is failed!");
            return false;
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/",
                true);
        return true;
    }


    /** 登录设备*/
    private int loginNormalDevice(String strIP, int nPort, String strUser, String strPsd, int channel)
    {
        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30 )
        {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }
        // call NET_DVR_Login_v30 to login on, port 8000 as default
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort, strUser, strPsd,
                m_oNetDvrDeviceInfoV30);
        if (iLogID < 0 )
        {
            Log.e(TAG, "NET_DVR_Login is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return -1;
        }
        // logcat.toast("shebei: "+m_oNetDvrDeviceInfoV30.wDevType);
        // logcat.toast("shebei: "+m_oNetDvrDeviceInfoV30.byStartDChan);
        // logcat.toast("shebei: "+m_oNetDvrDeviceInfoV30.byDVRType);
        if (m_oNetDvrDeviceInfoV30.byChanNum > 0 )
        {
            // m_oNetDvrDeviceInfoV30.byStartChan
            m_iStartChan = channel;
        }
        else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0 )
        {
            // m_oNetDvrDeviceInfoV30.byStartDChan
            m_iStartChan = channel + 32;
            Log.e("m_iStartChan",m_iStartChan+"");
        }

        Log.i(TAG, "NET_DVR_Login is Successful!");

        return iLogID;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        HCNetSDK.getInstance().NET_DVR_Cleanup();
    }

    @Override
    public void initFragment() {

    }
}
