package cn.meiqu.lainmonitor.aui.env.dotwater;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.aui.env.nlwater.FragmentNLwaterReal;

/**
 * Created by Fatel on 16-5-25.
 */
public class FragmentDotWaterReal extends FragmentNLwaterReal {
    String action_getData = className + API.getDotWaterReal;

    @Override
    public String getAction() {
        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getDotWaterReal(className);
    }

}
