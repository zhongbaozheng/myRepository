package cn.meiqu.lainmonitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.meiqu.baseproject.baseRecycle.BaseHolder;
import cn.meiqu.baseproject.baseRecycle.BaseOnRecycleClickListener;
import cn.meiqu.baseproject.baseRecycle.BaseRecycleAdapter;
import cn.meiqu.baseproject.view.RippleView;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.bean.HomePage;


public class RecycleHomeAdapter extends BaseRecycleAdapter {
    private Context mContent;
    private ArrayList<HomePage> homePages;

    public RecycleHomeAdapter(Context mContent, ArrayList<HomePage> homePages) {
        this.mContent = mContent;
        this.homePages = homePages;
    }

    private BaseOnRecycleClickListener clickListener;

    public BaseOnRecycleClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(BaseOnRecycleClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(View.inflate(mContent, R.layout.recycle_home, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).instanceView(position);
    }

    @Override
    public int getItemCount() {
        return homePages.size();
    }

    class Holder extends BaseHolder implements RippleView.OnRippleCompleteListener {
        public Holder(View itemView) {
            super(itemView);
            ((RippleView) itemView).setOnRippleCompleteListener(this);
        }


        TextView mTv;

        public void assignViews() {
            mTv = (TextView) findViewById(R.id.tv);
        }


        @Override
        public void instanceView(final int position) {
            itemView.setAlpha(0.0f);
            itemView.setScaleX(0.0f);
            itemView.animate().alpha(1.0f).scaleX(1.0f).setDuration(100 * position).start();
            String name = homePages.get(position).getName();
            if (name.length() > 3) {
                name = name.substring(0, 2) + "\n" + name.substring(2, name.length());
            }
            mTv.setText("" + name);
        }

        @Override
        public void onComplete(RippleView rippleView) {
            if (getClickListener() != null) {
                getClickListener().OnRecycleItemClick(getPosition());
            }
        }
    }
}
