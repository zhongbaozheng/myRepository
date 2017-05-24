package cn.meiqu.lainmonitor.aui.security.glass;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.aui.security.infrared.FragmentInfraredReal;

/**
 * Created by Fatel on 16-5-26.
 */
public class FragmentGlassReal extends FragmentInfraredReal {
    String action_getData = className + API.getGlassReal;

    @Override
    public String getAction() {
        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getGlassReal(className);
    }

}
