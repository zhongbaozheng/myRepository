package cn.meiqu.lainmonitor.aui.env.nlwater;

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
import cn.meiqu.lainmonitor.adapter.RecycleAlertType2Adapter;
import cn.meiqu.lainmonitor.aui.FragmentAlert;
import cn.meiqu.lainmonitor.aui.FragmentControl;
import cn.meiqu.lainmonitor.bean.AlertType2;
import cn.meiqu.lainmonitor.bean.NLocationWater;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentNLwaterAlart extends FragmentAlert implements BaseOnRecycleClickListener {
    String action_getData = className + API.getNoLocationWaterAlart;
    ArrayList<AlertType2> AlertType2s = new ArrayList<>();
    RecycleAlertType2Adapter adapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleAlertType2Adapter(getActivity(), AlertType2s);
        adapter.setClickListener(this);
        return adapter;
    }

    @Override
    public View getTopView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.recycle_alart_type2, null);
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getNoLocationWaterAlart(deviceId, start, end, className);
    }

    public void handleData(String data) {
        ArrayList<AlertType2> temps = new Gson().fromJson(data, new TypeToken<ArrayList<AlertType2>>() {
        }.getType());
        AlertType2s.clear();
        AlertType2s.addAll(temps);
        adapter.notifyDataSetChanged();
    }

    @Override
    public String[] getDeviceNames() {
        ArrayList<NLocationWater> tempReals = ((FragmentNLwaterReal) ((FragmentControl) getParentFragment()).fragments.get(0)).waters;
        String names[] = new String[tempReals.size() + 1];
        for (int i = 1; i < names.length; i++) {
            names[i] = tempReals.get(i - 1).getName();
        }
        return names;
    }

    @Override
    public String getDeviceId(int position) {
        ArrayList<NLocationWater> tempReals = ((FragmentNLwaterReal) ((FragmentControl) getParentFragment()).fragments.get(0)).waters;
        return tempReals.get(position - 1).getId() + "";
    }

    @Override
    public void OnRecycleItemClick(int position) {
        showAlart("报警信息\r\n" + AlertType2s.get(position).getInfo());
    }
}
