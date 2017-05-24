package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.DoorMessageBean;

/**
 * Created by Administrator on 2017/5/19.
 */

public class RecycleDoorMessageAdapter extends BaseRecycleAdapter {
    private ArrayList<DoorMessageBean> mList = new ArrayList<>();
    private Context mContext;
    private String mClassName;
    public RecycleDoorMessageAdapter(ArrayList<DoorMessageBean> list,Context context,String className){
        mList = list;
        mContext = context;
        mClassName = className;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContext, R.layout.recycle_door_message,null));
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

        private TextView mDoorNameTv;
        private TextView mDoorAddressTv;
        private TextView mDoorChangeTv;
        DoorMessageBean bean;


        public Holder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        @Override
        public void assignViews() {
            mDoorNameTv = (TextView) findViewById(R.id.door_name_tv);
            mDoorAddressTv = (TextView)findViewById(R.id.door_address_tv);
            mDoorChangeTv = (TextView)findViewById(R.id.door_change_tv);
            mDoorChangeTv.setOnClickListener(this);
        }

        @Override
        public void instanceView(int position) {
            bean = mList.get(position);
            mDoorNameTv.setText(bean.name);
            mDoorAddressTv.setText(bean.address+"");
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.door_change_tv:
                    //提交我们的修改数据并提交
                    final View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_door_layout,null);
                    new AlertDialog.Builder(mContext).setMessage("修改门禁").setView(v).
                            setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    EditText nameEd = (EditText) v.findViewById(R.id.door_name_ed);
                                    EditText addressEd = (EditText) v.findViewById(R.id.door_address_ed);
                                    if(!nameEd.getText().toString().equals("") && addressEd.getText().toString().equals("") ){
                                        HttpGetController.getInstance().updateDoorMessage(bean.id+"",nameEd.getText().toString(),addressEd.getText().toString(),mClassName);
                                    }else{
                                        Toast.makeText(mContext,"您的输入有误！",Toast.LENGTH_SHORT).show();
                                    }

//                                    Toast.makeText(mContext,"修改成功",Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create().show();
                    break;
                default:
                    break;
            }
        }
    }
}
