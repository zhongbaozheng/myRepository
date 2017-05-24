package cn.meiqu.lainmonitor.aui.env.temp;

import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecycleTempRealAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.bean.TempReal;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentTempReal extends FragmentReal {
    String action_getData = className + API.getTempReal;
    public ArrayList<TempReal> tempReals = new ArrayList<>();
    RecycleTempRealAdapter adapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleTempRealAdapter(getActivity(), tempReals);
        return adapter;
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getTempReal(className);
    }

    public void handleData(String data) {
        ArrayList<TempReal> temps = new Gson().fromJson(data, new TypeToken<ArrayList<TempReal>>() {
        }.getType());
        if (!temps.isEmpty()) {
            tempReals.clear();
            tempReals.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }
}
