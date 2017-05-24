package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.ElecMachineAlart;


public class RecycleElecMachineAlartAdapter extends BaseRecycleAdapter {
    private Context mContent;
    private ArrayList<ElecMachineAlart> ElecMachineAlarts;


    public RecycleElecMachineAlartAdapter(Context mContent, ArrayList<ElecMachineAlart> ElecMachineAlarts) {
        this.mContent = mContent;
        this.ElecMachineAlarts = ElecMachineAlarts;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContent, R.layout.recycle_alart_type1, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return ElecMachineAlarts.size();
    }

    class Holder extends BaseHolder implements View.OnClickListener {
        public Holder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            itemView.setOnClickListener(this);
        }

        private TextView mTvAddr;
        private TextView mTvName;
        private TextView mTvInfo;
        private TextView mTvTime;

        public void assignViews() {
            mTvAddr = (TextView) findViewById(R.id.tv_addr);
            mTvName = (TextView) findViewById(R.id.tv_name);
            mTvInfo = (TextView) findViewById(R.id.tv_info);
            mTvTime = (TextView) findViewById(R.id.tv_time);

            mTvAddr.setTextColor(mTvAddr.getResources().getColor(R.color.black3));
            mTvName.setTextColor(mTvAddr.getResources().getColor(R.color.black3));
            mTvInfo.setTextColor(mTvAddr.getResources().getColor(R.color.black3));
            mTvTime.setTextColor(mTvAddr.getResources().getColor(R.color.black3));

        }


        @Override
        public void instanceView(final int position) {
            if (position % 2 == 0) {
                itemView.setBackgroundColor(mTvAddr.getResources().getColor(R.color.white));
            } else {
                itemView.setBackgroundColor(mTvAddr.getResources().getColor(R.color.item_bg));
            }
            ElecMachineAlart ElecMachineAlart = ElecMachineAlarts.get(position);
            mTvAddr.setText(ElecMachineAlart.getEmaLocation() + "");
            mTvName.setText(ElecMachineAlart.getEmaName() + "");
            mTvInfo.setText(ElecMachineAlart.getEmaInfo() + "");
            mTvTime.setText(ElecMachineAlart.getEmaTime() + "");
        }


        @Override
        public void onClick(View v) {
            if (getClickListener() != null) {
                getClickListener().OnRecycleItemClick(getPosition());
            }
        }
    }
}
