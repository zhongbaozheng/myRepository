package cn.meiqu.lainmonitor.hkvideo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.lainmonitor.MineFragment;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.PagerHomeAdapter;

/**
 * Created by Administrator on 2017/7/13.
 */

public class VideoActivity extends BaseActivity {



    private String[] videoOptions = {"实时","回放"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    public void onHttpHandle(String action, String data) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        assignViews();
        AddFragements();
        setViewPager();
    }

    @Override
    public void initFragment() {

    }


    private void AddFragements(){
        //两个Fragments
        mFragments.add(new MineFragment());

    }


    public void assignViews(){
        initTitle("视频监控");
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout)findViewById(R.id.table);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setViewPager(){

        PagerHomeAdapter tabAdapter = new PagerHomeAdapter(getSupportFragmentManager(),mFragments,videoOptions);
        mViewPager.setAdapter(tabAdapter);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.black));//设置文本在选中和为选中时候的颜色
        tabLayout.setupWithViewPager(mViewPager);
    }

}
