package cn.meiqu.lainmonitor.aui.env.thunder;

import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecycleThunderRealAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.bean.Thunder;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentThunderReal extends FragmentReal {
    String action_getData = className + API.getThunderReal;
    public ArrayList<Thunder> thunders = new ArrayList<>();
    RecycleThunderRealAdapter adapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleThunderRealAdapter(getActivity(), thunders);
        return adapter;
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getThunderReal(className);
    }

    public void handleData(String data) {
        ArrayList<Thunder> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Thunder>>() {
        }.getType());
        if (!temps.isEmpty()) {
            thunders.clear();
            thunders.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }
}
