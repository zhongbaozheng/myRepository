package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.DoorControl;
import cn.meiqu.lainmonitor.bean.ManagerControlBean;

/**
 * Created by Administrator on 2017/5/8.
 */

public class RecycleDoorMangerAdapter extends BaseRecycleAdapter {


    //一个监听
    public interface OnItemClickListner {
        public void onItemDelete(int position);
        public void onItemUpdate(int position);
        public void onItemChangeMod(int position);
        public void onItemClick(int positon);

    }
    public OnItemClickListner getOnItemClickListner() {
        return onItemClickListner;
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    private OnItemClickListner onItemClickListner;


    private Context mContext;
    private ArrayList<ManagerControlBean> mList;

    public RecycleDoorMangerAdapter(Context context,ArrayList<ManagerControlBean> list){
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContext, R.layout.re_control_mamager, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Holder extends BaseHolder implements View.OnClickListener,View.OnLongClickListener{
        private TextView userNameTv;
        private TextView cardTv;
        private TextView selectionNameTv;
        private TextView applymodTv;

        private Button changemodBtn;
        private Button updateBtn;
        private Button deleteBtn;
        private View view;

        public Holder(View itemView) {
            super(itemView);
            view = itemView;
            itemView.setOnLongClickListener(this);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        @Override
        public void assignViews() {
            userNameTv = (TextView) findViewById(R.id.manager_name_tv);
            cardTv = (TextView) findViewById(R.id.manager_card_tv);
            selectionNameTv = (TextView) findViewById(R.id.manager_select_name_tv);
            applymodTv = (TextView) findViewById(R.id.manager_applymod_tv);
            changemodBtn = (Button)findViewById(R.id.manager_change_mod_btn);
            updateBtn = (Button)findViewById(R.id.manager_oprerate_update_btn);
            deleteBtn = (Button)findViewById(R.id.manager_delete_btn);
        }

        @Override
        public void instanceView(int position) {
            String str = "";
            ManagerControlBean bean = mList.get(position);
            userNameTv.setText(bean.name);
            cardTv.setText(bean.card);
            selectionNameTv.setText(bean.sectionName);
            for(int i = 0;i<bean.daui.size();i++){
                str = str+bean.daui.get(i).address+",";
            }
            str = str.substring(0,str.length()-1);
            applymodTv.setText(str);
            changemodBtn.setText(bean.status == 1?"已授权":"未授权");
            updateBtn.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);
            changemodBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getOnItemClickListner() != null) {
                if (v.getId() == updateBtn.getId()) {
                    getOnItemClickListner().onItemUpdate(getPosition());
                } else if (v.getId() == deleteBtn.getId()) {
                    getOnItemClickListner().onItemDelete(getPosition());
                }else if(v.getId() == changemodBtn.getId()){
                    getOnItemClickListner().onItemChangeMod(getPosition());
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(v.getId() == view.getId()){
                getOnItemClickListner().onItemClick(getPosition());
            }
            return true;
        }
    }
}
