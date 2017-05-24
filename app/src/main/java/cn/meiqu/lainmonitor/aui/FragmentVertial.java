package cn.meiqu.lainmonitor.aui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.meiqu.baseproject.baseUi.BaseFragment;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.untils.DividerItemDecoration;
import cn.meiqu.lainmonitor.untils.SpaceItemDecoration;

/**
 * Created by Administrator on 2017/5/2.
 */

public abstract class FragmentVertial extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public String className = getClass().getName() + Math.random() * 10000;
    private SwipeRefreshLayout mSwipe;
    public RecyclerView mRecycleV;

    public abstract RecyclerView.Adapter getAdapter();

    public abstract String getAction();

    public String[] getActions() {
        return null;
    }

    public abstract void requestData();

    public abstract void handleData(String data);

    //初始化
    public void initView() {

    }

    private void assignViews() {
        setSwipeRefresh(R.id.swipe, this);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mRecycleV = (RecyclerView) findViewById(R.id.recycleV);
        mRecycleV.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.item_space)));
        mRecycleV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleV.setAdapter(getAdapter());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActions() == null) {
            initReceiver(new String[]{getAction()});
        } else {
            initReceiver(getActions());
        }
        if (contain == null) {
            contain = inflater.inflate(R.layout.f_real, null);
            assignViews();
            initView();
            setSwipeRefreshing(true);
        }
        return contain;
    }

    @Override
    public void onResume() {
        requestData();
        super.onResume();
    }

    @Override
    public void onHttpHandle(String action, String data) {
        if (getHttpStatus(data)) {
            if (action.equals(getAction())) {
                handleData(data);
            }
        }
    }

    @Override
    public void onRefresh() {
        requestData();
    }
}
