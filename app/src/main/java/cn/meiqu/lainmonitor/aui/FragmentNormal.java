package cn.meiqu.lainmonitor.aui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.meiqu.baseproject.baseUi.BaseFragment;
import cn.meiqu.lainmonitor.R;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2017/5/5.
 * We use a PtrClassicFrameLayout to refresh our data
 */

public abstract class FragmentNormal extends BaseFragment{

    public String className = getClass().getName() + Math.random() * 10000;
    public PtrClassicFrameLayout mPtr;

    public abstract String getAction();

    public String[] getActions() {
        return null;
    }

    public abstract void requestData();

    public abstract void handleData(String data);

    public abstract int getLayoutId();

    public abstract void  initViews();

    private void assignViews() {
        mPtr = (PtrClassicFrameLayout) findViewById(R.id.ptr_view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActions() == null) {
            initReceiver(new String[]{getAction()});
        } else {
            initReceiver(getActions());
        }
        if (contain == null) {
            contain = inflater.inflate(getLayoutId(), null);
            assignViews();
            initViews();
            mPtr.setPtrHandler(new PtrHandler() {
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return true;
                }

                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    requestData();
                }
            });
        }
        return contain;
    }

    @Override
    public void onResume() {
        requestData();
        super.onResume();
    }

    @Override
    public void onHttpHandle(String action, String data) {
            if (action.equals(getAction())) {
                handleData(data);
                mPtr.refreshComplete();
            }
    }

}
