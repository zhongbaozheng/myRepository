package cn.meiqu.lainmonitor.hkvideo;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hikvision.netsdk.HCNetSDK;

import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.util.Config;
import cn.meiqu.lainmonitor.R;

/**
 * Created by Administrator on 2017/6/24.
 */

public class SimpleActivity extends BaseActivity implements View.OnClickListener{

    private SurfaceView surfaceView;
    private PlayAssistant assistant;
    Button tv;
    Button tv2;
    Button tv3;
    private boolean flag;
    private boolean flag2;
    private boolean flag3;
    FrameLayout.LayoutParams lp;
    FrameLayout.LayoutParams lp2;
    FrameLayout.LayoutParams lp3;

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
        surfaceView.setOnClickListener(this);
        assistant = new PlayAssistant(surfaceView);
        assistant.play("192.168.1.65",8000,"admin","lain123456",Config.getInt("channel"));

        tv = new Button(this);
        tv.setText("停止");
        tv.setTextColor(getResources().getColor(R.color.colorAccent));
        lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM | Gravity.LEFT;
        lp.setMargins(0,0,0,370);
        addContentView(tv,lp);
        tv.setVisibility(View.GONE);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flag){
                    tv.setText("停止");
                    assistant.play("192.168.1.65",8000,"admin","lain123456",Config.getInt("channel"));
                    flag = true;
                }else{
                    assistant.stopPlay();
                    flag = false;
                    tv.setText("开始");
                }

            }
        });


        tv2 = new Button(this);
        tv2.setText("录像");
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        lp2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp2.gravity = Gravity.BOTTOM | Gravity.CENTER;
        lp2.setMargins(0,0,0,370);
        addContentView(tv2,lp2);
        tv2.setVisibility(View.GONE);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flag2){
                    if(flag){
                        tv2.setText("停止");
                        flag2 = true;
                        assistant.startRecord();
                    }else{
                        Toast.makeText(SimpleActivity.this,"请先预览！",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    tv2.setText("录像");
                    assistant.stopRecord();
                    flag2 = false;
                }

            }
        });


        tv3 = new Button(this);
        tv3.setText("全屏");
        tv3.setTextColor(getResources().getColor(R.color.colorAccent));
         lp3 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp3.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        lp3.setMargins(0,0,0,370);
        addContentView(tv3,lp3);
        tv3.setVisibility(View.GONE);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flag3){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    tv3.setText("竖屏");
                    flag3 = true;
                }else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    tv3.setText("横屏");
                    flag3 = false;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        assistant.Cleanup();
    }

    @Override
    public void initFragment() {

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.surfaceviewId){
            if(!flag){
                tv.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                flag = true;
            }else{
                tv.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.GONE);
                flag = false;
            }

        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
            lp.setMargins(0,0,0,0);
            lp2.setMargins(0,0,0,0);
            lp3.setMargins(0,0,0,0);
            tv.setLayoutParams(lp);
            tv2.setLayoutParams(lp2);
            tv3.setLayoutParams(lp3);
            toolbar.setVisibility(View.GONE);
            tv3.setText("竖屏");

        }
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            toolbar.setVisibility(View.VISIBLE);
            lp.setMargins(0,0,0,310);
            lp2.setMargins(0,0,0,310);
            lp3.setMargins(0,0,0,310);
            tv.setLayoutParams(lp);
            tv2.setLayoutParams(lp2);
            tv3.setLayoutParams(lp3);
            tv3.setText("横屏");
        }

    }
}