package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.BitSet;

import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.AirAcurrateBean;

/**
 * Created by Administrator on 2017/4/28.
 */

public class RecylceAccurateAirAdapter extends BaseRecycleAdapter{

    private Context mContext;
    private ArrayList<AirAcurrateBean> mAirAcurrateBeanList;
    private String mClassName;

    public RecylceAccurateAirAdapter(Context context,ArrayList<AirAcurrateBean> list,String className){
        mContext = context;
        mAirAcurrateBeanList = list;
        mClassName = className;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContext,R.layout.recycle_accurate,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder)holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return mAirAcurrateBeanList.size();
    }

    //Holder
    public class Holder extends BaseHolder{

        private TextView mIdName;//设备名称
        private TextView mTempHumity;//温湿度
        //开关机状态
        private TextView mOpenTv;
        private TextView mCloseTv;

        //
        private TextView mYsuj1Tv;
        private ToggleButton mYsuj1State;
        private TextView mWfj1Tv;
        private ToggleButton mWfj1State;
        private TextView mYsj2Tv;
        private ToggleButton mYsj2State;
        private TextView mCsfTv;
        private ToggleButton mCsfState;
        private TextView mNfj2Tv;
        private ToggleButton mNfj2State;
        private TextView mRqptfTv;
        private ToggleButton mRqptfState;
        private TextView mJsqpsfTv;
        private ToggleButton mJsqpsfState;
        private TextView mJsqjsfTv;
        private ToggleButton mJsqjsfState;
        private TextView mYxmTv;
        private ToggleButton mYxmState;
        private TextView mKgjTv;
        private ToggleButton mKgjState;

        //报警
        private TextView mJsqddlTv;
        private ToggleButton mJsqddlState;
        private TextView mJsqgswTv;
        private ToggleButton mJsqgswState;
        private TextView mYsj2dyTv;
        private ToggleButton mYsj2dyState;
        private TextView mYsj2gyTv;
        private ToggleButton mYsj2gyState;
        private TextView mNfj2gzTv;
        private ToggleButton mNfj2gzState;
        private TextView mHfttTv;
        private ToggleButton mHfttState;
        private TextView mTxTv;
        private ToggleButton mTxState;


        public Holder(View itemView) {
            super(itemView);
        }

        @Override
        public void assignViews() {
            mYsj2gyTv = (TextView)findViewById(R.id.ysj2gy_alarm_state);
            mYsj2gyState = (ToggleButton)findViewById(R.id.ysj2gy_alarm_state_show);
            mIdName = (TextView)findViewById(R.id.ra_air_name);
            mTempHumity = (TextView)findViewById(R.id.ra_air_temperature_and_humidity);
            mOpenTv = (TextView) findViewById(R.id.ra_air_open_tv);
            mCloseTv = (TextView) findViewById(R.id.ra_air_close_tv);
            mYsuj1Tv = (TextView)findViewById(R.id.ysj1_state);
            mYsuj1State = (ToggleButton)findViewById(R.id.ysj1_state_show);
            mWfj1Tv = (TextView)findViewById(R.id.wfj1_state);
            mWfj1State =  (ToggleButton)findViewById(R.id.wfj1_state_show);
            mYsj2Tv = (TextView)findViewById(R.id.ysj2_state);
            mYsj2State = (ToggleButton)findViewById(R.id.ysj2_state_show);
            mCsfTv = (TextView)findViewById(R.id.csf2_state);
            mCsfState = (ToggleButton)findViewById(R.id.csf2_state_show);
            mNfj2Tv = (TextView)findViewById(R.id.nfj2_state);
            mNfj2State = (ToggleButton)findViewById(R.id.nfj2_state_show);
            mJsqpsfTv = (TextView)findViewById(R.id.jsqpsf_state);
            mJsqpsfState = (ToggleButton)findViewById(R.id.jsqpsf_state_show);
            mJsqjsfTv = (TextView)findViewById(R.id.jsqjsf_state);
            mJsqjsfState = (ToggleButton)findViewById(R.id.jsqjsf_state_show);
            mYxmTv = (TextView)findViewById(R.id.yxm_state);
            mYxmState = (ToggleButton)findViewById(R.id.yxm_state_show);
            mKgjTv = (TextView)findViewById(R.id.kgj_state);
            mKgjState = (ToggleButton)findViewById(R.id.kgj_state_show);
            mJsqddlTv = (TextView)findViewById(R.id.jsqddl_alarm_state);
            mJsqddlState = (ToggleButton)findViewById(R.id.jsqddl_alarm_state_show);
            mJsqgswTv = (TextView)findViewById(R.id.jsqgsw_alarm_state);
            mJsqgswState = (ToggleButton)findViewById(R.id.jsqgsw_alarm_state_show);
            mYsj2dyTv = (TextView)findViewById(R.id.ysj2dy_alarm_state);
            mYsj2dyState = (ToggleButton)findViewById(R.id.ysj2dy_alarm_state_show);
            mHfttTv = (TextView)findViewById(R.id.hftt_alarm_state);
            mHfttState = (ToggleButton)findViewById(R.id.hftt_alarm_state_show);
            mNfj2gzTv = (TextView)findViewById(R.id.nfj2gz_alarm_state);
            mNfj2gzState = (ToggleButton)findViewById(R.id.nfj2gz_state_alarm_show);
            mTxTv = (TextView)findViewById(R.id.tx_state);
            mTxState = (ToggleButton)findViewById(R.id.tx_state_show);
            mRqptfState = (ToggleButton)findViewById(R.id.rqp_state_show);
            mRqptfTv = (TextView)findViewById(R.id.rqp_state);
        }


        private void setState(final AirAcurrateBean airAcurrateBean){
            mIdName.setText(airAcurrateBean.name);
            mTempHumity.setText("      温度:  "+airAcurrateBean.temp+"°C                           湿度: "+airAcurrateBean.hum+"%");
            mRqptfState.setChecked(airAcurrateBean.rqptState == 1);
            mYsj2gyState.setChecked(airAcurrateBean.ysj2gyAlarm == 1);
            mYsuj1State.setChecked(airAcurrateBean.ysj1State == 1);
            mWfj1State.setChecked(airAcurrateBean.wfj1State == 1);
            mYsj2State.setChecked(airAcurrateBean.ysj2State == 1);
            mCsfState.setChecked(airAcurrateBean.csfState == 1);
            mNfj2State.setChecked(airAcurrateBean.nfj2State == 1);
            mJsqpsfState.setChecked(airAcurrateBean.jsqpsfState == 1);
            mJsqjsfState.setChecked(airAcurrateBean.jsqjsfState == 1);
            mYxmState.setChecked(airAcurrateBean.yxmsState == 1);
            mJsqddlState.setChecked(airAcurrateBean.jsqddlAlarm == 1);
            mJsqgswState.setChecked(airAcurrateBean.jsqgswAlarm == 1);
            mYsj2dyState.setChecked(airAcurrateBean.ysj2dyAlarm == 1);
            mHfttState.setChecked(airAcurrateBean.hfttAlarm == 1);
            mNfj2gzState.setChecked(airAcurrateBean.nfj2gzAlarm == 1);
            mTxState.setChecked(airAcurrateBean.txgzAlarm == 1);

            //mKgjStat
            if(airAcurrateBean.kgjState == 1){
                mKgjState.setChecked(true);
                mOpenTv.setBackgroundColor(mOpenTv.getResources().getColor(R.color.color_green));
                mCloseTv.setBackgroundColor(mOpenTv.getResources().getColor(R.color.color_gravy));
            }else{
                mKgjState.setChecked(false);
                mOpenTv.setBackgroundColor(mOpenTv.getResources().getColor(R.color.color_gravy));
                mCloseTv.setBackgroundColor(mOpenTv.getResources().getColor(R.color.color_green));
            }

            mCloseTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //将发送控制请求
//                    Toast.makeText(mContext,"关",Toast.LENGTH_SHORT).show();
                    /**
                     * 精密空调开机请求
                     http://localhost:8090/ktr-mrms/ opencracJson.html？Id=1

                     精密空调关机机请求
                     http://localhost:8090/ktr-mrms/ closecracJson.html？Id=1
                     */
                    if(airAcurrateBean.kgjState == 1){
                        //发送关
                        HttpGetController.getInstance().closeAir(mClassName,1);
                    }else{
                        //发送开
                        HttpGetController.getInstance().openAir(mClassName,1);
                    }
                }
            });
            mOpenTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(mContext,"开",Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void instanceView(int position) {
            AirAcurrateBean mAirAcurrateBean = mAirAcurrateBeanList.get(position);
            setState(mAirAcurrateBean);
//            mIdName.setText(mAirAcurrateBean.name);
//            mTempHumity.setText("  温度:  "+mAirAcurrateBean.temp+"                                                湿度: "+mAirAcurrateBean.hum);
//            //判断状态设置，并设置背景色
//            if(mAirAcurrateBean.kgjState == 1){
//                mOpenTv.setBackgroundColor(mOpenTv.getResources().getColor(R.color.colorPrimary));
//                mCloseTv.setBackgroundColor(mOpenTv.getResources().getColor(R.color.colorAccent));
//            }else{
//                mOpenTv.setBackgroundColor(mOpenTv.getResources().getColor(R.color.colorAccent));
//                mCloseTv.setBackgroundColor(mOpenTv.getResources().getColor(R.color.colorPrimary));
//            }
//            mCloseTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(mContext,"关",Toast.LENGTH_SHORT).show();
//                }
//            });
//            mOpenTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(mContext,"开",Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
