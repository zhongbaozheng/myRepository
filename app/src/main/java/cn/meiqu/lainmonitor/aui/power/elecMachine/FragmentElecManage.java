package cn.meiqu.lainmonitor.aui.power.elecMachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.RecycleBatteryAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.aui.power.FragmentPower;
import cn.meiqu.lainmonitor.bean.Baterry;

/**
 * Created by Fatel on 16-5-27.
 */
public class FragmentElecManage extends FragmentReal implements RecycleBatteryAdapter.OnItemClickListener{

    private RecycleBatteryAdapter mAdapter;
    private ArrayList<Baterry> mList = new ArrayList<>();

    String action_getData = className + API.batteryUrl;

    @Override
    public RecyclerView.Adapter getAdapter()
    {
        mRecycleV.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mAdapter = new RecycleBatteryAdapter(getActivity(),mList);
        mAdapter.setOnItemClickListner(this);
        return mAdapter;
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData() {
        HttpGetController.getInstance().getBaterry(className);
    }

    @Override
    public void handleData(String data) {

        ArrayList<Baterry> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Baterry>>() {
        }.getType());
        if (!temps.isEmpty()) {
            mList.clear();
            mList.addAll(temps);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int postion) {
        //开启我们的fragment
        Toast.makeText(getActivity(), ""+postion, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), ElecMachineManageActivity.class));
//        AddToFragment(new FragmentBattery(), R.id.frame_fragment,postion+"");
    }

    public void AddToFragment(Fragment f, int containerId,String position) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("postion",position);
        f.setArguments(bundle);
        //没办法再用Activity
        transaction.replace(containerId, f,f.getClass().getName());
//        getFragmentManager().popBackStack();
//        transaction.hide(getParentFragment());

        transaction.addToBackStack(null);//写了默认返回上一个fragment
        transaction.commit();
    }

}
