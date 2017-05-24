package cn.meiqu.lainmonitor.aui.security.doorControl;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.adapter.RecycleDoorControlAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.bean.DoorControl;

/**
 * Created by Fatel on 16-5-10.
 */
public class FragmentDoorControlReal extends FragmentReal {
    String action_getData = className + API.getDoorControlReal;
    public ArrayList<DoorControl> waters = new ArrayList<>();
    RecycleDoorControlAdapter adapter;


    @Override
    public RecyclerView.Adapter getAdapter() {
        ViewGroup viewGBody = (ViewGroup) findViewById(R.id.lin_top);
        viewGBody.addView(LayoutInflater.from(getActivity()).inflate(R.layout.recycle_door_real, null), 0);
        adapter = new RecycleDoorControlAdapter(getActivity(), waters);
        return adapter;
    }

    @Override
    public String getAction() {

        return action_getData;
    }

    public void requestData() {
        HttpGetController.getInstance().getDoorControl(className);
    }

    public void handleData(String data) {
        ArrayList<DoorControl> temps = new Gson().fromJson(data, new TypeToken<ArrayList<DoorControl>>() {
        }.getType());
        if (!temps.isEmpty()) {
            waters.clear();
            waters.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }
}
