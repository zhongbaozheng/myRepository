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
import cn.meiqu.lainmonitor.bean.TempAlart;


public class RecycleTempAlertAdapter extends BaseRecycleAdapter {
    private Context mContent;
    private ArrayList<TempAlart> TempAlarts;


    public RecycleTempAlertAdapter(Context mContent, ArrayList<TempAlart> TempAlarts) {
        this.mContent = mContent;
        this.TempAlarts = TempAlarts;
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
        return TempAlarts.size();
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
            TempAlart TempAlart = TempAlarts.get(position);
            mTvAddr.setText(TempAlart.getEhaLocation() + "");
            mTvName.setText(TempAlart.getEhaName() + "");
            mTvInfo.setText(TempAlart.getEhaInfo() + "");
            mTvTime.setText(TempAlart.getEhaTime() + "");
        }


        @Override
        public void onClick(View v) {
            if (getClickListener() != null) {
                getClickListener().OnRecycleItemClick(getPosition());
            }
        }
    }
}
