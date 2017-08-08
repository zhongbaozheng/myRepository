package cn.meiqu.lainmonitor.hkvideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.lainmonitor.R;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 2017/8/8.
 */

public class HKControlActivity extends BaseActivity {



    @Override
    public void onHttpHandle(String action, String data) {

    }

    @Override
    public void initFragment() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hkcontrl);
        initTitle("视频监控");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.hk1,R.id.hk2})
    public void onClick(View view){
        if(view.getId() == R.id.hk1){
            startActivity(new Intent(this,OnlyLoginIdActivity.class));
        }

        if(view.getId() == R.id.hk2){
            startActivity(new Intent(this,CameraMoveActivity.class));
        }
    }
}
