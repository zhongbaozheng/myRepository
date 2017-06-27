package cn.meiqu.lainmonitor.hkvideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;

import com.hikvision.netsdk.HCNetSDK;

import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.util.Config;
import cn.meiqu.lainmonitor.R;

/**
 * Created by Administrator on 2017/6/24.
 */

public class SimpleActivity extends BaseActivity {

    private SurfaceView surfaceView;
    private PlayAssistant assistant;

    @Override
    public void onHttpHandle(String action, String data) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surface);
        Config.register(this);
        initTitle("视频监控");
        HCNetSDK.getInstance().NET_DVR_Init();
        surfaceView = (SurfaceView)findViewById(R.id.surfaceviewId);
        assistant = new PlayAssistant(surfaceView);
        assistant.play("192.168.1.65",8000,"admin","lain123456", Config.getInt("channel"),0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        assistant.Cleanup();
    }

    @Override
    public void initFragment() {

    }
}
