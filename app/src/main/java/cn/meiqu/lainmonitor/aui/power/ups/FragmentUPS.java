package cn.meiqu.lainmonitor.aui.power.ups;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.FragmentWeb;
import cn.meiqu.lainmonitor.adapter.RecycleUpsAdapter;
import cn.meiqu.lainmonitor.adapter.RecylceAccurateAirAdapter;
import cn.meiqu.lainmonitor.aui.FragmentVertial;
import cn.meiqu.lainmonitor.bean.AirAcurrateBean;
import cn.meiqu.lainmonitor.bean.UPSBean;

/**
 * Created by Fatel on 16-5-27.
 */
public class FragmentUPS extends FragmentVertial {

    String action_getData = className + API.UpsUrl;
    private ArrayList<UPSBean> mUPSBeanList = new ArrayList<UPSBean>();
    private RecycleUpsAdapter mAdapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new RecycleUpsAdapter(getActivity(),mUPSBeanList);
        return mAdapter;
    }

    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData() {
        HttpGetController.getInstance().getUPS(className);
    }

    @Override
    public void handleData(String data) {
        ArrayList<UPSBean> temps = new Gson().
                fromJson(data,new TypeToken<ArrayList<UPSBean>>(){}.getType());
        if(temps != null){
            mUPSBeanList.clear();
            mUPSBeanList.addAll(temps);
            mAdapter.notifyDataSetChanged();
        }
    }
}
