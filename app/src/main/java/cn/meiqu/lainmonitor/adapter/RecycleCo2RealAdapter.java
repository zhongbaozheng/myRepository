package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseOnRecycleClickListener;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.baseproject.view.RippleView;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.Co2;

public class RecycleCo2RealAdapter extends BaseRecycleAdapter {
    private Context mContent;
    private ArrayList<Co2> co2s;

    public RecycleCo2RealAdapter(Context mContent, ArrayList<Co2> co2s) {
        this.mContent = mContent;
        this.co2s = co2s;
    }

    private BaseOnRecycleClickListener clickListener;

    public BaseOnRecycleClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(BaseOnRecycleClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContent, R.layout.recycle_co2_real, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return co2s.size();
    }

    class Holder extends BaseHolder implements RippleView.OnRippleCompleteListener {
        public Holder(View itemView) {
            super(itemView);
        }

        private TextView mTvName;
        private TextView mTvAlert;
        private TextView mTvReal;

        public void assignViews() {
            mTvName = (TextView) findViewById(R.id.tv_name);
            mTvAlert = (TextView) findViewById(R.id.tv_alert);
            mTvReal = (TextView) findViewById(R.id.tv_real);
        }


        @Override
        public void instanceView(final int position) {
            Co2 co2 = co2s.get(position);
            mTvName.setText(co2.getName() + "");
            mTvAlert.setText(co2.getAlarmData() + "PM");
            mTvReal.setText(co2.getCurrentData() + "PM");
            mTvAlert.setBackgroundColor(mTvReal.getResources().getColor(R.color.colorPrimary));
            if (co2.getCurrentData() <= co2.getAlarmData()) {
                mTvReal.setBackgroundColor(mTvReal.getResources().getColor(R.color.colorPrimary));
            } else {
                mTvReal.setBackgroundColor(mTvReal.getResources().getColor(R.color.red));
            }
        }

        @Override
        public void onComplete(RippleView rippleView) {
            if (getClickListener() != null) {
                getClickListener().OnRecycleItemClick(getPosition());
            }
        }
    }
}
