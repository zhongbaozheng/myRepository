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
import cn.meiqu.lainmonitor.bean.Electricity;


public class RecycleElecRealAdapter extends BaseRecycleAdapter {
    private Context mContent;
    private ArrayList<Electricity> electricities;

    public RecycleElecRealAdapter(Context mContent, ArrayList<Electricity> electricities) {
        this.mContent = mContent;
        this.electricities = electricities;
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
        return new Holder(View.inflate(mContent, R.layout.recycle_nlwater_real, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return electricities.size();
    }

    class Holder extends BaseHolder implements RippleView.OnRippleCompleteListener {
        public Holder(View itemView) {
            super(itemView);
        }

        private TextView mTvName;
        private TextView mTvGallery;
        private TextView mTvStatus;

        public void assignViews() {
            mTvName = (TextView) findViewById(R.id.tv_name);
            mTvGallery = (TextView) findViewById(R.id.tv_gallery);
            mTvStatus = (TextView) findViewById(R.id.tv_status);
        }


        @Override
        public void instanceView(final int position) {
            Electricity electricity = electricities.get(position);
            mTvName.setText(electricity.getName() + "");
            mTvGallery.setText(electricity.getGallery() + "");
            if (electricity.getStatus() == 0) {
                mTvStatus.setText("正常" + "");
                mTvStatus.setBackgroundColor(mTvStatus.getResources().getColor(R.color.colorPrimary));
            } else if (electricity.getStatus() == 1) {
                mTvStatus.setText("报警");
                mTvStatus.setBackgroundColor(mTvStatus.getResources().getColor(R.color.red));
            } else if (electricity.getStatus() == 2) {
                mTvStatus.setText("未连接");
                mTvStatus.setBackgroundColor(mTvStatus.getResources().getColor(R.color.selorgerds));
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
