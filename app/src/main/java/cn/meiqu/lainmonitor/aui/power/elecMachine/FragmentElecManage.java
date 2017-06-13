package cn.meiqu.lainmonitor.aui.power.elecMachine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.baseproject.util.Config;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.RecycleBatteryAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.aui.power.FragmentPower;
import cn.meiqu.lainmonitor.bean.Baterry;
import cn.meiqu.lainmonitor.untils.DividerItemDecoration;

/**
 * Created by Fatel on 16-5-27.
 */
public class FragmentElecManage extends FragmentReal implements RecycleBatteryAdapter.OnItemClickListener{

    private RecycleBatteryAdapter mAdapter;
    private ArrayList<Baterry> mList = new ArrayList<>();

    String action_getData = className + API.batteryUrl;
    String action_addMachine = className + API.addElctroMachineUrl;
    int currentAddr= 0;
    int currentId = 0;


    @Override
    public void initViews() {
        mFBtn.setVisibility(View.VISIBLE);
        mFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShow();
            }
        });
    }

    private void dialogShow(){

        final String[] addrs = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_electric_machine,null);
        final EditText nameEd = (EditText)v.findViewById(R.id.elect_machine_name_ed);
        final EditText addressEd = (EditText)v.findViewById(R.id.elect_machine_address_ed);
        final EditText locationEd = (EditText)v.findViewById(R.id.elect_machine_location_ed);
        final EditText ipEd = (EditText)v.findViewById(R.id.elect_machine_ip_ed);
        ipEd.setText("192.168.1.222:5400");

        addressEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(addrs, currentAddr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentAddr = which;
                        addressEd.setText(addrs[which] + "");
                        dialog.dismiss();
                    }
                }).create();
                alertDialog.show();

            }
        });
        locationEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(addrs, currentAddr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentId = which;
                        locationEd.setText(addrs[which] + "");
                        dialog.dismiss();
                    }
                }).create();
                alertDialog.show();
            }
        });

        new AlertDialog.Builder(getActivity()).setMessage("添加设备").setView(v).
                setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //提交我们的
                        if(!nameEd.getText().toString().equals("") && !locationEd.getText().toString().equals("")
                                && !ipEd.getText().toString().equals("") && !addressEd.getText().toString().equals("")){
                            HttpGetController.getInstance().addBattery(className,"6",locationEd.getText().toString(),addressEd.getText().toString(),nameEd.getText().toString());
                        }else{
                            toast(getActivity(),"添加失败，输入不能为空！");
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create().show();
    }

    @Override
    public RecyclerView.Adapter getAdapter()
    {
        mRecycleV.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mAdapter = new RecycleBatteryAdapter(getActivity(),mList);
        mAdapter.setOnItemClickListner(this);
        return mAdapter;
    }

    @Override
    public String[] getActions() {
        return new String[]{action_addMachine,action_getData};
    }

    @Override
    public String getAction() {
        return null;
    }

    @Override
    public void requestData() {
        HttpGetController.getInstance().getBaterry(className);
    }

    @Override
    public void handleData(String data) {

    }

    public void handlegetData(String data) {

        ArrayList<Baterry> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Baterry>>() {
        }.getType());
        if (!temps.isEmpty()) {
            mList.clear();
            mList.addAll(temps);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onHttpHandle(String action, String data) {
        if(action.equals(action_getData)){
            setSwipeRefreshing(false);
            handlegetData(data);
        }
        if(action.equals(action_addMachine)){
            showMsg(data);
        }
    }

    @Override
    public void onItemClick(int postion) {
        Config.putInt("postion",postion);
        startActivity(new Intent(getActivity(), ElecMachineManageActivity.class));
    }

}
