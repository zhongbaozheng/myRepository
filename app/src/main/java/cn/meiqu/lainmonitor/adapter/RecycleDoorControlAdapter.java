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
import cn.meiqu.lainmonitor.bean.DoorControl;


public class RecycleDoorControlAdapter extends BaseRecycleAdapter {
    private Context mContent;
    private ArrayList<DoorControl> DoorControls;


    public RecycleDoorControlAdapter(Context mContent, ArrayList<DoorControl> DoorControls) {
        this.mContent = mContent;
        this.DoorControls = DoorControls;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContent, R.layout.recycle_door_real, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return DoorControls.size();
    }

    class Holder extends BaseHolder implements View.OnClickListener {
        public Holder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            itemView.setOnClickListener(this);
        }


        private TextView mTvUserName;
        private TextView mTvName;
        private TextView mTvSectionName;
        private TextView mTvDoorName;
        private TextView mTvTime;

        public void assignViews() {
            mTvUserName = (TextView) findViewById(R.id.tv_userName);
            mTvName = (TextView) findViewById(R.id.tv_name);
            mTvSectionName = (TextView) findViewById(R.id.tv_sectionName);
            mTvDoorName = (TextView) findViewById(R.id.tv_doorName);
            mTvTime = (TextView) findViewById(R.id.tv_time);

            mTvUserName.setTextColor(mTvName.getResources().getColor(R.color.black3));
            mTvName.setTextColor(mTvName.getResources().getColor(R.color.black3));
            mTvSectionName.setTextColor(mTvSectionName.getResources().getColor(R.color.black3));
            mTvDoorName.setTextColor(mTvDoorName.getResources().getColor(R.color.black3));
            mTvTime.setTextColor(mTvTime.getResources().getColor(R.color.black3));

        }

        @Override
        public void instanceView(final int position) {
            if (position % 2 == 0) {
                itemView.setBackgroundColor(mTvUserName.getResources().getColor(R.color.white));
            } else {
                itemView.setBackgroundColor(mTvUserName.getResources().getColor(R.color.item_bg));
            }
            DoorControl DoorControl = DoorControls.get(position);
            mTvUserName.setText(DoorControl.getName() + "");
            mTvName.setText(DoorControl.getCard() + "");
            mTvSectionName.setText(DoorControl.getSectionName() + "");
            mTvDoorName.setText(DoorControl.getDoorName() + "");
            mTvTime.setText(DoorControl.getTime() + "");
        }


        @Override
        public void onClick(View v) {
            if (getClickListener() != null) {
                getClickListener().OnRecycleItemClick(getPosition());
            }
        }
    }
}
