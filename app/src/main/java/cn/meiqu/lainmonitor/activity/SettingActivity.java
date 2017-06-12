package cn.meiqu.lainmonitor.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.dao.SettingDao;
import cn.meiqu.lainmonitor.R;

/**
 * Created by Administrator on 2017/6/12.
 */

public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initTitle("设置");
    }

    @Override
    public void onHttpHandle(String action, String data) {

    }

    @Override
    public void initFragment() {

    }

    @OnClick({R.id.fb_logout})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.fb_logout:

                SettingDao.getInstance().setIsLogin(0);
                SettingDao.getInstance().setAccount("");
                SettingDao.getInstance().setPwd("");
                Toast.makeText(this,"已退出！",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },300);
                break;
        }
    }
}
