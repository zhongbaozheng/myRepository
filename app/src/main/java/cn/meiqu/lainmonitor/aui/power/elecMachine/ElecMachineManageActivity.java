package cn.meiqu.lainmonitor.aui.power.elecMachine;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.baseproject.util.Config;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.Baterry;
import cn.meiqu.lainmonitor.bean.CommitBatBean;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/6/5.
 */

public class ElecMachineManageActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.battery_scrollView)
    NestedScrollView mScroll;
    @BindView(R.id.ptr_view)
    PtrClassicFrameLayout mPtr;

    @BindView(R.id.tv_1)
    TextView mTv1;
    @BindView(R.id.tv_2)
    TextView mTv2;
    @BindView(R.id.tv_3)
    TextView mTv3;

    @BindView(R.id.tv_4)
    TextView mTv4;
    @BindView(R.id.tv_5)
    TextView mTv5;
    @BindView(R.id.tv_6)
    TextView mTv6;
    @BindView(R.id.tv_7)
    TextView mTv7;
    @BindView(R.id.tv_8)
    TextView mTv8;
    @BindView(R.id.tv_9)
    TextView mTv9;

    @BindView(R.id.tv_10)
    TextView mTv10;
    @BindView(R.id.tv_11)
    TextView mTv11;
    @BindView(R.id.tv_12)
    TextView mTv12;
    @BindView(R.id.tv_13)
    TextView mTv13;
    @BindView(R.id.tv_14)
    TextView mTv14;
    @BindView(R.id.tv_15)
    TextView mTv15;

    @BindView(R.id.tv_16)
    TextView mTv16;
    @BindView(R.id.tv_17)
    TextView mTv17;
    @BindView(R.id.tv_18)
    TextView mTv18;
    @BindView(R.id.tv_19)
    TextView mTv19;
    @BindView(R.id.tv_20)
    TextView mTv20;
    @BindView(R.id.tv_21)
    TextView mTv21;

    @BindView(R.id.tv_22)
    TextView mTv22;
    @BindView(R.id.tv_23)
    TextView mTv23;
    @BindView(R.id.tv_24)
    TextView mTv24;

    @BindView(R.id.ed_1)
    EditText mEd1;
    @BindView(R.id.ed_2)
    EditText mEd2;
    @BindView(R.id.ed_3)
    EditText mEd3;

    @BindView(R.id.ed_4_1)
    EditText mEd4_1;
    @BindView(R.id.ed_4_2)
    EditText mEd4_2;
    @BindView(R.id.ed_5_1)
    EditText mEd5_1;
    @BindView(R.id.ed_5_2)
    EditText mEd5_2;
    @BindView(R.id.ed_6_1)
    EditText mEd6_1;
    @BindView(R.id.ed_6_2)
    EditText mEd6_2;
    @BindView(R.id.ed_7_1)
    EditText mEd7_1;
    @BindView(R.id.ed_7_2)
    EditText mEd7_2;
    @BindView(R.id.ed_8_1)
    EditText mEd8_1;
    @BindView(R.id.ed_8_2)
    EditText mEd8_2;
    @BindView(R.id.ed_9_1)
    EditText mEd9_1;
    @BindView(R.id.ed_9_2)
    EditText mEd9_2;

    @BindView(R.id.ed_10_1)
    EditText mEd10_1;
    @BindView(R.id.ed_10_2)
    EditText mEd10_2;
    @BindView(R.id.ed_11_1)
    EditText mEd11_1;
    @BindView(R.id.ed_11_2)
    EditText mEd11_2;
    @BindView(R.id.ed_12_1)
    EditText mEd12_1;
    @BindView(R.id.ed_12_2)
    EditText mEd12_2;
    @BindView(R.id.ed_13_1)
    EditText mEd13_1;
    @BindView(R.id.ed_13_2)
    EditText mEd13_2;
    @BindView(R.id.ed_14_1)
    EditText mEd14_1;
    @BindView(R.id.ed_14_2)
    EditText mEd14_2;
    @BindView(R.id.ed_15_1)
    EditText mEd15_1;
    @BindView(R.id.ed_15_2)
    EditText mEd15_2;

    @BindView(R.id.ed_16_1)
    EditText mEd16_1;
    @BindView(R.id.ed_16_2)
    EditText mEd16_2;
    @BindView(R.id.ed_17_1)
    EditText mEd17_1;
    @BindView(R.id.ed_17_2)
    EditText mEd17_2;
    @BindView(R.id.ed_18_1)
    EditText mEd18_1;
    @BindView(R.id.ed_18_2)
    EditText mEd18_2;
    @BindView(R.id.ed_19_1)
    EditText mEd19_1;
    @BindView(R.id.ed_19_2)
    EditText mEd19_2;
    @BindView(R.id.ed_20_1)
    EditText mEd20_1;
    @BindView(R.id.ed_20_2)
    EditText mEd20_2;
    @BindView(R.id.ed_21_1)
    EditText mEd21_1;
    @BindView(R.id.ed_21_2)
    EditText mEd21_2;

    @BindView(R.id.ed_22_1)
    EditText mEd22_1;
    @BindView(R.id.ed_22_2)
    EditText mEd22_2;
    @BindView(R.id.ed_23_1)
    EditText mEd23_1;
    @BindView(R.id.ed_23_2)
    EditText mEd23_2;
    @BindView(R.id.ed_24_1)
    EditText mEd24_1;
    @BindView(R.id.ed_24_2)
    EditText mEd24_2;

    @BindView(R.id.commit_tv)
    TextView mCommitTv;
    @BindView(R.id.delete_tv)
    TextView mDeleteTv;


    private ArrayList<Baterry> mList = new ArrayList<>();
    Baterry bean;



    String className = getClass().getName();
    String action_getData = className + API.batteryUrl;
    String action_delete = className + API.deleteElctroMachineUrl;
    String action_update = className + API.updateElctroMachineUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_battery);
        ButterKnife.bind(this);
        initTitle("设备管理");
        initReceiver(new String[]{action_getData,action_delete,action_update});
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScroll, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestData();
            }
        });
        mCommitTv.setOnClickListener(this);
        mDeleteTv.setOnClickListener(this);

    }

    @Override
    public void onHttpHandle(String action, String data) {
        if(action.equals(action_getData)){
            //获取
            ArrayList<Baterry> temps = new Gson().fromJson(data,new TypeToken<ArrayList<Baterry>>(){}.getType());
            if(temps!=null) {
                mList.clear();
                mList.addAll(temps);
                handlerMessage();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPtr.refreshComplete();
                }
            },1000);
        }

        if(action.equals(action_delete)){
            showMsg(data);
            finish();
        }
        if(action.equals(action_update)){
            showMsgNotArray(data);
        }
    }

    @Override
    public void initFragment() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData(){
        HttpGetController.getInstance().getBaterry(className);
    }

    private void deleteElctroMachine(String className,String id,String ip,String address){
        HttpGetController.getInstance().deleteBattery(className,address,id,ip);
    }

    private void upateElctroMachine(String className,String jsonStr){
        HttpGetController.getInstance().updateBattery(className,jsonStr);
    }

    private void handlerMessage(){

        int postion = Config.getInt("postion");
        bean = mList.get(postion);
        mEd1.setText(bean.emmAddress+"");
        mEd2.setText(bean.deviceLocationPojo.dlName+"");
        mEd3.setText(bean.emmName+"");

        mEd4_1.setText(bean.maxAvol+"");
        mEd4_2.setText(bean.minAvol+"");
        mEd5_1.setText(bean.maxAcur+"");
        mEd5_2.setText(bean.minAcur+"");
        mEd6_1.setText(bean.maxABvol+"");
        mEd6_2.setText(bean.minABvol+"");
        mEd7_1.setText(bean.maxApap+"");
        mEd7_2.setText(bean.minApap+"");
        mEd8_1.setText(bean.maxAprp+"");
        mEd8_2.setText(bean.minAprp+"");
        mEd9_1.setText(bean.maxAppf+"");
        mEd9_2.setText(bean.minAppf+"");

        mEd10_1.setText(bean.maxBvol+"");
        mEd10_2.setText(bean.minBvol+"");
        mEd11_1.setText(bean.maxBcur+"");
        mEd11_2.setText(bean.minBcur+"");
        mEd12_1.setText(bean.maxBCvol+"");
        mEd12_2.setText(bean.minBCvol+"");
        mEd13_1.setText(bean.maxBpap+"");
        mEd13_2.setText(bean.minBpap+"");
        mEd14_1.setText(bean.maxBprp+"");
        mEd14_2.setText(bean.minBprp+"");
        mEd15_1.setText(bean.maxBppf+"");
        mEd15_2.setText(bean.minBppf+"");

        mEd16_1.setText(bean.maxCvol+"");
        mEd16_2.setText(bean.minCvol+"");
        mEd17_1.setText(bean.maxCcur+"");
        mEd17_2.setText(bean.minCcur+"");
        mEd18_1.setText(bean.maxCAvol+"");
        mEd18_2.setText(bean.minCAvol+"");
        mEd19_1.setText(bean.maxCpap+"");
        mEd19_2.setText(bean.minCpap+"");
        mEd20_1.setText(bean.maxCprp+"");
        mEd20_2.setText(bean.minCprp+"");
        mEd21_1.setText(bean.maxCppf+"");
        mEd21_2.setText(bean.minCppf+"");

        mEd22_1.setText(bean.maxTpap+"");
        mEd22_2.setText(bean.minTpap+"");
        mEd23_1.setText(bean.maxTprp+"");
        mEd23_2.setText(bean.minTprp+"");
        mEd24_1.setText(bean.maxTppf+"");
        mEd24_2.setText(bean.minTppf+"");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.commit_tv:

                CommitBatBean cbean = new CommitBatBean();
                cbean.address = mEd1.getText().toString();
                cbean.id = mEd2.getText().toString();
                cbean.name = mEd3.getText().toString();

                cbean.maxAvol = mEd4_1.getText().toString();
                cbean.minAvol = mEd4_2.getText().toString();
                cbean.maxAcur = mEd5_1.getText().toString();
                cbean.minAcur = mEd5_2.getText().toString();
                cbean.maxABvol = mEd6_1.getText().toString();
                cbean.minABvol = mEd6_2.getText().toString();
                cbean.maxApap = mEd7_1.getText().toString();
                cbean.minApap = mEd7_2.getText().toString();
                cbean.maxAprp = mEd8_1.getText().toString();
                cbean.minAprp = mEd8_2.getText().toString();
                cbean.maxAppf = mEd9_1.getText().toString();
                cbean.minAppf = mEd9_2.getText().toString();
                cbean.maxBvol = mEd10_1.getText().toString();
                cbean.minBvol = mEd10_2.getText().toString();
                cbean.maxBcur = mEd11_1.getText().toString();
                cbean.minBcur = mEd11_2.getText().toString();
                cbean.maxBCvol = mEd12_1.getText().toString();
                cbean.minBCvol = mEd12_2.getText().toString();
                cbean.maxBpap = mEd13_1.getText().toString();
                cbean.minBpap = mEd13_2.getText().toString();
                cbean.maxBprp = mEd14_1.getText().toString();
                cbean.minBprp = mEd14_2.getText().toString();
                cbean.maxBppf = mEd15_1.getText().toString();
                cbean.minBppf = mEd15_2.getText().toString();
                cbean.maxCvol = mEd16_1.getText().toString();
                cbean.minCvol = mEd16_2.getText().toString();
                cbean.maxCcur = mEd17_1.getText().toString();
                cbean.minCcur = mEd17_2.getText().toString();
                cbean.maxCAvol = mEd18_1.getText().toString();
                cbean.minCAvol = mEd18_2.getText().toString();
                cbean.maxCpap = mEd19_1.getText().toString();
                cbean.minCpap = mEd19_2.getText().toString();
                cbean.maxCprp = mEd20_1.getText().toString();
                cbean.minCprp = mEd20_2.getText().toString();
                cbean.maxCppf = mEd21_1.getText().toString();
                cbean.minCppf = mEd21_2.getText().toString();
                cbean.maxTpap = mEd22_1.getText().toString();
                cbean.minTpap= mEd22_2.getText().toString();
                cbean.maxTprp = mEd23_1.getText().toString();
                cbean.minTprp = mEd23_2.getText().toString();
                cbean.maxTppf = mEd24_1.getText().toString();
                cbean.minTppf = mEd24_2.getText().toString();

                Gson gson = new Gson();
                String batteryStr = gson.toJson(cbean);

                try {
                    batteryStr = URLEncoder.encode(batteryStr,"UTF-8");
                }catch (Exception e){
                    e.printStackTrace();
                }
                upateElctroMachine(className,batteryStr);

                break;
            case R.id.delete_tv:
                deleteElctroMachine(className,bean.emmId+"",bean.ip+"",bean.emmAddress+"");
                break;
        }
    }
}
