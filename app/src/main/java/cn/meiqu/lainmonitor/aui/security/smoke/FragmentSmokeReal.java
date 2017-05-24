package cn.meiqu.lainmonitor.aui.security.smoke;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.aui.security.infrared.FragmentInfraredReal;

/**
 * Created by Fatel on 16-5-26.
 */
public class FragmentSmokeReal extends FragmentInfraredReal {
    String action_getData = className + API.getSmokeReal;

    @Override
    public String getAction() {
        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getSmokeReal(className);
    }

}
