package cn.meiqu.lainmonitor.aui.power.sElec;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.aui.power.pElec.FragmentPElecReal;

/**
 * Created by Fatel on 16-5-25.
 */
public class FragmentSElecReal extends FragmentPElecReal {
    String action_getData = className + API.getSElecReal;

    @Override
    public String getAction() {
        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getSElecReal(className);
    }

}
