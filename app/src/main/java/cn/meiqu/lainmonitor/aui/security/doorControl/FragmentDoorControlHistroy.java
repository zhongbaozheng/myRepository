package cn.meiqu.lainmonitor.aui.security.doorControl;

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
import cn.meiqu.lainmonitor.adapter.RecycleDoorControlAdapter;
import cn.meiqu.lainmonitor.aui.FragmentAlert;
import cn.meiqu.lainmonitor.bean.DoorControl;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentDoorControlHistroy extends FragmentAlert implements BaseOnRecycleClickListener {
    String action_getData = className + API.getDoorControlHistroy;
    ArrayList<DoorControl> DoorControls = new ArrayList<>();
    RecycleDoorControlAdapter adapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new RecycleDoorControlAdapter(getActivity(), DoorControls);
        adapter.setClickListener(this);
        return adapter;
    }

    @Override
    public View getTopView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.recycle_door_real, null);
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData(String start, String end, String deviceId) {
        showProgressDialog();
        HttpGetController.getInstance().getDoorControlHistroy(deviceId, start, end, className);
    }

    public void handleData(String data) {
        ArrayList<DoorControl> temps = new Gson().fromJson(data, new TypeToken<ArrayList<DoorControl>>() {
        }.getType());
        DoorControls.clear();
        DoorControls.addAll(temps);
        adapter.notifyDataSetChanged();
    }

    @Override
    public String[] getDeviceNames() {
        String names[] = new String[1];
        return names;
    }

    @Override
    public String getDeviceId(int position) {
//        ArrayList<NLocationWater> tempReals = ((FragmentNLwaterReal) ((FragmentControl) getParentFragment()).fragments.get(0)).waters;
        return "0";
    }

    @Override
    public void OnRecycleItemClick(int position) {
//        showAlart("报警信息\r\n" + DoorControls.get(position).getInfo());
    }
}
