package cn.meiqu.lainmonitor.aui.env.temp;

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
import cn.meiqu.lainmonitor.adapter.RecycleTempAlertAdapter;
import cn.meiqu.lainmonitor.aui.FragmentAlert;
import cn.meiqu.lainmonitor.aui.FragmentControl;
import cn.meiqu.lainmonitor.bean.TempAlart;
import cn.meiqu.lainmonitor.bean.TempReal;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentTempAlert extends FragmentAlert implements BaseOnRecycleClickListener {
    String action_getData = className + API.getTempAlart;
    ArrayList<TempAlart> TempAlarts = new ArrayList<>();
    RecycleTempAlertAdapter adapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleTempAlertAdapter(getActivity(), TempAlarts);
        adapter.setClickListener(this);
        return adapter;
    }

    @Override
    public View getTopView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.recycle_alart_type1, null);
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getTempAlart(deviceId, start, end, className);
    }

    public void handleData(String data) {
        ArrayList<TempAlart> temps = new Gson().fromJson(data, new TypeToken<ArrayList<TempAlart>>() {
        }.getType());
        TempAlarts.clear();
        TempAlarts.addAll(temps);
        adapter.notifyDataSetChanged();
    }

    @Override
    public String[] getDeviceNames() {
        ArrayList<TempReal> tempReals = ((FragmentTempReal) ((FragmentControl) getParentFragment()).fragments.get(0)).tempReals;
        String names[] = new String[tempReals.size() + 1];
        for (int i = 1; i < names.length; i++) {
            names[i] = tempReals.get(i - 1).getEhmName();
        }
        return names;
    }

    @Override
    public String getDeviceId(int position) {
        ArrayList<TempReal> tempReals = ((FragmentTempReal) ((FragmentControl) getParentFragment()).fragments.get(0)).tempReals;
        return tempReals.get(position - 1).getEhmId() + "";
    }

    @Override
    public void OnRecycleItemClick(int position) {
        showAlart("报警信息\r\n" + TempAlarts.get(position).getEhaInfo());
    }
}
