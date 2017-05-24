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
import cn.meiqu.lainmonitor.bean.Device80;


public class Recycle80Adapter extends BaseRecycleAdapter {
    private Context mContent;
    private ArrayList<Device80> Device80s;

    public interface OnItemClickListner {
        public void onItemDel(int position);

        public void onItemEdit(int position);

    }

    public OnItemClickListner getOnItemClickListner() {
        return onItemClickListner;
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    private OnItemClickListner onItemClickListner;

    public Recycle80Adapter(Context mContent, ArrayList<Device80> Device80s) {
        this.mContent = mContent;
        this.Device80s = Device80s;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContent, R.layout.recycle_8060, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return Device80s.size();
    }

    class Holder extends BaseHolder implements View.OnClickListener {
        public Holder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        private TextView mTvAddr;
        private TextView mTvGallery;
        private TextView mTvLocation;
        private TextView mTvDeviceName;
        private TextView mTvName;
        private TextView mTvIp;
        private TextView mTvEdt;
        private TextView mTvDel;

        public void assignViews() {
            mTvAddr = (TextView) findViewById(R.id.tv_addr);
            mTvGallery = (TextView) findViewById(R.id.tv_gallery);
            mTvLocation = (TextView) findViewById(R.id.tv_location);
            mTvDeviceName = (TextView) findViewById(R.id.tv_deviceName);
            mTvName = (TextView) findViewById(R.id.tv_name);
            mTvIp = (TextView) findViewById(R.id.tv_ip);
            mTvEdt = (TextView) findViewById(R.id.tv_edt);
            mTvDel = (TextView) findViewById(R.id.tv_del);

            mTvAddr.setTextColor(mTvAddr.getResources().getColor(R.color.black3));
            mTvGallery.setTextColor(mTvAddr.getResources().getColor(R.color.black3));
            mTvLocation.setTextColor(mTvAddr.getResources().getColor(R.color.black3));
            mTvDeviceName.setTextColor(mTvAddr.getResources().getColor(R.color.black3));
            mTvName.setTextColor(mTvAddr.getResources().getColor(R.color.black3));
            mTvIp.setTextColor(mTvAddr.getResources().getColor(R.color.black3));
            mTvEdt.setBackgroundColor(mTvName.getResources().getColor(R.color.colorPrimary));
            mTvDel.setBackgroundColor(mTvName.getResources().getColor(R.color.colorAccent));
            mTvEdt.setOnClickListener(this);
            mTvDel.setOnClickListener(this);
        }

        @Override
        public void instanceView(final int position) {
            if (position % 2 == 0) {
                itemView.setBackgroundColor(mTvName.getResources().getColor(R.color.white));
            } else {
                itemView.setBackgroundColor(mTvName.getResources().getColor(R.color.item_bg));
            }
            Device80 Device80 = Device80s.get(position);
            mTvAddr.setText("" + Device80.getAddress());
            mTvGallery.setText("" + Device80.getGallery());
            mTvLocation.setText("" + Device80.getDeviceLocationPojo().getDlName());
            mTvDeviceName.setText("" + Device80.getDeviceName());
            mTvName.setText("" + Device80.getName());
            mTvIp.setText("" + Device80.getIpPort());

        }

        @Override
        public void onClick(View v) {
            if (getOnItemClickListner() != null) {
                if (v.getId() == mTvEdt.getId()) {
                    getOnItemClickListner().onItemEdit(getPosition());
                } else if (v.getId() == mTvDel.getId()) {
                    getOnItemClickListner().onItemDel(getPosition());
                }
            }
        }
    }
}
