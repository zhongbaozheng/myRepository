package cn.meiqu.lainmonitor.aui.power.elecMachine;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import cn.meiqu.baseproject.API;
import cn.meiqu.lainmonitor.FragmentWeb;

/**
 * Created by Fatel on 16-5-27.
 */
public class FragmentElecManage extends FragmentWeb {
    @Override
    public void loadUrl() {
        getmWeb().loadUrl(API.getAbsolutePath(API.webElecUrl));
        getmWeb().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((ViewGroup) v.getParent()).requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }
}
