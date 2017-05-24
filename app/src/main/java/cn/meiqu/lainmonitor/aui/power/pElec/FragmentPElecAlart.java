package cn.meiqu.lainmonitor.aui.power.pElec;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.aui.FragmentControl;
import cn.meiqu.lainmonitor.aui.env.nlwater.FragmentNLwaterAlart;
import cn.meiqu.lainmonitor.bean.Electricity;

/**
 * Created by Fatel on 16-5-25.
 */
public class FragmentPElecAlart extends FragmentNLwaterAlart {
    String action_getData = className + API.getPElecAlart;

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getPElecAlart(deviceId, start, end, className);
    }

    @Override
    public String[] getDeviceNames() {
        ArrayList<Electricity> tempReals = ((FragmentPElecReal) ((FragmentControl) getParentFragment()).fragments.get(0)).electricities;
        String names[] = new String[tempReals.size() + 1];
        for (int i = 1; i < names.length; i++) {
            names[i] = tempReals.get(i - 1).getName();
        }
        return names;
    }

    @Override
    public String getDeviceId(int position) {
        ArrayList<Electricity> tempReals = ((FragmentPElecReal) ((FragmentControl) getParentFragment()).fragments.get(0)).electricities;
        return tempReals.get(position - 1).getId() + "";
    }
}
