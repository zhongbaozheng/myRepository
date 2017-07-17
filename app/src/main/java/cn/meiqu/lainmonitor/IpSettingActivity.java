package cn.meiqu.lainmonitor;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.ArrayList;

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
        permissionManage();
    }


    //permissonManage
    public void permissionManage(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
          getPersimmions();
        }
    }

    @TargetApi(23)
    public void getPersimmions() {
        ArrayList<String> permissions = new ArrayList<String>();
        /***
         * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
         */
        // 定位精确位置
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        /*
		 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
		 */
        // 读写权限
//        if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
//        }
//        // 读取电话状态权限
//        if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
//            permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
//        }
        if(addPermission(permissions,Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionInfo += "Manifest.permission.ACCESS_FINE_LOCATION Deny \n";
        }


        if (permissions.size() > 0) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }

        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        for (int i = 0 ;i < permissions.length;i++) {
//            if(permissions[i].equals(Manifest.permission.CAMERA)) {
//                if( grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//
//                }else {
//                    finish();
//                }
//            }
//        }
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
