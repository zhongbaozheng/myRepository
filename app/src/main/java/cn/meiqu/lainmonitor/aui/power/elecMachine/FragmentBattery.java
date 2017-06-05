package cn.meiqu.lainmonitor.aui.power.elecMachine;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.aui.FragmentNormal;

/**
 * Created by Administrator on 2017/6/1.
 */

public class FragmentBattery extends FragmentNormal {
    String action_getData = className + API.batteryUrl;
    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData() {
        HttpGetController.getInstance().getBaterry(className);
    }

    @Override
    public void handleData(String data) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activtiy_battery;
    }

    @Override
    public void initViews() {

    }
}
