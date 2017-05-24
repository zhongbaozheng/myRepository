package cn.meiqu.lainmonitor.aui.security.glass;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.aui.security.infrared.FragmentInfraredAlart;

/**
 * Created by Fatel on 16-5-25.
 */
public class FragmentGlassAlart extends FragmentInfraredAlart {
    String action_getData = className + API.getGlassAlart;

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getGlassAlart(deviceId, start, end, className);
    }


}
