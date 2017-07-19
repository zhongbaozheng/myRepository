package cn.meiqu.lainmonitor.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhongbao on 2017/6/14.
 */

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleHolder> {

    public interface ItemClickListener{
        public void OnItemClick(View view, int position);
    }

    private ItemClickListener listener;

    public ItemClickListener setOnItemClickListner(ItemClickListener OnItemClickListener){
        return listener = OnItemClickListener;
    }

    public ItemClickListener getItemListener(){
        return listener;
    }

}
