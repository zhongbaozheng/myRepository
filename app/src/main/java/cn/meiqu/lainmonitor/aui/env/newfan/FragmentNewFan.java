package cn.meiqu.lainmonitor.aui.env.newfan;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseRecycle.BaseOnRecycleClickListener;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecycleNewFanAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.bean.NewFan;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentNewFan extends FragmentReal implements BaseOnRecycleClickListener {

    String action_getData = className + API.getNewFan;
    String action_switch = className + API.siwtchNewFan;
    ArrayList<NewFan> newFans = new ArrayList<>();
    RecycleNewFanAdapter adapter;


    @Override
    public RecyclerView.Adapter getAdapter() {
//        mRecycleV.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecycleNewFanAdapter(getActivity(), newFans);
        adapter.setClickListener(this);
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
        HttpGetController.getInstance().getNewFan(className);
    }

    public void handleData(String data) {
        ArrayList<NewFan> temps = new Gson().fromJson(data, new TypeToken<ArrayList<NewFan>>() {
        }.getType());
        if (!temps.isEmpty()) {
            newFans.clear();
            newFans.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }

    public void requestSwitch(String id, String status) {
        showProgressDialog();
        HttpGetController.getInstance().siwtchNewFan(id, status, className);
    }

    public void handleSwitch(String data) {
        //
        try {
            String msg = ((JSONObject) new JSONArray(data).opt(0)).optString("msg");
            new AlertDialog.Builder(getActivity()).setMessage(msg).setPositiveButton("确定", null).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestData();
    }

    @Override
    public void OnRecycleItemClick(int position) {
        requestSwitch(newFans.get(position).getId() + "", (newFans.get(position).getStatus() == 1 ? 0 : 1) + "");
    }
}
