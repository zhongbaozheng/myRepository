package cn.meiqu.lainmonitor.aui.security.infrared;

import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecycleInfraredRealAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.bean.Security;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentInfraredReal extends FragmentReal {
    String action_getData = className + API.getInfraredReal;
    ArrayList<Security> securities = new ArrayList<>();
    RecycleInfraredRealAdapter adapter;


    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleInfraredRealAdapter(getActivity(), securities);
        return adapter;
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getInfraredReal(className);
    }

    public void handleData(String data) {
        ArrayList<Security> temps = new Gson().fromJson(data, new TypeToken<ArrayList<Security>>() {
        }.getType());
        if (!temps.isEmpty()) {
            securities.clear();
            securities.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }
}
