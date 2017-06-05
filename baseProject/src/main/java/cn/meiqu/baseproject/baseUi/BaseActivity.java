package cn.meiqu.baseproject.baseUi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cn.meiqu.baseproject.R;
import cn.meiqu.baseproject.bean.MsgBean;
import cn.meiqu.baseproject.dialog.LoadingDialog;
import cn.meiqu.baseproject.dialog.SelectImageDialog;
import cn.meiqu.baseproject.util.Config;
import cn.meiqu.baseproject.util.StringUtil;
import cn.meiqu.baseproject.util.ToastUtil;

/**
 * Created by Fatel on 15-4-9.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public FragmentManager fm;
    public BroadcastReceiver receiver;
    public int containerId;
    public LoadingDialog progressDialog = null;
    public static String action_exitApplication = "action_exitApplication";
    public ExitBroadCase exitBroadCase = new ExitBroadCase();

    //
    Toolbar toolbar;
    TextView mTvTitle;
    TextView mTvRight;
    public SwipeRefreshLayout swipeRefresh;

    public void initTitle(String title) {
        initTitle(new SpannableStringBuilder(title));
    }

    public void initTitle(SpannableStringBuilder title) {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBack();
            }
        });
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvRight = (TextView) findViewById(R.id.tv_title_more);
        setTitle(title);
    }

    public void initTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

//    public void initTitleWithNavi(String title, View.OnClickListener listener) {
//        initTitle(title);
//        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.);
//        toolbar.setNavigationOnClickListener(listener);
//    }


    public void setTitleRight(String text, View.OnClickListener listener) {
        if (mTvRight != null) {
            mTvRight.setText(text + "");
            mTvRight.setVisibility(View.VISIBLE);
            mTvRight.setOnClickListener(listener);
        }
    }

    public void setTitle(String title) {
        mTvTitle.setText(new SpannableStringBuilder(title));
    }

    public void setTitle(SpannableStringBuilder title) {
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
//        mTvTitle.setText(title);
        getSupportActionBar().setTitle(title);
    }

    public void setSwipeRefresh(int id, SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        swipeRefresh = (SwipeRefreshLayout) findViewById(id);
//        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.window_background));
        swipeRefresh.setOnRefreshListener(onRefreshListener);
    }

    public void setSwipeRefreshing(boolean ing) {
        if (swipeRefresh != null) {
            if (ing != swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(ing);
            }
        }
    }

    public abstract void onHttpHandle(String action, String data);


    public boolean getHttpStatus(String data) {
        setSwipeRefreshing(false);
        dismissProgressDialog();
        if (StringUtil.isEmpty(data)) {
            ToastUtil.showNetWorkFailure(this);
            return false;
        } else {
            return true;
        }
    }

    public void initReceiver(String[] filters) {
        receiver = new ActionReceiver();
        IntentFilter filter = new IntentFilter();
        for (String action : filters) {
            filter.addAction(action);
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    class ActionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String data = intent.getStringExtra("data");
            //cn.meiqu.lainmonitor.MainNewActivityktr-mrms/queHeadJson.html
            Log.e("homeAction",action);
            //[{"id":1,"isShow":1,"name":"主页","number":1},{"id":3,"isShow":1,"name":"动力监控","number":3}]
            Log.e("homeData",data);
            onHttpHandle(action, data);
        }
    }

    public void showProgressDialog(String content) {
        if (progressDialog == null) {
            progressDialog = new LoadingDialog(this);
        }
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showMsg(String data){
        ArrayList<MsgBean> msgBeanArrayList = new ArrayList<>();
        ArrayList<MsgBean> temps = new Gson().fromJson(data,new TypeToken<ArrayList<MsgBean>>(){}.getType());
        if(temps!=null){
            msgBeanArrayList.clear();
            msgBeanArrayList.addAll(temps);
            MsgBean bean = msgBeanArrayList.get(0);
            Toast.makeText(this,bean.msg,Toast.LENGTH_SHORT).show();
        }
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.register(this);
        fm = getSupportFragmentManager();
        containerId = R.id.frame_fragment;
        LocalBroadcastManager.getInstance(this).registerReceiver(exitBroadCase, new IntentFilter(action_exitApplication));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(exitBroadCase);
        if (receiver != null)
            try {
                LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public abstract void initFragment();

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    //添加fragment
    public void showFirst(Fragment f) {
        fm.beginTransaction().add(containerId, f, f.getClass().getName())
                .commit();
    }

    public void showNoPop(Fragment f) {
        fm.beginTransaction().replace(containerId, f, f.getClass().getName())
                .commit();
    }

    public void showAndPop(Fragment f, int containerId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            fm.beginTransaction().setCustomAnimations(R.anim.push_right_in, R.anim.fragment_fade_out, R.anim.activity_fade_in, R.anim.push_right_out).replace(containerId, f, f.getClass().getName())
                    .addToBackStack(null).commit();
        } else {
            fm.beginTransaction().replace(containerId, f, f.getClass().getName())
                    .addToBackStack(null).commit();
        }
    }

    public void showAndPop(Fragment f) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            fm.beginTransaction().setCustomAnimations(R.anim.push_right_in, R.anim.fragment_fade_out, R.anim.activity_fade_in, R.anim.push_right_out).replace(containerId, f, f.getClass().getName())
                    .addToBackStack(null).commit();
        } else {
            fm.beginTransaction().replace(containerId, f, f.getClass().getName())
                    .addToBackStack(null).commit();
        }
    }


    public void showAndPopWithAnimation(Fragment f) {
        fm.beginTransaction().setCustomAnimations(R.anim.activity_fade_in, R.anim.fragment_fade_out).replace(containerId, f, f.getClass().getName())
                .addToBackStack(null).commit();
    }

    public void add(Fragment f) {
        fm.beginTransaction().add(containerId, f, f.getClass().getName())
                .commit();
    }

    public void popBack() {
        if (fm.getBackStackEntryCount() > 0)
            fm.popBackStack();
        else
            finish();

    }

    public void jump(Class c) {
        Intent intent = new Intent(this, c);
        jump(intent);
    }

    public void jump(Intent intent) {
        startActivity(intent);
    }

    public void jumpFinish(Class c) {
        jump(c);
        finish();
    }

    public void jumpForResult(Class c, int requestCode) {
        Intent intent = new Intent(this, c);
        jumpForResult(intent, requestCode);
    }

    public void jumpForResult(Intent i, int requestCode) {
        startActivityForResult(i, requestCode);
    }

    class ExitBroadCase extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(action_exitApplication)) {
                //TODO 退出程序时，保存用户统计数据
                MobclickAgent.onKillProcess(BaseActivity.this);
                finish();
            }
        }
    }

    public void toast(String text) {

        ToastUtil.show(this, text);
    }

    public void onSelectImageReuslt(String path) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (fm.getFragments() != null) {
            for (Fragment f : fm.getFragments()) {
                if (f != null && f.isVisible()) {
                    f.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
        if (resultCode == -1) {
            switch (requestCode) {
                // 如果是直接从相册获取
                case SelectImageDialog.ablumResult:
                    if (data != null) {
                        try {
                            Uri uri = data.getData();
                            String[] proj = {MediaStore.Images.Media.DATA};
                            Cursor actualimagecursor = managedQuery(uri, proj, null,
                                    null, null);
                            int actual_image_column_index = actualimagecursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            actualimagecursor.moveToFirst();
                            onSelectImageReuslt(actualimagecursor
                                    .getString(actual_image_column_index));
                        } catch (Exception e) {

                        }
                    }
                    break;
                case SelectImageDialog.photographResult:
                    onSelectImageReuslt(SelectImageDialog.imagePath);
                    break;

            }
        }
        // super.onActivityResult(requestCode, resultCode, data);
    }
//
//    //TODO 加入友盟统计
//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }

    public void toastShow(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onResume(this);
        //TODO 自定义事件的代码需要放在Activity里的onResume方法后面，不支持放在onCreat()方法中
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
