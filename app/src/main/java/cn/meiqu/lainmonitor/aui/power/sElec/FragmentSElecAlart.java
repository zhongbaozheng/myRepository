package cn.meiqu.lainmonitor.aui.power.sElec;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.aui.power.pElec.FragmentPElecAlart;

/**
 * Created by Fatel on 16-5-25.
 */
public class FragmentSElecAlart extends FragmentPElecAlart {
    String action_getData = className + API.getSElecAlart;

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getSElecAlart(deviceId, start, end, className);
    }


}
