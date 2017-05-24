package cn.meiqu.lainmonitor.aui.env.airF;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecycleAirFRealAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.bean.AirF;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentAirF extends FragmentReal implements RecycleAirFRealAdapter.OnAirClickListener {
    String action_getData = className + API.getAirF;
    String action_switch = className + API.switchAirF;
    ArrayList<AirF> AirFs = new ArrayList<>();
    RecycleAirFRealAdapter adapter;


    @Override
    public RecyclerView.Adapter getAdapter() {
        mRecycleV.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecycleAirFRealAdapter(getActivity(), AirFs);
        adapter.setOnAirClickListener(this);
        return adapter;
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public String[] getActions() {
        return new String[]{action_getData, action_switch};
    }

    @Override
    public void onHttpHandle(String action, String data) {
        super.onHttpHandle(action, data);
        if (getHttpStatus(data)) {
            if (action.equals(action_switch)) {
                handleSwitch(data);
            }
        }
    }

    public void requestData() {
        HttpGetController.getInstance().getAirF(className);
    }

    public void handleData(String data) {
        ArrayList<AirF> temps = new Gson().fromJson(data, new TypeToken<ArrayList<AirF>>() {
        }.getType());
        if (!temps.isEmpty()) {
            AirFs.clear();
            AirFs.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }

    public void requestSwitch(String id, String ip, String number) {
        showProgressDialog();
        HttpGetController.getInstance().switchAirF(id, ip, number, className);
    }

    public void handleSwitch(String data) {
        //
        try {
            String msg = ((JSONObject) new JSONArray(data).opt(0)).optString("reslog");
            new AlertDialog.Builder(getActivity()).setMessage(msg).setPositiveButton("确定", null).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAirClick(int position, String number) {
        AirF airF = AirFs.get(position);
        requestSwitch(airF.getId() + "", airF.getIp() + "", number);
    }
}
