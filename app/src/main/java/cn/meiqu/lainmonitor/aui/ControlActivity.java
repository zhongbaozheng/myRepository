package cn.meiqu.lainmonitor.aui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.aui.env.FragmentEnv;
import cn.meiqu.lainmonitor.aui.operation.FragmentOper;
import cn.meiqu.lainmonitor.aui.power.FragmentPower;
import cn.meiqu.lainmonitor.aui.security.FragmentSecy;
import cn.meiqu.lainmonitor.aui.system.FragmentSystem;

/**
 * ControlActivity这里选择我们对应的fragment
 */
public class ControlActivity extends BaseActivity {

    public static String extra_pId = "extra_pId";
    public static String extra_cId = "extra_cId";
    public static String extra_name = "extra_name";


    String pId = "";
    String cId = "";
    String name = "";
    Fragment f = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        pId = getIntent().getStringExtra(extra_pId);
        cId = getIntent().getStringExtra(extra_cId);
        name = getIntent().getStringExtra(extra_name);
        initTitle("" + name);
        checkInput();
        initFragment();
    }

    public void checkInput() {
        //环境监控
        if (pId.equals("2")) {
            f = new FragmentEnv();
        }
        //动力监控
        else if (pId.equals("3")) {
            f = new FragmentPower();
        }

        //安防监控
        else if (pId.equals("4")) {
            f = new FragmentSecy();
        }

        //运维监控
        else if (pId.equals("5")) {
            f = new FragmentOper();
            FragmentOper.number1 = pId;
            FragmentOper.number2 = cId;
        }
        //系统管理
        else if (pId.equals("6")) {
            f = new FragmentSystem();
        }
    }

    @Override
    public void initFragment() {
        FragmentControl.number1 = pId;
        FragmentControl.number2 = cId;
        showFirst(f);
    }

    @Override
    public void onHttpHandle(String action, String data) {

    }
}
