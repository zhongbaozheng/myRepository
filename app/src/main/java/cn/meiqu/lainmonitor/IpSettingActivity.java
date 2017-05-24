package cn.meiqu.lainmonitor;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.dao.SettingDao;
import cn.meiqu.baseproject.util.StringUtil;
import cn.meiqu.baseproject.view.ClearEditText;
import cn.meiqu.baseproject.view.RippleView;

public class IpSettingActivity extends BaseActivity implements RippleView.OnRippleCompleteListener {
    private ClearEditText mEdtAddr;
    private ClearEditText mEdtIp;
    private TextView mTvPwdForget;
    private RippleView mRippleComit;
    public static boolean isFirst = false;

    private void assignViews() {
        initTitle("IP/域名设置");
        mEdtAddr = (ClearEditText) findViewById(R.id.edt_addr);
        mEdtIp = (ClearEditText) findViewById(R.id.edt_ip);
        mTvPwdForget = (TextView) findViewById(R.id.tv_pwd_forget);
        mRippleComit = (RippleView) findViewById(R.id.ripple_comit);
        mEdtAddr.setText("" + SettingDao.getInstance().getHostAddr());
        mEdtIp.setText("" + SettingDao.getInstance().getHostIp());
        mRippleComit.setOnRippleCompleteListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_setting);
        assignViews();
    }

    @Override
    public void initFragment() {

    }

    @Override
    public void onHttpHandle(String action, String data) {

    }

    public void checkInput() {
        String ip = mEdtIp.getText().toString();
        String addr = mEdtAddr.getText().toString();
        if (StringUtil.isEmpty(addr) && StringUtil.isEmpty(ip)) {
            toast("请输入域名或者IP");
        } else {
            showProgressDialog();
            SettingDao.getInstance().setHostIp(ip);
            SettingDao.getInstance().setHostAddr(addr);
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isFirst) {
                        jumpFinish(LoginActivity.class);
                        isFirst = false;
                    } else {
                        finish();
                    }
                }
            }, 1000);
            toast("设置成功");
        }
    }

    @Override
    public void onComplete(RippleView v) {
        if (v.getId() == mRippleComit.getId()) {
            checkInput();
        }
    }
}
