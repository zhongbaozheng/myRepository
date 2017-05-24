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
import cn.meiqu.lainmonitor.bean.TempReal;


public class RecycleTempRealAdapter extends BaseRecycleAdapter {
    private Context mContent;
    private ArrayList<TempReal> tempReals;

    public RecycleTempRealAdapter(Context mContent, ArrayList<TempReal> tempReals) {
        this.mContent = mContent;
        this.tempReals = tempReals;
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
        return new Holder(View.inflate(mContent, R.layout.recycle_temp_real, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return tempReals.size();
    }

    class Holder extends BaseHolder implements RippleView.OnRippleCompleteListener {
        public Holder(View itemView) {
            super(itemView);
        }

        private TextView mTvName;
        private TextView mTvTemp;
        private TextView mTvWet;

        public void assignViews() {
            mTvName = (TextView) findViewById(R.id.tv_name);
            mTvTemp = (TextView) findViewById(R.id.tv_temp);
            mTvWet = (TextView) findViewById(R.id.tv_wet);
        }

        @Override
        public void instanceView(final int position) {
            TempReal tempReal = tempReals.get(position);
            mTvName.setText(tempReal.getEhmName() + "");
            mTvTemp.setText(tempReal.getEhmTemp() + "℃");
            mTvWet.setText(tempReal.getEhmHum() + "%");
            if (tempReal.getEhmTemp() >= tempReal.getEhmMinTemp() && tempReal.getEhmTemp() <= tempReal.getEhmMaxTemp()) {
                mTvTemp.setBackgroundColor(mTvTemp.getResources().getColor(R.color.colorPrimary));
            } else {
                mTvTemp.setBackgroundColor(mTvTemp.getResources().getColor(R.color.red));
            }

            if (tempReal.getEhmHum() >= tempReal.getEhmMinHum() && tempReal.getEhmTemp() <= tempReal.getEhmMaxHum()) {
                mTvWet.setBackgroundColor(mTvTemp.getResources().getColor(R.color.colorPrimary));
            } else {
                mTvWet.setBackgroundColor(mTvTemp.getResources().getColor(R.color.red));
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
