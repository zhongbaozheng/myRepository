package cn.meiqu.lainmonitor.aui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import cn.meiqu.baseproject.baseUi.BaseFragment;
import cn.meiqu.baseproject.util.LogUtil;
import cn.meiqu.baseproject.util.TimeUtil;
import cn.meiqu.baseproject.view.superrecyclerview.SuperRecyclerView;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.untils.DividerItemDecoration;
import cn.meiqu.lainmonitor.untils.SpaceItemDecoration;

/**
 * Created by Administrator on 2017/5/9.
 */

public abstract class FragmentAlarm2 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public String className = getClass().getName() + Math.random() * 10000;
    public SuperRecyclerView mRecycleV;
    public FloatingActionButton mFab;
    public ViewGroup viewGBody;

    public abstract RecyclerView.Adapter getAdapter();

    public abstract View getTopView();

    public abstract String getAction();

    public String[] getActions() {
        return null;
    }

    public abstract void requestData();

    public abstract void handleData(String data);

    public abstract String[] getDeviceNames();

    public abstract String getDeviceId(int position);

    String start = "";
    String end = "";
    long startStamp = 0;
    long endStamp = 0;
    String deviceId = "0";
    private EditText mTvStart;
    private EditText mTvEnd;
    private EditText mTvRange;
    private EditText mTvDevice;
    String timeRange[] = {"当天", "本周", "本月", "今年"};
    int currentTimeRange = 3;

    private void assignSearchViews() {
        mTvStart = (EditText) findViewById(R.id.tv_start);
        mTvEnd = (EditText) findViewById(R.id.tv_end);
        mTvRange = (EditText) findViewById(R.id.tv_range);
        mTvDevice = (EditText) findViewById(R.id.tv_device);
        mTvStart.setOnClickListener(this);
        mTvEnd.setOnClickListener(this);
        mTvRange.setOnClickListener(this);
        mTvDevice.setOnClickListener(this);

        setSelectRange();

    }

    private void assignViews() {
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        //
        viewGBody = (ViewGroup) findViewById(R.id.lin_top);
        viewGBody.addView(getTopView(), 1);
        mRecycleV = (SuperRecyclerView) findViewById(R.id.recycleV);
        mRecycleV.setRefreshListener(this);
        mRecycleV.addItemDecoration(new SpaceItemDecoration(20));
        mRecycleV.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRecycleV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleV.setOnMoreListener(null);
        mRecycleV.setAdapter(getAdapter());

        //

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActions() == null) {
            initReceiver(new String[]{getAction()});
        } else {
            initReceiver(getActions());
        }
        if (contain == null) {
            contain = inflater.inflate(R.layout.f_alart, null);
            assignViews();
            assignSearchViews();
        }
        requestData();
        return contain;
    }

    @Override
    public void onResume() {
        requestData();
        super.onResume();
    }

    @Override
    public void onHttpHandle(String action, String data) {
        mRecycleV.setRefreshing(false);
        if (getHttpStatus(data)) {
            if (action.equals(getAction())) {
                handleData(data);
            }
        }
    }

    public void onClickAdd() {

    }


    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mFab.getId()) {
            onClickAdd();
        } else if (v.getId() == mTvStart.getId()) {
            showStart();
        } else if (v.getId() == mTvEnd.getId()) {
            showEnd();
        } else if (v.getId() == mTvDevice.getId()) {
            showDevice();
        } else if (v.getId() == mTvRange.getId()) {
            showRange();
        }

    }

    private void setSelectRange() {
        if (currentTimeRange == 0) {
            startStamp = System.currentTimeMillis() - Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * (long) 60 * 60 * 1000 - Calendar.getInstance().get(Calendar.MINUTE) * 60 * 1000;
        } else if (currentTimeRange == 1) {
            startStamp = System.currentTimeMillis() - Calendar.getInstance().get(Calendar.DAY_OF_WEEK) * (long) 24 * 60 * 60 * 1000 - Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 - Calendar.getInstance().get(Calendar.MINUTE) * 60 * 1000;
        } else if (currentTimeRange == 2) {
            startStamp = System.currentTimeMillis() - Calendar.getInstance().get(Calendar.DAY_OF_MONTH) * (long) 24 * 60 * 60 * 1000 - Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 - Calendar.getInstance().get(Calendar.MINUTE) * 60 * 1000;
        } else if (currentTimeRange == 3) {
            startStamp = System.currentTimeMillis() - Calendar.getInstance().get(Calendar.DAY_OF_YEAR) * (long) 24 * 60 * 60 * 1000 - Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 - Calendar.getInstance().get(Calendar.MINUTE) * 60 * 1000;
        }
        LogUtil.log("Calendar.DAY_OF_MONTH=" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        endStamp = System.currentTimeMillis();
        start = TimeUtil.getTime(startStamp);
        end = TimeUtil.getTime(endStamp);
        mTvRange.setText(timeRange[currentTimeRange]);
    }

    private void onSelectTime() {
        start = TimeUtil.getTime(startStamp);
        end = TimeUtil.getTime(endStamp);
        mTvStart.setText("" + TimeUtil.getTime(startStamp, TimeUtil.DATE_FORMAT_DATE));
        mTvEnd.setText("" + TimeUtil.getTime(endStamp, TimeUtil.DATE_FORMAT_DATE));
        requestData();
    }

    public void showStart() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startStamp);
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.setTimeInMillis(0);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startStamp = calendar.getTimeInMillis();
                if (startStamp > endStamp) {
                    endStamp = System.currentTimeMillis();
                }
                onSelectTime();
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void showEnd() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endStamp);
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.setTimeInMillis(0);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endStamp = calendar.getTimeInMillis();
                onSelectTime();
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMinDate(startStamp);
        datePickerDialog.show();
    }

    public void showRange() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(timeRange, currentTimeRange, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which != currentTimeRange) {
                    currentTimeRange = which;
                    dialog.dismiss();
                    setSelectRange();
                    requestData();
                }
            }
        }).create();
        alertDialog.show();
    }

    public void showDevice() {
        final String[] names = getDeviceNames();
        names[0] = "所有设备";
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(names, currentTimeRange, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentTimeRange = which;
                dialog.dismiss();
                if (currentTimeRange == 0) {
                    deviceId = "0";
                } else {
                    deviceId = getDeviceId(which);
                }
                mTvDevice.setText(names[which]);
                requestData();
            }
        }).create();
        alertDialog.show();
    }

    public void showAlart(String info) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage(info).setPositiveButton("确定", null).create();
        alertDialog.show();
    }
}

