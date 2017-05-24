package cn.meiqu.lainmonitor.aui.security;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import cn.meiqu.lainmonitor.aui.FragmentControl;
import cn.meiqu.lainmonitor.aui.security.doorControl.FragmentDoorControlHistroy;
import cn.meiqu.lainmonitor.aui.security.doorControl.FragmentDoorControlManage;
import cn.meiqu.lainmonitor.aui.security.doorControl.FragmentDoorControlReal;
import cn.meiqu.lainmonitor.aui.security.doorMagn.FragmentDoorMagnAlart;
import cn.meiqu.lainmonitor.aui.security.doorMagn.FragmentDoorMagnReal;
import cn.meiqu.lainmonitor.aui.security.fire.FragmentFireAlart;
import cn.meiqu.lainmonitor.aui.security.fire.FragmentFireReal;
import cn.meiqu.lainmonitor.aui.security.glass.FragmentGlassAlart;
import cn.meiqu.lainmonitor.aui.security.glass.FragmentGlassReal;
import cn.meiqu.lainmonitor.aui.security.infrared.FragmentInfraredAlart;
import cn.meiqu.lainmonitor.aui.security.infrared.FragmentInfraredReal;
import cn.meiqu.lainmonitor.aui.security.smoke.FragmentSmokeAlart;
import cn.meiqu.lainmonitor.aui.security.smoke.FragmentSmokeReal;
import cn.meiqu.lainmonitor.bean.ThirdPage;

/**
 * Created by Fatel on 16-5-24.
 */
public class FragmentSecy extends FragmentControl {
    @Override
    public void initFragments(List<Fragment> fragments, ArrayList<ThirdPage> thirdPages) {
        //红外监控
        if (number2.equals("1")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentInfraredReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentInfraredAlart());
        }
        //烟感监控
        else if (number2.equals("2")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentSmokeReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentSmokeAlart());
        }
        //消防监控
        else if (number2.equals("3")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentFireReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentFireAlart());
        }
        //玻璃监控
        else if (number2.equals("4")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentGlassReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentGlassAlart());
        }
        //门磁监控
        else if (number2.equals("5")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentDoorMagnReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentDoorMagnAlart());
        }
        //门禁监控
        else if (number2.equals("6")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentDoorControlReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentDoorControlManage());
            if (thirdPages.size() >= 3)
                fragments.add(new FragmentDoorControlHistroy());
        }
        //视频监控
        else if (number2.equals("7")) {

        }
    }

    public void checkInput() {

    }
}
