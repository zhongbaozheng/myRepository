package cn.meiqu.lainmonitor.aui.system;

import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.meiqu.lainmonitor.aui.FragmentControl;
import cn.meiqu.lainmonitor.aui.system.manage.Fragment8052;
import cn.meiqu.lainmonitor.aui.system.manage.Fragment8060;
import cn.meiqu.lainmonitor.aui.system.manage.FragmentAlart;
import cn.meiqu.lainmonitor.aui.system.manage.FragmentIp;
import cn.meiqu.lainmonitor.aui.system.manage.FragmentLocation;
import cn.meiqu.lainmonitor.bean.ThirdPage;

/**
 * Created by Fatel on 16-5-24.
 */
public class FragmentSystem extends FragmentControl {
    @Override
    public void initFragments(List<Fragment> fragments, ArrayList<ThirdPage> thirdPages) {
//设备管理
        if (number2.equals("1")) {
            if (thirdPages.size() >= 1)
                fragments.add(new FragmentLocation());
            if (thirdPages.size() >= 2)
                fragments.add(new FragmentAlart());
            if (thirdPages.size() >= 3)
                fragments.add(new FragmentIp());
            if (thirdPages.size() >= 4)
                fragments.add(new Fragment8060());
            if (thirdPages.size() >= 5)
                fragments.add(new Fragment8052());
        }
        //系统操作
        else if (number2.equals("2")) {

        }
        //管理员
        else if (number2.equals("3")) {
            fragments.add(new FragmentAdmin());
            mTabL.setVisibility(View.GONE);
        }else if(number2.equals("4")){

        }
    }

    public void checkInput() {

    }
}
