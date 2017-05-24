package cn.meiqu.lainmonitor.aui.security.doorControl;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.activity.DoorControlActivity;
import cn.meiqu.lainmonitor.activity.SelectionActivity;
import cn.meiqu.lainmonitor.activity.UserManagerActivity;
import cn.meiqu.lainmonitor.adapter.RecycleDoorMangerAdapter;
import cn.meiqu.lainmonitor.aui.FragmentAlarm2;
import cn.meiqu.lainmonitor.bean.ManagerControlBean;

/**
 * Created by Fatel on 16-5-27.
 * unfinished
 */
public class FragmentDoorControlManage extends FragmentAlarm2 implements RecycleDoorMangerAdapter.OnItemClickListner{

    String action_getData = className + API.doorcontrlUrl;
    String action_deleteUser = className+API.deleteUserUrl;
    String action_updateUserName = className + API.updateUserNameUrl;
    String action_releaseMod = className + API.changModUrl;
    String aaction_requestMod = className +API.requestModUrl;
    private ArrayList<ManagerControlBean> mList= new ArrayList<>();
    private RecycleDoorMangerAdapter mAdapter;

    @Override
    public View getTopView() {
        viewGBody.getChildAt(0).setVisibility(View.GONE);
        mFab.setVisibility(View.VISIBLE);
        return LayoutInflater.from(getActivity()).inflate(R.layout.f_control_manager, null);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new RecycleDoorMangerAdapter(getActivity(),mList);
        mAdapter.setOnItemClickListner(this);
        return mAdapter;
    }


    @Override
    public String getAction() {
        return null;
    }

    public String[] getActions(){
        return new String[]{action_getData,action_deleteUser,action_updateUserName,action_releaseMod,aaction_requestMod};
    }

    @Override
    public void requestData() {
        showProgressDialog();
        HttpGetController.getInstance().getDoorUserControl(className);
    }

    @Override
    public void handleData(String data) {
        ArrayList<ManagerControlBean> temps = new Gson().
                fromJson(data,new TypeToken<ArrayList<ManagerControlBean>>(){}.getType());
        if(temps != null){
            mList.clear();
            mList.addAll(temps);
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onHttpHandle(String action, String data) {
        super.onHttpHandle(action, data);
        if(action.equals(action_getData)) {
            ArrayList<ManagerControlBean> temps = new Gson().
                    fromJson(data, new TypeToken<ArrayList<ManagerControlBean>>() {
                    }.getType());
            if (temps != null) {
                mList.clear();
                mList.addAll(temps);
                mAdapter.notifyDataSetChanged();
            }
        }

        if(action.equals(action_deleteUser)){
            showMsg(data);
        }
        if(action.equals(action_releaseMod)){
            showMsg(data);
        }
        if(action.equals(action_updateUserName)){

            showMsg(data);
        }
        if(action.equals(aaction_requestMod)){
            showMsg(data);
        }
    }

    @Override
    public String[] getDeviceNames() {
        return new String[0];
    }

    @Override
    public String getDeviceId(int position) {
        return null;
    }

    @Override
    public void onClickAdd() {
        //Fab的用法
        String[] updateList = {"门禁管理","部门管理","用户管理"};
        View v = getView();
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(updateList,3,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                startActivity(new Intent(getActivity(), DoorControlActivity.class));
                            }
                        },1000);
                        break;
                    case 1:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                startActivity(new Intent(getActivity(), SelectionActivity.class));
                            }
                        },1000);
                        break;
                    case 2:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                startActivity(new Intent(getActivity(), UserManagerActivity.class));
                            }
                        },1000);
                        break;
                }

            }
        }).create();
        dialog.show();

    }

    //按钮监听
    @Override
    public void onItemDelete(int position) {
        HttpGetController.getInstance().deleteDoorUser(mList.get(position).id+"",className);
    }

    @Override
    public void onItemUpdate(final int position) {

        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_selection_layout,null);
        TextView nameTv = (TextView)v.findViewById(R.id.edt_name);
        nameTv.setText("用户名称：");
        new AlertDialog.Builder(getActivity()).setMessage("修改用户名称").setView(v).
                setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        EditText nameEd = (EditText) v.findViewById(R.id.selection_name_ed);
                        if(!nameEd.getText().toString().equals("")){
                            HttpGetController.getInstance().updateDoorUserName(mList.get(position).id+"",nameEd.getText().toString(),className);
                        }else{
                            Toast.makeText(getActivity(),"输入不能为空!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create().show();
    }

    @Override
    public void onItemChangeMod(int position) {

        if(mList.get(position).status == 1){
            HttpGetController.getInstance().releasedMod(mList.get(position).id+"",mList.get(position).card,className);
        }else{
            HttpGetController.getInstance().requestMod(mList.get(position).id+"",mList.get(position).card,className);
        }
    }

    @Override
    public void onItemClick(int positon) {
        Toast.makeText(getActivity(),""+positon,Toast.LENGTH_SHORT).show();
        //开启一个查看门禁信息

    }
}
