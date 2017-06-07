package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import cn.meiqu.lainmonitor.R;

import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.lainmonitor.bean.Baterry;
import cn.meiqu.lainmonitor.bean.ElecMachine;

/**
 * Created by Administrator on 2017/6/1.
 */

public class RecycleBatteryAdapter extends BaseRecycleAdapter {

    private Context mContext;
    private ArrayList<Baterry> mList;
    View v;
    public interface OnItemClickListener{
        public void onItemClick(int postion);
    }

    private OnItemClickListener onItemClickListner;

    public OnItemClickListener getOnItemClickListener(){
        return onItemClickListner;
    }

    public void setOnItemClickListner(OnItemClickListener onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }



    public RecycleBatteryAdapter(Context context,ArrayList<Baterry> list){
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = View.inflate(mContext,R.layout.re_battery_item,null);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder)holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class Holder extends BaseHolder implements View.OnClickListener{

        private int mPosition;
        private TextView mBaterryNameTv;
        private TextView mBaterryIdTv;
        private TextView mBaterryAddressTv;
        private TextView mBaterryIpTv;

        public Holder(View itemView){
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        @Override
        public void instanceView(int position) {
            Baterry baterry = mList.get(position);
            mPosition = position;
            mBaterryIdTv.setText("设备位置："+baterry.deviceLocationPojo.dlName);
            mBaterryNameTv.setText("设备名称："+baterry.emmName);
            mBaterryAddressTv.setText("设备地址："+baterry.emmAddress);
            mBaterryIpTv.setText("Ip："+baterry.ipPort);
        }

        @Override
        public void assignViews() {
            super.assignViews();
            mBaterryNameTv = (TextView) findViewById(R.id.battery_name_tv);
            mBaterryIdTv = (TextView) findViewById(R.id.battery_id_tv);
            mBaterryAddressTv = (TextView) findViewById(R.id.battery_address_tv);
            mBaterryIpTv = (TextView) findViewById(R.id.battery_ip_tv);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //点击这里传递我们的postion,，开启我们的fragment
            if(view.getId() == v.getId()){
                getOnItemClickListener().onItemClick(getPosition());
            }

        }
    }
}
