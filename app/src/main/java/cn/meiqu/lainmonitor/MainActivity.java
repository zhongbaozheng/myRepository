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
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import cn.meiqu.baseproject.util.UpdateUtil;
import cn.meiqu.lainmonitor.adapter.PagerHomeAdapter;
import cn.meiqu.lainmonitor.aui.ControlActivity;
import cn.meiqu.lainmonitor.view.NoScrollViewPager;

/**
 * 主要是ViewPager与TableLayout和Fragment的结合使用
 */

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
        assignViews();
        UpdateUtil.checkUpdate(getApplication(), false);
        addFragments();
        setViewPager();
    }

    //主页 和 我的
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

    /**
     * 获取当前PageNumber（如环境监控）  ItemNumber（环境监控下的温湿度模块）
     * 和 每一个class（温湿度模块对应的class文件的名字）文件的名字
     * @param pId
     * @param cId
     * @param name
     */
    public void jumpControlActivity(String pId, String cId, String name) {
        Intent intent = new Intent(this, ControlActivity.class);
        intent.putExtra(ControlActivity.extra_pId, pId);
        intent.putExtra(ControlActivity.extra_cId, cId);
        intent.putExtra(ControlActivity.extra_name, name);
        startActivity(intent);
    }

}
