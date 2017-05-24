package cn.meiqu.lainmonitor.aui.env.nlwater;

import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecycleNLwaterRealAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.bean.NLocationWater;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentNLwaterReal extends FragmentReal {
    String action_getData = className + API.getNoLocationWaterReal;
    public ArrayList<NLocationWater> waters = new ArrayList<>();
    RecycleNLwaterRealAdapter adapter;


    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleNLwaterRealAdapter(getActivity(), waters);
        return adapter;
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getnLWaterReal(className);
    }

    public void handleData(String data) {
        ArrayList<NLocationWater> temps = new Gson().fromJson(data, new TypeToken<ArrayList<NLocationWater>>() {
        }.getType());
        if (!temps.isEmpty()) {
            waters.clear();
            waters.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }
}
