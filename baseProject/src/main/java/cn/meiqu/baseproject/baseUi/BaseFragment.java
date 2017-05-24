package cn.meiqu.baseproject.baseUi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.R;
import cn.meiqu.baseproject.bean.MsgBean;
import cn.meiqu.baseproject.dialog.LoadingDialog;
import cn.meiqu.baseproject.dialog.SelectImageDialog;
import cn.meiqu.baseproject.util.StringUtil;
import cn.meiqu.baseproject.util.ToastUtil;


/**
 * Created by Fatel on 15-4-9.
 */
public abstract class BaseFragment extends Fragment {
    public LoadingDialog progressDialog = null;
    public BroadcastReceiver receiver;
    public View contain;
    //
    Toolbar toolbar;
    public TextView mTvTitle;
    public TextView mTvRight;
    public SwipeRefreshLayout swipeRefresh;


    public void initTitle(String title) {
        initTitle(new SpannableStringBuilder(title));
    }

    public void initTitle(SpannableStringBuilder title) {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).popBack();
            }
        });
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvRight = (TextView) findViewById(R.id.tv_title_more);
        setTitle(title);
    }

//    public void initTitleWithNavi(String title, View.OnClickListener listener) {
//        initTitle(title);
//        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.);
//        toolbar.setNavigationOnClickListener(listener);
//    }

    public void showMsg(String data){
        ArrayList<MsgBean> msgBeanArrayList = new ArrayList<>();
        ArrayList<MsgBean> temps = new Gson().fromJson(data,new TypeToken<ArrayList<MsgBean>>(){}.getType());
        if(temps!=null){
            msgBeanArrayList.clear();
            msgBeanArrayList.addAll(temps);
            MsgBean bean = msgBeanArrayList.get(0);
            Toast.makeText(getActivity(),bean.msg,Toast.LENGTH_SHORT).show();
        }
    }

    public void setTitleRight(String text, View.OnClickListener listener) {
        if (mTvRight != null) {
            mTvRight.setText(text + "");
            mTvRight.setVisibility(View.VISIBLE);
            mTvRight.setOnClickListener(listener);
        }
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setTitle(SpannableStringBuilder title) {
        mTvTitle.setText(title);
    }

    public void setSwipeRefresh(int id, SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        swipeRefresh = (SwipeRefreshLayout) findViewById(id);
//        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.window_background));
        swipeRefresh.setOnRefreshListener(onRefreshListener);
    }

    public void setSwipeRefreshing(boolean ing) {
        if (swipeRefresh != null) {
            if (ing != swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(ing);
            }
        }
    }

    public void showProgressDialog(String content) {
//        setSwipeRefreshing(true);
        if (progressDialog == null) {
            progressDialog = new LoadingDialog(getActivity());
        }
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void dismissProgressDialog() {
        setSwipeRefreshing(false);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public View findViewById(int id) {
        return contain.findViewById(id);
    }

    public void toast(String text) {
        if (getActivity() != null)
            ToastUtil.show(getActivity(), text);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        setSwipeRefreshing(false);
        super.onDestroyView();
        if (receiver != null)
            try {
                LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void showNoPop(Fragment f, int containerId) {
        getChildFragmentManager().beginTransaction().replace(containerId, f, f.getClass().getName())
                .commit();
    }

    public abstract void onHttpHandle(String action, String data);

    public boolean getHttpStatus(String data) {
        setSwipeRefreshing(false);
        dismissProgressDialog();
        if (StringUtil.isEmpty(data)) {
            ToastUtil.showNetWorkFailure(getActivity());
            return false;
        } else {
            return true;
        }
    }


    public void initReceiver(String[] filters) {
        receiver = new ActionReceiver();
        IntentFilter filter = new IntentFilter();
        for (String action : filters) {
            filter.addAction(action);
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, filter);
    }

    @Override
    public void startActivity(Intent intent) {
        getActivity().startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        getActivity().startActivityForResult(intent, requestCode);
    }

    class ActionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String data = intent.getStringExtra("data");
            onHttpHandle(action, data);
        }
    }

    public void onSelectImageReuslt(String path) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                // 如果是直接从相册获取
                case SelectImageDialog.ablumResult:
                    if (data != null) {
                        try {
                            Uri uri = data.getData();
                            String[] proj = {MediaStore.Images.Media.DATA};
                            Cursor actualimagecursor = getActivity().managedQuery(uri, proj, null,
                                    null, null);
                            int actual_image_column_index = actualimagecursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            actualimagecursor.moveToFirst();
                            onSelectImageReuslt(actualimagecursor
                                    .getString(actual_image_column_index));
                        } catch (Exception e) {

                        }

                    }
                    break;
                case SelectImageDialog.photographResult:
                    onSelectImageReuslt(SelectImageDialog.imagePath);
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void AddFragment(Fragment f, int containerId) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(containerId, f,f.getClass().getName());
//        getFragmentManager().popBackStack();//这个只用于我们关闭当前的fragment
        transaction.addToBackStack(null);//写了默认返回上一个fragment
        transaction.commit();
    }
}
