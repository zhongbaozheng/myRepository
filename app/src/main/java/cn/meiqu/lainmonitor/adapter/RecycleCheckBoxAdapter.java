package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.baseproject.util.Config;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.DoorMessageBean;

/**
 * Created by Administrator on 2017/5/22.
 */

public class RecycleCheckBoxAdapter extends BaseRecycleAdapter {
    private Context mContext;
    private ArrayList<DoorMessageBean> mList = new ArrayList<>();

    public RecycleCheckBoxAdapter(Context context,ArrayList<DoorMessageBean> list){
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContext, R.layout.re_check,null),mContext);
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

        private CheckBox checkBox;
        DoorMessageBean bean;


        public Holder(View itemView,Context context) {
            super(itemView);
            Config.register(context);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        @Override
        public void assignViews() {
            super.assignViews();
            checkBox = (CheckBox) findViewById(R.id.checkbox);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void instanceView(int position) {
            bean = mList.get(position);
            Config.putString(bean.name,"");
            checkBox.setText(bean.name);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String  flag;
            if(checkBox.isChecked()){
                flag = bean.id+",";
            }else{
                flag = "";
            }

            //key 为bean.name
            Config.putString(bean.name,flag);

        }
    }
}
