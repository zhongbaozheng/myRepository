package cn.meiqu.lainmonitor.aui.power.elecMachine;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/6/5.
 */

public class ElecMachineManageActivity extends BaseActivity {

    @BindView(R.id.battery_scrollView)
    NestedScrollView mScroll;
    @BindView(R.id.ptr_view)
    PtrClassicFrameLayout mPtr;

    private boolean isUp;


    String className = getClass().getName();
    String action_getData = className + API.batteryUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_battery);
        ButterKnife.bind(this);
        initTitle("设备管理");
        initReceiver(new String[]{action_getData});
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScroll, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestData();
            }
        });

    }

    @Override
    public void onHttpHandle(String action, String data) {
        if(action.equals(action_getData)){
            //获取
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPtr.refreshComplete();
                }
            },1000);
        }
    }

    @Override
    public void initFragment() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData(){
        HttpGetController.getInstance().getBaterry(className);
    }
}
