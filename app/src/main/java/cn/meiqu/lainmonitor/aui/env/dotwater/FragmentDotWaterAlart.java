package cn.meiqu.lainmonitor.aui.env.dotwater;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.aui.env.nlwater.FragmentNLwaterAlart;

/**
 * Created by Fatel on 16-5-25.
 */
public class FragmentDotWaterAlart extends FragmentNLwaterAlart {
    String action_getData = className + API.getDotLocationWaterAlart;

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getDotLocationWaterAlart(deviceId, start, end, className);
    }
}
