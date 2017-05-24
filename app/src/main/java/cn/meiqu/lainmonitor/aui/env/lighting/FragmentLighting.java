package cn.meiqu.lainmonitor.aui.env.lighting;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.aui.env.newfan.FragmentNewFan;

/**
 * Created by Fatel on 16-5-26.
 */
public class FragmentLighting extends FragmentNewFan {
    String action_getData = className + API.getLighting;
    String action_switch = className + API.siwtchNewFan;


    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public String[] getActions() {
        return new String[]{action_getData, action_switch};
    }

    public void requestData() {
        HttpGetController.getInstance().getLighting(className);
    }

}
