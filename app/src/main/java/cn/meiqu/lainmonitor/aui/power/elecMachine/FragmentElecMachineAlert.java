package cn.meiqu.lainmonitor.aui.power.elecMachine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseRecycle.BaseOnRecycleClickListener;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.RecycleElecMachineAlartAdapter;
import cn.meiqu.lainmonitor.aui.FragmentAlert;
import cn.meiqu.lainmonitor.aui.FragmentControl;
import cn.meiqu.lainmonitor.bean.ElecMachine;
import cn.meiqu.lainmonitor.bean.ElecMachineAlart;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentElecMachineAlert extends FragmentAlert implements BaseOnRecycleClickListener {
    String action_getData = className + API.getElecMachineAlart;
    ArrayList<ElecMachineAlart> TempAlarts = new ArrayList<>();
    RecycleElecMachineAlartAdapter adapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleElecMachineAlartAdapter(getActivity(), TempAlarts);
        adapter.setClickListener(this);
        return adapter;
    }

    @Override
    public View getTopView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.recycle_alart_type1, null);
    }


    public void handleData(String data) {
        ArrayList<ElecMachineAlart> temps = new Gson().fromJson(data, new TypeToken<ArrayList<ElecMachineAlart>>() {
        }.getType());
        TempAlarts.clear();
        TempAlarts.addAll(temps);
        adapter.notifyDataSetChanged();
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getElecMachineAlart(deviceId, start, end, className);
    }

    @Override
    public String[] getDeviceNames() {
        ArrayList<ElecMachine> tempReals = ((FragmentElecMachine) ((FragmentControl) getParentFragment()).fragments.get(0)).ElecMachines;
        String names[] = new String[tempReals.size() + 1];
        for (int i = 1; i < names.length; i++) {
            names[i] = tempReals.get(i - 1).getEmdName();
        }
        return names;
    }

    @Override
    public String getDeviceId(int position) {
        ArrayList<ElecMachine> tempReals = ((FragmentElecMachine) ((FragmentControl) getParentFragment()).fragments.get(0)).ElecMachines;
        return tempReals.get(position - 1).getEmdId() + "";
    }


    @Override
    public void OnRecycleItemClick(int position) {
        showAlart("报警信息\r\n" + TempAlarts.get(position).getEmaInfo());
    }
}
