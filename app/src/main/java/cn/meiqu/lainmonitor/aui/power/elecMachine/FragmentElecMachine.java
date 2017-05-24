package cn.meiqu.lainmonitor.aui.power.elecMachine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecycleElecMachineRealAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.bean.ElecMachine;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentElecMachine extends FragmentReal {
    String action_getData = className + API.getElecMachine;
//    String action_switch = className + API.switchElecMachine;
   public  ArrayList<ElecMachine> ElecMachines = new ArrayList<>();
    RecycleElecMachineRealAdapter adapter;


    @Override
    public RecyclerView.Adapter getAdapter() {
        mRecycleV.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecycleElecMachineRealAdapter(getActivity(), ElecMachines);
        return adapter;
    }

    @Override
    public String getAction() {
        return action_getData;
    }
//
//    @Override
//    public String[] getActions() {
//        return new String[]{action_getData, action_switch};
//    }

//    @Override
//    public void onHttpHandle(String action, String data) {
//        super.onHttpHandle(action, data);
//        if (getHttpStatus(data)) {
//            if (action.equals(action_switch)) {
//                handleSwitch(data);
//            }
//        }
//    }

    public void requestData() {
        HttpGetController.getInstance().getElecMachine(className);
    }

    public void handleData(String data) {
        ArrayList<ElecMachine> temps = new Gson().fromJson(data, new TypeToken<ArrayList<ElecMachine>>() {
        }.getType());
        if (!temps.isEmpty()) {
            ElecMachines.clear();
            ElecMachines.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }
}
