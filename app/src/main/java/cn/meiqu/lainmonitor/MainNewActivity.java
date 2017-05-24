package cn.meiqu.lainmonitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.dao.SettingDao;
import cn.meiqu.baseproject.httpGet.HttpGetBase;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.baseproject.httpGet.HttpResponController;
import cn.meiqu.baseproject.util.LogUtil;
import cn.meiqu.baseproject.util.ScreenUtil;
import cn.meiqu.baseproject.util.StringUtil;
import cn.meiqu.baseproject.util.ToastUtil;
import cn.meiqu.baseproject.util.UpdateUtil;
import cn.meiqu.lainmonitor.adapter.PagerHomeAdapter;
import cn.meiqu.lainmonitor.aui.ControlActivity;
import cn.meiqu.lainmonitor.bean.HomePage;

public class MainNewActivity extends BaseActivity {
    String className = getClass().getName();
    String action_getHomePage = className + API.getHomePage;
    private TabLayout mTabL;
    private ViewPager mViewP;
    private PagerHomeAdapter adapter;
    private List<Fragment> fragments = new ArrayList<>();
    ArrayList<HomePage> homePages = new ArrayList<>();
    String titles[] = new String[100];

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 1:
                    requestHomePage();
                    break;
                case 2:
                    break;
            }
        }
    };


    private void assignViews() {
//        Toolbar toolbar = (Toolbar) findViewById(cn.meiqu.baseproject.R.id.toolBar);
//        setSupportActionBar(toolbar);

        Toolbar toolbar = (Toolbar) findViewById(cn.meiqu.baseproject.R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) findViewById(R.id.tv_title)).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tv_title)).setText("莱安监控系统");

        mTabL = (TabLayout) findViewById(R.id.tabL);
        mViewP = (ViewPager) findViewById(R.id.viewP);
    }

    private void initPager() {
        adapter = new PagerHomeAdapter(getSupportFragmentManager(), fragments, titles);
        mViewP.setAdapter(adapter);
        mTabL.setupWithViewPager(mViewP);
        mTabL.setTabTextColors(getResources().getColor(R.color.black3), getResources().getColor(R.color.colorAccent));//设置文本在选中和为选中时候的颜色
        mTabL.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        mTabL.setTabMode(TabLayout.MODE_FIXED);
        //初始化数据
        initData();
    }

    public void initData() {
        homePages.clear();
        fragments.clear();
        String jsonData = SettingDao.getInstance().getHomePagerJson();
        if (!StringUtil.isEmpty(jsonData)) {
            ArrayList<HomePage> temps = new Gson().fromJson(jsonData, new TypeToken<ArrayList<HomePage>>() {
            }.getType());
            homePages.addAll(temps);
            for (int i = 0; i < homePages.size(); i++) {
                fragments.add(MainFragment.newInstance(homePages.get(i).getNumber() + ""));
                titles[i] = homePages.get(i).getName();
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        initTransparent();
        UpdateUtil.checkUpdate(getApplication(), false);
        assignViews();
        initPager();
        //
        initReceiver(new String[]{action_getHomePage});
        showProgressDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestHomePage();
    }

    @Override
    public void initFragment() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return ScreenUtil.onKeyDown(this, event);
    }

    public void requestHomePage() {
        HttpGetController.getInstance().getHomePage(className);
    }

    public void handleHomePage(String data) {
        ArrayList<HomePage> temps = new Gson().fromJson(data, new TypeToken<ArrayList<HomePage>>() {
        }.getType());
        if (!temps.isEmpty()) {
            temps.remove(0);
//            for (HomePage homePage : temps) {
//                if (homePage.getIsShow() != 1) {
//                    temps.remove(homePage);
//                }
//            }
            //  containerId = R.id.frame_fragment;这是这里的fragment的container容器
            //所以这里可以试试按照自己的那个popStack的方法除去当前的fragment
            for(int i=0;i<temps.size();i++){
                if(temps.get(i).getIsShow()!=1){
                    temps.remove(i);
//                    FragmentManager fm = getSupportFragmentManager();
//                    fm.popBackStack();

                }
            }
            SettingDao.getInstance().setHomePagerJson(new Gson().toJson(temps));
//            if (!temps.isEmpty() && temps.size() != homePages.size()) {
//                homePages.clear();
//                homePages.addAll(temps);
//                initData();
//            }
            initData();

        }
    }

    AlertDialog dialog;

    @Override
    public void onHttpHandle(String action, String data) {
        if (getHttpStatus(data)) {
            if (action.equals(action_getHomePage)) {
                handleHomePage(data);
            }
        } else {
            LogUtil.log("fatel", "dddd");
            if (dialog == null || !dialog.isShowing()) {
                dialog = new AlertDialog.Builder(this).setTitle("服务器没响应").setMessage("请检查域名设置是否正确").setPositiveButton("马上去", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jump(IpSettingActivity.class);
                    }
                }).show();
            }
        }

    }

    public void jumpControlActivity(String pId, String cId, String name) {
        Intent intent = new Intent(this, ControlActivity.class);
        intent.putExtra(ControlActivity.extra_pId, pId);
        intent.putExtra(ControlActivity.extra_cId, cId);
        intent.putExtra(ControlActivity.extra_name, name);
        jump(intent);
    }
}
