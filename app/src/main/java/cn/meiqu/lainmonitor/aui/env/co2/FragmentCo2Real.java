package cn.meiqu.lainmonitor.aui.env.co2;

import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecycleCo2RealAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.bean.Co2;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentCo2Real extends FragmentReal {
    String action_getData = className + API.getCo2Real;
    public ArrayList<Co2> co2s = new ArrayList<>();
    RecycleCo2RealAdapter adapter;


    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleCo2RealAdapter(getActivity(), co2s);
        return adapter;
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getCO2Real(className);
    }

    public void handleData(String data) {
        ArrayList<Co2> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Co2>>() {
        }.getType());
        if (!temps.isEmpty()) {
            co2s.clear();
            co2s.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }
}
