package cn.meiqu.lainmonitor;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.dao.SettingDao;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.baseproject.util.LogUtil;
import cn.meiqu.baseproject.util.StringUtil;
import cn.meiqu.baseproject.view.ClearEditText;
import cn.meiqu.baseproject.view.RippleView;
import cn.meiqu.lainmonitor.view.CustomProgress;

public class LoginActivity extends BaseActivity implements View.OnClickListener, RippleView.OnRippleCompleteListener {
    String className = getClass().getName();
    String action_login = className + API.login;
    private ClearEditText mEdtUesrname;
    private ClearEditText mEdtPwd;
    private TextView mTvPwdForget;
    private RippleView mRippleLogin;
    private FloatingActionButton mFab;

    String userName = "";
    String password = "";

    private CustomProgress customProgress;

    private void assignViews() {
        initTitle("登录");
        mEdtUesrname = (ClearEditText) findViewById(R.id.edt_uesrname);
        mEdtPwd = (ClearEditText) findViewById(R.id.edt_pwd);
        mTvPwdForget = (TextView) findViewById(R.id.tv_pwd_forget);
        mRippleLogin = (RippleView) findViewById(R.id.ripple_login);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        mRippleLogin.setOnRippleCompleteListener(this);
        mEdtUesrname.setText("" + SettingDao.getInstance().getAccount());
        mEdtPwd.setText("" + SettingDao.getInstance().getPwd());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initTransparent();
        assignViews();
        initReceiver(new String[]{action_login});
    }

    @Override
    public void initFragment() {

    }

    public void requestLogin() {
//        showProgressDialog();
        customProgress = CustomProgress.show(this,"登录中...",false,null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HttpGetController.getInstance().login(userName, password, className);
            }
        },2000);



    }

    public void handleLogin(String data) {
        try {
            JSONArray array = new JSONArray(data);
            JSONObject object = array.getJSONObject(0);
            if (object.optString("status").equals("1")) {
                SettingDao.getInstance().setIsLogin(1);
                SettingDao.getInstance().setAccount(userName);
                SettingDao.getInstance().setPwd(password);
                jumpFinish(MainActivity.class);
                toast("登录成功！");
            } else {
                toast("登录失败，您输入的用户名或密码有误！");
            }
            customProgress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onHttpHandle(String action, String data) {
        if (getHttpStatus(data)) {
            if (action.equals(action_login)) {
                handleLogin(data);
            }
        }
    }

    public void checkInput() {
        userName = mEdtUesrname.getText().toString();
        password = mEdtPwd.getText().toString();
        if (StringUtil.isEmpty(userName)) {
            toast("请输入账号！");
            return;
        } else if (StringUtil.isEmpty(password)) {
            toast("请输入密码！");
            return;
        } else {
            requestLogin();
        }
    }

    @Override
    public void onClick(View v) {
        jump(IpSettingActivity.class);
    }

    @Override
    public void onComplete(RippleView v) {
        if (v.getId() == mRippleLogin.getId()) {
//            jumpFinish(MainNewActivity.class);
            checkInput();
        }
    }
}
