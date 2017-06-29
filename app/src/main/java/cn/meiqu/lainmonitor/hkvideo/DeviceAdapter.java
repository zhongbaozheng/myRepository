package cn.meiqu.lainmonitor.hkvideo;

import android.content.Context;
import android.widget.ImageView;


import java.util.ArrayList;

import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.common.BaseRecycleHolder;
import cn.meiqu.lainmonitor.common.CommonAdapter;


/**
 * Created by Administrator on 2017/6/28.
 */

public class DeviceAdapter extends CommonAdapter<DeviceBean> {

    private Context mContext;
    private ArrayList<DeviceBean> mBeanList = new ArrayList<>();

   public DeviceAdapter(Context context,ArrayList<DeviceBean> list){
       super(context, list);
       mContext = context;
       mBeanList = list;
    }
    @Override
    public void instanceOfViewHolder(BaseRecycleHolder holder, DeviceBean deviceBean, int position) {
        ImageView im = (ImageView) holder.getView(R.id.image);
        im.setImageBitmap(deviceBean.bitmap);
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
