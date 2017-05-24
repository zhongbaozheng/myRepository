package cn.meiqu.lainmonitor.aui.power.ups;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.aui.env.lwater.FragmentLwaterAlart;

/**
 * Created by Fatel on 16-5-25.
 */
public class FragmentUPSAlart extends FragmentLwaterAlart {
    String action_getData = className + API.getUPSAlart;

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public String[] getDeviceNames() {
        String names[] = new String[1];
        return names;
    }

    @Override
    public String getDeviceId(int position) {
        return "0";
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getUPSAlart(deviceId, start, end, className);
    }

}
