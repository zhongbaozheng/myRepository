package cn.meiqu.lainmonitor.aui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseUi.BaseFragment;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.baseproject.util.ScreenUtil;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.PagerHomeAdapter;
import cn.meiqu.lainmonitor.bean.ThirdPage;

/**
 * Created by zhongbao on 16-5-24.
 */
public abstract class FragmentControl extends BaseFragment {
    String className = getClass().getName();
    String action_getPage = className + API.getHomeChildThirdPage;
    public TabLayout mTabL;
    private ViewPager mViewP;
    private PagerHomeAdapter adapter;
    public List<Fragment> fragments = new ArrayList<>();
    private ArrayList<ThirdPage> thirdPages = new ArrayList<>();
    String titles[] = new String[100];
    public static String number1 = "";
    public static String number2 = "";

    private void assignViews() {
        mTabL = (TabLayout) findViewById(R.id.tabL);
        mViewP = (ViewPager) findViewById(R.id.viewP);
    }

    private void initPager() {
        //如果是fragment的嵌套的话，那么这里要采用getChildFragmentManager（）
        adapter = new PagerHomeAdapter(getChildFragmentManager(), fragments, titles);
        mViewP.setAdapter(adapter);
        mTabL.setupWithViewPager(mViewP);
        mTabL.setTabTextColors(getResources().getColor(R.color.black3), getResources().getColor(R.color.colorAccent));//设置文本在选中和为选中时候的颜色
        mTabL.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        mTabL.setTabMode(TabLayout.MODE_FIXED);
        //
    }

    public void initData(String data) {
        thirdPages.clear();
        fragments.clear();
        ArrayList<ThirdPage> temps = new Gson().fromJson(data, new TypeToken<ArrayList<ThirdPage>>() {
        }.getType());
        thirdPages.addAll(temps);
        for (int i = 0; i < thirdPages.size(); i++) {
//            fragments.add(new FragmentTemp());
            titles[i] = thirdPages.get(i).getName();
        }

        initFragments(fragments, thirdPages);

        if (!thirdPages.isEmpty()) {
            View top = findViewById(R.id.lin_top);
            top.setVisibility(View.VISIBLE);
            top.setTranslationY(-ScreenUtil.dip2px(getActivity(), 40));
            top.animate().translationY(0).setDuration(500).start();
        }
        adapter.notifyDataSetChanged();
    }

    public abstract void initFragments(List<Fragment> fragments, ArrayList<ThirdPage> thirdPages);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initReceiver(new String[]{action_getPage});
        if (contain == null) {
            contain = inflater.inflate(R.layout.f_control, null);
            assignViews();
            initPager();
            requestPage();
        }
        return contain;
    }

    public void requestPage() {
        showProgressDialog();
        HttpGetController.getInstance().getHomeChildThirdPage(number1, number2, className);
    }

    public void handlePage(String data) {
        initData(data);
    }

    @Override
    public void onHttpHandle(String action, String data) {
        if (getHttpStatus(data)) {
            if (action.equals(action_getPage)) {
                handlePage(data);
            }
        }

    }
}
