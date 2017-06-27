package cn.meiqu.lainmonitor.hkvideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hikvision.netsdk.HCNetSDK;

import java.util.ArrayList;

import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.util.Config;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.common.BaseRecycleAdapter;
import cn.meiqu.lainmonitor.untils.DividerItemDecoration;
import cn.meiqu.lainmonitor.untils.SpaceItemDecoration;

/**
 * Created by Administrator on 2017/6/23.
 */

public class RTSPActivity extends BaseActivity {


    private SurfaceViewAdapter mAdapter;
    private ArrayList<DeviceBean> deviceBeanArrayList = new ArrayList<>();
    private RecyclerView recyclerView;


    @Override
    public void onHttpHandle(String action, String data) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtsp);
        Config.register(this);
        initTitle("视频监控");

        recyclerView = (RecyclerView)findViewById(R.id.list);

        if (!initeSdk()) {
            this.finish();
            return;
        }

        mAdapter = new SurfaceViewAdapter(this,deviceBeanArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListner(new BaseRecycleAdapter.ItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Config.putInt("channel",position+1);
                startActivity(new Intent(RTSPActivity.this,SimpleActivity.class));
            }
        });

        deviceBeanArrayList.clear();
        ArrayList<DeviceBean> beanArrayList = new ArrayList<>();
        for(int i = 0;i<16;i++){
            beanArrayList.add(new DeviceBean(i));
        }
        deviceBeanArrayList.addAll(beanArrayList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void initFragment() {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HCNetSDK.getInstance().NET_DVR_Cleanup();
    }
}
