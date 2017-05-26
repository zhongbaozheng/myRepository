package cn.meiqu.lainmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseRecycle.BaseOnRecycleClickListener;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.baseUi.BaseFragment;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecycleHomeAdapter;
import cn.meiqu.lainmonitor.bean.HomePage;

/**
 * Created by Fatel on 16-5-24.
 */
public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseOnRecycleClickListener {
    String className = getClass().getName() + Math.random() * 10000;
    String action_getHomeChildPage = className + API.getHomeChildPage;
    String numberId = "";
    ArrayList<HomePage> homePages = new ArrayList<>();
    RecycleHomeAdapter adapter;

    public static MainFragment newInstance(String numberId) {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("numberId", numberId);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        numberId = getArguments().getString("numberId");
        super.onCreate(savedInstanceState);
    }

    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRecycleV;

    private void assignViews() {
        setSwipeRefresh(R.id.swipe, this);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mRecycleV = (RecyclerView) findViewById(R.id.recycleV);
        adapter = new RecycleHomeAdapter(getActivity(), homePages);
        mRecycleV.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecycleV.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initReceiver(new String[]{action_getHomeChildPage});
        if (contain == null) {
            contain = inflater.inflate(R.layout.f_main, null);
            assignViews();
            setSwipeRefreshing(true);
        }
        return contain;
    }

    @Override
    public void onResume() {
        requestPage();
        super.onResume();
    }

    public void requestPage() {
        HttpGetController.getInstance().getHomeChildPage(numberId, className);
    }

    public void handleHomePage(String data) {
        ArrayList<HomePage> temps = new Gson().fromJson(data, new TypeToken<ArrayList<HomePage>>() {
        }.getType());
        if (!temps.isEmpty()) {
            for (HomePage homePage : temps) {
                if (homePage.getIsShow() != 1) {
                    temps.remove(homePage);
                }
            }
            if (!temps.isEmpty() && temps.size() != homePages.size()) {
                homePages.clear();
                homePages.addAll(temps);
                if (numberId.equals("6")) {
                    homePages.add(new HomePage("域名设置", -1));
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onHttpHandle(String action, String data) {
        if (getHttpStatus(data)) {
            if (action.equals(action_getHomeChildPage)) {
                handleHomePage(data);
            }
        }
    }

    @Override
    public void onRefresh() {
        requestPage();
//        ((MainNewActivity)getActivity()).mHandler.sendEmptyMessage(1);
    }

    @Override
    public void OnRecycleItemClick(int position) {
        HomePage homePage = homePages.get(position);
        if (homePage.getNumber() == -1) {
            ((BaseActivity) getActivity()).jump(IpSettingActivity.class);
        } else {
            //跳转到对应的界面id这里对应的是numberId，就是每一个界面的id,当点击的话就传入一个homePage.getId()，并跳到对应的界面
//            ((MainNewActivity) getActivity()).jumpControlActivity(numberId, homePage.getId() + "", homePage.getName());
            ((MainActivity) getActivity()).jumpControlActivity(numberId, homePage.getId() + "", homePage.getName());
        }
    }
}
