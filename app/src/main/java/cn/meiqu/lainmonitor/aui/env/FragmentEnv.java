package cn.meiqu.lainmonitor.aui.env;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import cn.meiqu.baseproject.util.LogUtil;
import cn.meiqu.lainmonitor.aui.FragmentControl;
import cn.meiqu.lainmonitor.aui.env.airF.FragmentAirF;
import cn.meiqu.lainmonitor.aui.env.airF.FragmentAirFManage;
import cn.meiqu.lainmonitor.aui.env.airJ.FragmentAirJ;
import cn.meiqu.lainmonitor.aui.env.airJ.FragmentAirJAlart;
import cn.meiqu.lainmonitor.aui.env.co2.FragmentCo2Alart;
import cn.meiqu.lainmonitor.aui.env.co2.FragmentCo2Manage;
import cn.meiqu.lainmonitor.aui.env.co2.FragmentCo2Real;
import cn.meiqu.lainmonitor.aui.env.dotwater.FragmentDotWaterAlart;
import cn.meiqu.lainmonitor.aui.env.dotwater.FragmentDotWaterReal;
import cn.meiqu.lainmonitor.aui.env.lighting.FragmentLighting;
import cn.meiqu.lainmonitor.aui.env.lwater.FragmentLwaterAlart;
import cn.meiqu.lainmonitor.aui.env.lwater.FragmentLwaterManage;
import cn.meiqu.lainmonitor.aui.env.lwater.FragmentLwaterReal;
import cn.meiqu.lainmonitor.aui.env.newfan.FragmentNewFan;
import cn.meiqu.lainmonitor.aui.env.nlwater.FragmentNLwaterAlart;
import cn.meiqu.lainmonitor.aui.env.nlwater.FragmentNLwaterReal;
import cn.meiqu.lainmonitor.aui.env.temp.FragmentTempAlert;
import cn.meiqu.lainmonitor.aui.env.temp.FragmentTempChart;
import cn.meiqu.lainmonitor.aui.env.temp.FragmentTempManage;
import cn.meiqu.lainmonitor.aui.env.temp.FragmentTempReal;
import cn.meiqu.lainmonitor.aui.env.thunder.FragmentThunderAlart;
import cn.meiqu.lainmonitor.aui.env.thunder.FragmentThunderReal;
import cn.meiqu.lainmonitor.aui.env.wet.FragmentWet;
import cn.meiqu.lainmonitor.bean.ThirdPage;

/**
 * Created by Fatel on 16-5-24.
 */
public class FragmentEnv extends FragmentControl {
    @Override
    public void initFragments(List<Fragment> fragments, ArrayList<ThirdPage> thirdPages) {
        LogUtil.log("initFragments=" + number2);
        //温湿度
        if (number2.equals("1")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentTempReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentTempAlert());
            if (thirdPages.size() >= 3)
                fragments.add(new FragmentTempChart());
            if (thirdPages.size() >= 4)
                fragments.add(new FragmentTempManage());
        }
        //非定位漏水
        else if (number2.equals("2")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentNLwaterReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentNLwaterAlart());
        } //定位漏水
        else if (number2.equals("3")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentLwaterReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentLwaterAlart());
            if (thirdPages.size() >= 3)
                fragments.add(new FragmentLwaterManage());
        }//点式漏水
        else if (number2.equals("4")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentDotWaterReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentDotWaterAlart());

        }
        //分体空调
        else if (number2.equals("5")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentAirF());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentAirFManage());
        }
        //精密空调
        else if (number2.equals("6")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentAirJ());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentAirJAlart());
        }
        //新风机
        else if (number2.equals("7")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentNewFan());
        }
        //抽湿加湿
        else if (number2.equals("8")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentWet());
        }
        //灯光照明
        else if (number2.equals("9")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentLighting());
        }
        //二氧化碳
        else if (number2.equals("10")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentCo2Real());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentCo2Alart());
            if (thirdPages.size() >= 3)
                fragments.add(new FragmentCo2Manage());
        }
        //防雷
        else if (number2.equals("11")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentThunderReal());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentThunderAlart());
        }
    }

    public void checkInput() {

    }
}
