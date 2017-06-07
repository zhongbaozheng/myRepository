package cn.meiqu.lainmonitor.aui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.meiqu.baseproject.baseUi.BaseFragment;
import cn.meiqu.lainmonitor.MainActivity;
import cn.meiqu.lainmonitor.R;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static android.app.ProgressDialog.show;

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

    private ProgressDialog mDialog ;

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
        mDialog = ProgressDialog.show(getActivity(),"提示","正在加载...");
        requestData();
        super.onResume();
    }

    @Override
    public void onHttpHandle(String action, String data) {
            if (action.equals(getAction())) {
                handleData(data);
                mDialog.dismiss();
                mPtr.refreshComplete();
            }
    }

}
