package cn.meiqu.lainmonitor.aui.env.airJ;

import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.adapter.RecylceAccurateAirAdapter;
import cn.meiqu.lainmonitor.aui.FragmentReal;
import cn.meiqu.lainmonitor.aui.FragmentVertial;
import cn.meiqu.lainmonitor.bean.AirAcurrateBean;

/**
 * Created by Fatel on 16-5-27.
 */
public class FragmentAirJ extends FragmentVertial {

    String action_getData = className + API.accurateAirUrl;
    String action_isOpen  = className + API.kgjStateOpenUrl;
    String action_isClose   = className + API.kgjStateCloseUrl;

    private ArrayList<AirAcurrateBean> mAirAcurrateBeanList = new ArrayList<AirAcurrateBean>();
    private RecylceAccurateAirAdapter mAdapter;

    @Override
    public void onHttpHandle(String action, String data) {
        if(action.equals(action_getData)){
            handleGetData(data);
        }
        if(action.equals(action_isOpen)){
            showMsg(data);
        }
        if(action.equals(action_isClose)){
            showMsg(data);
        }
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new RecylceAccurateAirAdapter(getActivity(),mAirAcurrateBeanList,className);
        initReceiver(new String[]{action_getData,action_isClose,action_isOpen});
        return mAdapter;
    }


    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData() {
        HttpGetController.getInstance().getAirAcurrate(className);
    }

    @Override
    public void handleData(String data) {

    }

    public void handleGetData(String data){
        ArrayList<AirAcurrateBean> temps = new Gson().
                fromJson(data,new TypeToken<ArrayList<AirAcurrateBean>>(){}.getType());
        if(temps != null){
            mAirAcurrateBeanList.clear();
            mAirAcurrateBeanList.addAll(temps);
            mAdapter.notifyDataSetChanged();
        }
    }
}
