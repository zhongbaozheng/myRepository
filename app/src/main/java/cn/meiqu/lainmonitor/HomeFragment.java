package cn.meiqu.lainmonitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseUi.BaseFragment;
import cn.meiqu.baseproject.dao.SettingDao;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.baseproject.util.LogUtil;
import cn.meiqu.baseproject.util.StringUtil;
import cn.meiqu.baseproject.util.UpdateUtil;
import cn.meiqu.lainmonitor.adapter.PagerHomeAdapter;
import cn.meiqu.lainmonitor.aui.ControlActivity;
import cn.meiqu.lainmonitor.bean.HomePage;
import cn.meiqu.lainmonitor.untils.AndroidBug54971Workaround;
import cn.meiqu.lainmonitor.view.ShapeIndicatorView;

/**
 * Created by Administrator on 2017/5/26.
 */

public class HomeFragment extends BaseFragment {


    String className = getClass().getName();
    String action_getHomePage = className + API.getHomePage;
    private TabLayout mTabL;
    private ViewPager mViewP;
    private PagerHomeAdapter adapter;
    private List<Fragment> fragments = new ArrayList<>();
    ArrayList<HomePage> homePages = new ArrayList<>();
    String titles[] = new String[100];
    private View homeView;
    AlertDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       homeView = inflater.inflate(R.layout.home_fragment,container,false);
        AndroidBug54971Workaround.assistActivity(homeView.findViewById(R.id.viewP));
        assignViews();
        initPager();
        initReceiver(new String[]{action_getHomePage});
        return homeView;
    }

    @Override
    public void onHttpHandle(String action, String data) {
        if (getHttpStatus(data)) {
            if (action.equals(action_getHomePage)) {
                handleHomePage(data);
            }
        } else {
            LogUtil.log("fatel", "dddd");
            if (dialog == null || !dialog.isShowing()) {
                dialog = new AlertDialog.Builder(getActivity()).setTitle("服务器没响应").setMessage("请检查域名设置是否正确").setPositiveButton("马上去", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(),IpSettingActivity.class));
                    }
                }).show();
            }
        }
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

    private void assignViews() {

        mTabL = (TabLayout) homeView.findViewById(R.id.tabL);
        mViewP = (ViewPager) homeView.findViewById(R.id.viewP);
    }


    private void initPager() {
        adapter = new PagerHomeAdapter(getFragmentManager(), fragments, titles);
        mViewP.setAdapter(adapter);
        mTabL.setupWithViewPager(mViewP);
        mTabL.setTabTextColors(getResources().getColor(R.color.color_green), getResources().getColor(R.color.colorAccent));//设置文本在选中和为选中时候的颜色
        mTabL.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));//设置指示器的颜色
        mTabL.setTabMode(TabLayout.MODE_SCROLLABLE);
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
    public void onResume() {
        super.onResume();
        requestHomePage();
    }

    public void requestHomePage() {
        HttpGetController.getInstance().getHomePage(className);
    }


    public void jumpControlActivity(String pId, String cId, String name) {
        Intent intent = new Intent(getActivity(), ControlActivity.class);
        intent.putExtra(ControlActivity.extra_pId, pId);
        intent.putExtra(ControlActivity.extra_cId, cId);
        intent.putExtra(ControlActivity.extra_name, name);
        startActivity(intent);
    }




}
