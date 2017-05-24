package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.baseproject.view.RippleView;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.Location;


public class RecycleLocationAdapter extends BaseRecycleAdapter {
    private Context mContent;
    private ArrayList<Location> Locations;

    public interface OnItemClickListner {
        public void onItemEdt(int position);

        public void onItemDel(int position);
    }

    public OnItemClickListner getOnItemClickListner() {
        return onItemClickListner;
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    private OnItemClickListner onItemClickListner;

    public RecycleLocationAdapter(Context mContent, ArrayList<Location> Locations) {
        this.mContent = mContent;
        this.Locations = Locations;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContent, R.layout.recycle_location, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return Locations.size();
    }

    class Holder extends BaseHolder implements RippleView.OnRippleCompleteListener {
        public Holder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }


        private TextView mTvId;
        private TextView mTvName;
        private RippleView mRippleEdt;
        private TextView mTvEdt;
        private RippleView mRippleDel;
        private TextView mTvDel;

        public void assignViews() {
            mTvId = (TextView) findViewById(R.id.tv_id);
            mTvName = (TextView) findViewById(R.id.tv_name);
            mRippleEdt = (RippleView) findViewById(R.id.ripple_edt);
            mTvEdt = (TextView) findViewById(R.id.tv_edt);
            mRippleDel = (RippleView) findViewById(R.id.ripple_del);
            mTvDel = (TextView) findViewById(R.id.tv_del);

            mTvId.setTextColor(mTvId.getResources().getColor(R.color.black3));
            mTvName.setTextColor(mTvId.getResources().getColor(R.color.black3));
            mTvEdt.setBackgroundColor(mTvId.getResources().getColor(R.color.colorPrimary));
            mTvDel.setBackgroundColor(mTvId.getResources().getColor(R.color.colorAccent));
            mRippleEdt.setOnRippleCompleteListener(this);
            mRippleDel.setOnRippleCompleteListener(this);
        }


        @Override
        public void instanceView(final int position) {
            if (position % 2 == 0) {
                itemView.setBackgroundColor(mTvId.getResources().getColor(R.color.white));
            } else {
                itemView.setBackgroundColor(mTvId.getResources().getColor(R.color.item_bg));
            }
            Location location = Locations.get(position);
            mTvId.setText("" + location.getDlId());
            mTvName.setText("" + location.getDlName());
            mTvEdt.setText("更新");
        }


        @Override
        public void onComplete(RippleView v) {
            if (getOnItemClickListner() != null) {
                if (v.getId() == mRippleDel.getId()) {
                    getOnItemClickListner().onItemDel(getPosition());
                } else if (v.getId() == mRippleEdt.getId()) {
                    getOnItemClickListner().onItemEdt(getPosition());
                }
            }
        }
    }
}
