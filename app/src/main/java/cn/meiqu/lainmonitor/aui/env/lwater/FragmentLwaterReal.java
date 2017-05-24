package cn.meiqu.lainmonitor.aui.env.lwater;

import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecycleLwaterRealAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.bean.LocationWater;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentLwaterReal extends FragmentReal {
    String action_getData = className + API.getLocationWaterReal;
    ArrayList<LocationWater> waters = new ArrayList<>();
    RecycleLwaterRealAdapter adapter;


    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleLwaterRealAdapter(getActivity(), waters);
        return adapter;
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getLWaterReal(className);
    }

    public void handleData(String data) {
        ArrayList<LocationWater> temps = new Gson().fromJson(data, new TypeToken<ArrayList<LocationWater>>() {
        }.getType());
        if (!temps.isEmpty()) {
            waters.clear();
            waters.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }
}
