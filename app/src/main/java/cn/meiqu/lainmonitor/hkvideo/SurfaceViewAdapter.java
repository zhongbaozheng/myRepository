package cn.meiqu.lainmonitor.hkvideo;

import android.content.Context;
import android.view.SurfaceView;
import android.widget.ImageView;


import java.util.ArrayList;

import cn.meiqu.baseproject.util.Config;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.common.BaseRecycleHolder;
import cn.meiqu.lainmonitor.common.CommonAdapter;

/**
 * Created by Administrator on 2017/6/21.
 */

public class SurfaceViewAdapter extends CommonAdapter<DeviceBean> {

    private Context mContext;
    private ArrayList<DeviceBean> mBeanList = new ArrayList<>();


    private SurfaceView playView;


    public SurfaceViewAdapter(Context context,ArrayList<DeviceBean> list){
        super(context, list);
        mContext = context;
        mBeanList = list;
        Config.putInt("position",0);
    }

    @Override
    public void instanceOfViewHolder(BaseRecycleHolder holder, DeviceBean deviceBean, int position) {


        if(position+1>=16){
            Config.putInt("position",16);
        }


        deviceBean.channel = position+1;
        deviceBean.strIP = "192.168.1.65";
        deviceBean.nPort = 8000;
        deviceBean.strUser = "admin";
        deviceBean.strPsd = "lain123456";
        PlayAssistant assistant = new PlayAssistant((ImageView) holder.getView(R.id.image),false);
        assistant.play(deviceBean.strIP,  deviceBean.nPort,deviceBean.strUser,deviceBean.strPsd, deviceBean.channel,Config.getInt("position"));
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.capture_image;
    }

    @Override
    public int getItemOtherLayoutId() {
        return 0;
    }


}
