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
import cn.meiqu.lainmonitor.bean.NewFan;


public class RecycleNewFanAdapter extends BaseRecycleAdapter {
    private Context mContent;
    private ArrayList<NewFan> newFans;

    public RecycleNewFanAdapter(Context mContent, ArrayList<NewFan> newFans) {
        this.mContent = mContent;
        this.newFans = newFans;
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
        return new Holder(View.inflate(mContent, R.layout.recycle_newfan, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return newFans.size();
    }

    class Holder extends BaseHolder implements RippleView.OnRippleCompleteListener {
        public Holder(View itemView) {
            super(itemView);
        }

        private TextView mTvName;
        private TextView mTvGallery;
        private TextView mTvStatus;
        private RippleView mRippleAction;
        private TextView mTvAction;

        public void assignViews() {
            mTvName = (TextView) findViewById(R.id.tv_name);
            mTvGallery = (TextView) findViewById(R.id.tv_gallery);
            mTvStatus = (TextView) findViewById(R.id.tv_status);
            mRippleAction = (RippleView) findViewById(R.id.ripple_action);
            mTvAction = (TextView) findViewById(R.id.tv_action);
            mRippleAction.setOnRippleCompleteListener(this);
        }

        @Override
        public void instanceView(final int position) {
            NewFan newFan = newFans.get(position);
            mTvName.setText(newFan.getName() + "");
            mTvGallery.setText(newFan.getGallery() + "");
            mTvStatus.setBackgroundColor(mTvStatus.getResources().getColor(R.color.colorPrimary));
            mTvAction.setBackgroundColor(mTvStatus.getResources().getColor(R.color.colorAccent));
            if (newFan.getStatus() == 0) {
                mTvStatus.setText("已关" + "");
//                mTvStatus.setBackgroundColor(mTvStatus.getResources().getColor(R.color.colorPrimary));
                mTvAction.setText("打开");
            } else if (newFan.getStatus() == 1) {
                mTvStatus.setText("已开");
//                mTvStatus.setBackgroundColor(mTvStatus.getResources().getColor(R.color.red));
                mTvAction.setText("关闭");
            } else if (newFan.getStatus() == 2) {
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
