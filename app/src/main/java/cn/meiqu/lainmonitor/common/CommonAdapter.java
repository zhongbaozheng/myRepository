package cn.meiqu.lainmonitor.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 针对于我们的RecycleView的适配器，致力于减少代码重复量:
 * 主要集成，传入convertView的类型、用T表示泛型的JavaBean、不用写Holder对象、ItemClick点击监听
 * Created by zhongbao on 2017/6/15.
 */

public abstract class CommonAdapter<T> extends BaseRecycleAdapter<BaseRecycleHolder> implements BaseRecycleHolder.RecycleViewItemClickListener{

    public interface Type{
       public static int ITEM_TYPE_0 = 0;
        public static int ITEM_TYPE_1 = 1;
    }


    private Context mContext;
    private ArrayList<T> mList;


    public CommonAdapter(Context context, ArrayList<T> list){
        mContext = context;
        mList = list;
    }

    /**
     * 创建我们的Holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == Type.ITEM_TYPE_1){
            return new BaseRecycleHolder(mContext,View.inflate(mContext,getItemOtherLayoutId(),null));
        }
        return new BaseRecycleHolder(mContext,View.inflate(mContext,getItemLayoutId(),null));
    }

    /**
     * 实例化，绑定我们的数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseRecycleHolder holder, int position) {
        holder.setOnRecycleViewItemClickListner(this);
        instanceOfViewHolder(holder,mList.get(position),position);
    }

    /**
     * instance of our itemViewHolder,so we can set our view by this
     * abstract Method
     * @param holder
     */
    public abstract void   instanceOfViewHolder(BaseRecycleHolder holder,T t,int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }


    /**
     * @return Item's default layout Id
     */
    public abstract int getItemLayoutId();

    public abstract int getItemOtherLayoutId();

    public Context getCommonAdaterContext(){
        return mContext;
    }

    public ArrayList<T> getCommonList(){
        return mList;
    }

    @Override
    public void OnRecycleItemClick(View view,int position) {
        getItemListener().OnItemClick(view,position);
    }
}
