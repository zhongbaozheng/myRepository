package cn.meiqu.lainmonitor.aui.power;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import cn.meiqu.lainmonitor.aui.FragmentControl;
import cn.meiqu.lainmonitor.aui.power.elecMachine.FragmentElecMachine;
import cn.meiqu.lainmonitor.aui.power.elecMachine.FragmentElecMachineAlert;
import cn.meiqu.lainmonitor.aui.power.elecMachine.FragmentElecManage;
import cn.meiqu.lainmonitor.aui.power.pElec.FragmentPElecAlart;
import cn.meiqu.lainmonitor.aui.power.pElec.FragmentPElecReal;
import cn.meiqu.lainmonitor.aui.power.sElec.FragmentSElecAlart;
import cn.meiqu.lainmonitor.aui.power.sElec.FragmentSElecReal;
import cn.meiqu.lainmonitor.aui.power.ups.FragmentUPS;
import cn.meiqu.lainmonitor.aui.power.ups.FragmentUPSAlart;
import cn.meiqu.lainmonitor.bean.ThirdPage;

/**
 * Created by Fatel on 16-5-24.
 */
public class FragmentPower extends FragmentControl {
    @Override
    public void initFragments(List<Fragment> fragments, ArrayList<ThirdPage> thirdPages) {
        //电量仪
        if (number2.equals("1")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentElecMachine());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentElecMachineAlert());
            if (thirdPages.size() >= 3)
                fragments.add(new FragmentElecManage());
        }
        //配电监控
        else if (number2.equals("2")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentPElecReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentPElecAlart());
        }
        //市电监控
        else if (number2.equals("3")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentSElecReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentSElecAlart());
        }
        //巡检仪
        else if (number2.equals("4")) {

        }
        //UPS
        else if (number2.equals("5")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentUPS());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentUPSAlart());
        }
    }

    public void checkInput() {

    }
}
