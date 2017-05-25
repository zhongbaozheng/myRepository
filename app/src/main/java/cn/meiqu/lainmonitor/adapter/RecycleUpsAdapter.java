package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.UPSBean;

/**
 * Created by Administrator on 2017/5/2.
 */

public class RecycleUpsAdapter extends BaseRecycleAdapter {

    private Context mContext;
    private ArrayList<UPSBean> mUpsBeanList;

    public RecycleUpsAdapter(Context context,ArrayList<UPSBean> upsBeanList){
        mContext = context;
        mUpsBeanList = upsBeanList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContext,R.layout.re_ups,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder)holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return mUpsBeanList.size();
    }

    class Holder extends BaseHolder{
        private TextView mUpsName;
        private TextView triphaseInputTv;
        private TextView triphaseOutputTv;
        private TextView mainTv;
        //state
        private TextView mUpsbytActive;
        private TextView mBatVol;
        private TextView mUpsType;
        private TextView mUpsTest;
        private TextView mUpsIntvolault;
        private TextView mUpsRun;

        @Override
        public void assignViews() {
            mUpsName = (TextView)findViewById(R.id.ups_name);
            triphaseInputTv = (TextView) findViewById(R.id.triphase_input_tv);
            triphaseOutputTv = (TextView) findViewById(R.id.triphase_output_tv);
            mainTv = (TextView) findViewById(R.id.main_tv);

            mUpsbytActive = (TextView)findViewById(R.id.ups_bypact_state);
            mBatVol = (TextView)findViewById(R.id.ups_batvollow_state);
            mUpsType = (TextView)findViewById(R.id.ups_type_state);
            mUpsIntvolault = (TextView)findViewById(R.id.ups_intvolault_state);
            mUpsRun = (TextView)findViewById(R.id.ups_intfault_state);
            mUpsTest = (TextView)findViewById(R.id.ups_test);

        }

        public Holder(View itemView) {
            super(itemView);
        }

        @Override
        public void instanceView(int position) {
            UPSBean upsBean = mUpsBeanList.get(position);
            setBean(upsBean);
        }


        /**
         * @param source     文字
         * @param colorid    颜色
         * @param start      开始
         * @param end        结束
         * @return   文字部分着色
         * http://www.jianshu.com/p/84067ad289d2
         */
        private SpannableString setSpannableString(String source,int colorid,int start,int end){
            SpannableString spannableString = new SpannableString(source);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(colorid);
            spannableString.setSpan(colorSpan,start,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        private void setBean(UPSBean bean){
            mUpsName.setText(bean.name);
            triphaseInputTv.setText("A相输入电压："+bean.intAvol+" V\n\nB相输入电压："+bean.intBvol+" V\n\nC相输入电压："+bean.intCvol
            +" V\n\nA相旁路电压："+bean.bypAvol+" V\n\nB相旁路电压："+bean.bypBvol+" V\n\nC相旁路电压："+bean.bypCvol+" V");
            mainTv.setText(" 电池电压："+bean.batVol+" V\n\n 电池容量："+bean.batCapacity+" %\n\n 剩余时间："+bean.resTime+" min\n\n 旁路电流："+
            bean.batCur+" A\n\n 电池温度："+bean.batTemp+" °C\n\n 输入频率："+bean.intFrequency+" Hz\n\n 旁路频率："+bean.bypFrequency+" Hz");
            triphaseOutputTv.setText("A相输出电压："+bean.outAvol+" V\n\nB相输出电压："+bean.outBvol+" V\n\nC相输出电压："+bean.outCvol
                    +" V\n\nA相负载百分比："+bean.loadA+" %\n\nB相负载百分比："+bean.loadB+" %\n\nC相负载百分比："+bean.loadC+" %");
            if(bean.intFault == 1){
                mUpsRun.setText("UPS运行：正常");
            }else{
                mUpsRun.setText(setSpannableString("UPS运行：异常",mUpsRun.getResources().getColor(R.color.color_red),6,8));
//                mUpsRun.setTextColor(mUpsRun.getResources().getColor(R.color.color_red));
            }
            if(bean.bypActivate == 1){
                mUpsbytActive.setText("旁路电压：已激活");
            }else{
                mUpsbytActive.setText("旁路电压：未激活");
            }

            if(bean.batVol == 1){
                mBatVol.setText("电池电压低：正常");
            }else{
                mBatVol.setText(setSpannableString("电池电压低：异常",mBatVol.getResources().getColor(R.color.color_red),6,8));
//                mBatVol.setTextColor(mBatVol.getResources().getColor(R.color.color_red));
            }

            if(bean.upsType == 1){
                mUpsType.setText("UPS类型：在线式");
            }else{
                mUpsType.setText("UPS类型：后备式");
            }

            if(bean.intVolFault == 1){
                mUpsIntvolault.setText("输入电压：正常");
            }else{
                mUpsIntvolault.setText(setSpannableString("输入电压：异常",mUpsIntvolault.getResources().getColor(R.color.color_red),5,7));
//                mUpsIntvolault.setTextColor(mUpsIntvolault.getResources().getColor(R.color.color_red));
            }

            if(bean.inTest == 1){
                mUpsTest.setText("测试中：正常");
            }else{
                mUpsTest.setText(setSpannableString("测试中：异常",mUpsTest.getResources().getColor(R.color.color_red),4,6));
            }

        }
    }
}
