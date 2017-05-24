package cn.meiqu.lainmonitor.aui.power.pElec;

import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecycleElecRealAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.bean.Electricity;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentPElecReal extends FragmentReal {
    String action_getData = className + API.getPElecReal;
    public ArrayList<Electricity> electricities = new ArrayList<>();
    RecycleElecRealAdapter adapter;


    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleElecRealAdapter(getActivity(), electricities);
        return adapter;
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getPElecReal(className);
    }

    public void handleData(String data) {
        ArrayList<Electricity> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Electricity>>() {
        }.getType());
        if (!temps.isEmpty()) {
            electricities.clear();
            electricities.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }
}
