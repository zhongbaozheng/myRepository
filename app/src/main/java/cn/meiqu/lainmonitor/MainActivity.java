package cn.meiqu.lainmonitor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import cn.meiqu.baseproject.util.UpdateUtil;
import cn.meiqu.lainmonitor.adapter.PagerHomeAdapter;
import cn.meiqu.lainmonitor.aui.ControlActivity;
import cn.meiqu.lainmonitor.untils.AndroidWorkaround;
import cn.meiqu.lainmonitor.untils.VirtualUtil;
import cn.meiqu.lainmonitor.view.NoScrollViewPager;

public class MainActivity extends AppCompatActivity{

    private String[] mTitles = {"主页","我的"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    //禁止滑动
    private NoScrollViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initTransparent();
        assignViews();
        UpdateUtil.checkUpdate(getApplication(), false);
        addFragments();
        setViewPager();

    }

    private void addFragments(){
        mFragments.add(new HomeFragment());
        mFragments.add(new MineFragment());
    }

    private void setViewPager(){

        PagerHomeAdapter tabAdapter = new PagerHomeAdapter(getSupportFragmentManager(),mFragments,mTitles);
        mViewPager.setAdapter(tabAdapter);
        tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.color_green));//设置文本在选中和为选中时候的颜色
        tabLayout.setupWithViewPager(mViewPager);
    }



    public void assignViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.layout_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) findViewById(R.id.tv_title)).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tv_title)).setText("莱安监控系统");

        mViewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout)findViewById(R.id.table);

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


    public void jumpControlActivity(String pId, String cId, String name) {
        Intent intent = new Intent(this, ControlActivity.class);
        intent.putExtra(ControlActivity.extra_pId, pId);
        intent.putExtra(ControlActivity.extra_cId, cId);
        intent.putExtra(ControlActivity.extra_name, name);
        startActivity(intent);
    }

}
