package cn.meiqu.lainmonitor.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.RecycleSelectionAdapter;
import cn.meiqu.baseproject.bean.MsgBean;
import cn.meiqu.lainmonitor.bean.SelectionBean;
import cn.meiqu.lainmonitor.untils.SpaceItemDecoration;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2017/5/9.
 */

public class SelectionActivity extends BaseActivity {

    String className = getClass().getName();
    String action_selection = className + API.queSectionUrl;
    String action_updateSelection = className+API.updateSelectionUrl;

    ArrayList<SelectionBean> mList = new ArrayList<>();

    private PtrClassicFrameLayout mPtrLayout;
    private RecyclerView mRv;

    private RecycleSelectionAdapter mAdapter;

    @Override
    public void onHttpHandle(String action, String data) {

        if(action.equals(action_selection)){
            ArrayList<SelectionBean> temps = new Gson().fromJson(data,new TypeToken<ArrayList<SelectionBean>>(){}.getType());
            if(temps!=null){
                mList.clear();
                mList.addAll(temps);
                mAdapter.notifyDataSetChanged();
                //adapter
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       mPtrLayout.refreshComplete();
                    }
                },1000);
            }
        }

        if(action.equals(action_updateSelection)){
            showMsg(data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_control);
        initTransparent();
        initTitle("部门管理");
        initViews();
        initReceiver(new String[]{action_selection,action_updateSelection});

        mAdapter = new RecycleSelectionAdapter(mList,this,className);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new SpaceItemDecoration(20));
        mRv.setAdapter(mAdapter);

        mPtrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return !mRv.canScrollVertically(-1);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestSelection();
            }
        });
    }

    private void initViews(){
        mPtrLayout = (PtrClassicFrameLayout)findViewById(R.id.ptr_layout);
        mRv = (RecyclerView)findViewById(R.id.selection_rv);
    }

    private void requestSelection(){
        HttpGetController.getInstance().querySelection(className);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestSelection();
    }

    @Override
    public void initFragment() {

    }
}
