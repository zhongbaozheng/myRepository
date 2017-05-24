package cn.meiqu.lainmonitor.activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.RecycleDoorMessageAdapter;
import cn.meiqu.lainmonitor.bean.DoorMessageBean;
import cn.meiqu.baseproject.bean.MsgBean;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2017/5/9.
 */

public class DoorControlActivity extends BaseActivity{

    String className = getClass().getName();
    String action_doorMessage = className + API.doorMessageUrl;
    String action_updateDoor = className + API.doorUpdateUrl;
    private ArrayList<DoorMessageBean> mList = new ArrayList<>();
    private PtrClassicFrameLayout mPtrLayout;
    private RecycleDoorMessageAdapter mAdapter;

    private RecyclerView mRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_control);
        initTransparent();
        initTitle("门禁管理");
        initReceiver(new String[]{action_doorMessage,action_updateDoor});
        assignLayout();
        mAdapter = new RecycleDoorMessageAdapter(mList,this,className);
        mRv.setLayoutManager(new LinearLayoutManager(this));
//        mRv.addItemDecoration(new DividerItemDecoration(this));
        mRv.setAdapter(mAdapter);
        mPtrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return !mRv.canScrollVertically(-1);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestDoorMessage();
            }
        });
    }
    private void assignLayout(){
        mPtrLayout = (PtrClassicFrameLayout)findViewById(R.id.ptr_layout);
        mRv = (RecyclerView)findViewById(R.id.doorMessage_rv);
    }

    @Override
    public void onHttpHandle(String action, String data) {
        Log.e("doorMessage",data);
        if(action.equals(action_doorMessage)){
            handleMessage(data);
        }
        if(action.equals(action_updateDoor)){
            showMsg(data);
        }
    }

    private void handleMessage(String data){
        ArrayList<DoorMessageBean> temps = new Gson().fromJson(data,new TypeToken<ArrayList<DoorMessageBean>>(){}.getType());
        if(temps!=null){
            mList.clear();
            mList.addAll(temps);
            Log.e("mList",mList.size()+"");
            mAdapter.notifyDataSetChanged();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPtrLayout.refreshComplete();
                }
            },1000);

        }

    }

    private void requestDoorMessage(){
        HttpGetController.getInstance().getDoorMessage(className);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestDoorMessage();
    }

    @Override
    public void initFragment() {

    }


}
