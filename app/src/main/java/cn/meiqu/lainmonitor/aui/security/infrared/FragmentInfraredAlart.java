package cn.meiqu.lainmonitor.aui.security.infrared;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.aui.FragmentControl;
import cn.meiqu.lainmonitor.aui.env.nlwater.FragmentNLwaterAlart;
import cn.meiqu.lainmonitor.bean.Security;

/**
 * Created by Fatel on 16-5-25.
 */
public class FragmentInfraredAlart extends FragmentNLwaterAlart {
    String action_getData = className + API.getInfraredAlart;

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getInfraredAlart(deviceId, start, end, className);
    }

    @Override
    public String[] getDeviceNames() {
        ArrayList<Security> tempReals = ((FragmentInfraredReal) ((FragmentControl) getParentFragment()).fragments.get(0)).securities;
        String names[] = new String[tempReals.size() + 1];
        for (int i = 1; i < names.length; i++) {
            names[i] = tempReals.get(i - 1).getName();
        }
        return names;
    }

    @Override
    public String getDeviceId(int position) {
        ArrayList<Security> tempReals = ((FragmentInfraredReal) ((FragmentControl) getParentFragment()).fragments.get(0)).securities;
        return tempReals.get(position - 1).getId() + "";
    }
}
