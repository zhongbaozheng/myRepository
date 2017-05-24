package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.SelectionBean;

/**
 * Created by Administrator on 2017/5/23.
 */

public class RecycleSelectionAdapter extends BaseRecycleAdapter {

    private ArrayList<SelectionBean> mList = new ArrayList<>();
    private Context mContext;
    String mClassName;
    public RecycleSelectionAdapter(ArrayList<SelectionBean> list,Context context,String className){
        mList = list;
        mContext = context;
        mClassName = className;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContext, R.layout.re_selection,null));
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

        private TextView mSelectionNameTv;
        private TextView mUpdateTv;
        SelectionBean bean;


        public Holder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        @Override
        public void assignViews() {
            mSelectionNameTv = (TextView) findViewById(R.id.selection_name);
            mUpdateTv = (TextView)findViewById(R.id.update_tv);
            mUpdateTv.setOnClickListener(this);
        }

        @Override
        public void instanceView(int position) {
            bean = mList.get(position);
            mSelectionNameTv.setText(bean.name);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.update_tv:
                    //提交我们的修改数据并提交
                    final View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_selection_layout,null);
                    new AlertDialog.Builder(mContext).setMessage("修改部门").setView(v).
                            setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    EditText nameEd = (EditText) v.findViewById(R.id.selection_name_ed);
                                    Log.e("nameEd",nameEd.getText().toString());
                                    if(!nameEd.getText().toString().equals("")){
                                        HttpGetController.getInstance().updateSelectionName(bean.id+"",nameEd.getText().toString(),mClassName);
                                    }else{
                                        Toast.makeText(mContext,"输入不能为空!",Toast.LENGTH_SHORT).show();
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
