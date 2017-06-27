package cn.meiqu.baseproject.baseRecycle;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.meiqu.baseproject.util.Config;

/**
 * Created by Fatel on 15-9-23.
 */
public abstract class BaseHolder extends RecyclerView.ViewHolder {

    public View itemView;

    public BaseHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        assignViews();
    }

    public View findViewById(int id) {
        return itemView.findViewById(id);
    }

    public void assignViews() {
    }


    public abstract void instanceView(int position);

}
