package cn.meiqu.lainmonitor.aui.env.temp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseUi.BaseFragment;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.baseproject.util.LogUtil;
import cn.meiqu.baseproject.util.TimeUtil;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.aui.FragmentControl;
import cn.meiqu.lainmonitor.bean.TempHistroy;
import cn.meiqu.lainmonitor.bean.TempReal;

/**
 * Created by Fatel on 16-6-2.
 */
public class FragmentTempChart extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnChartValueSelectedListener {
    public String className = getClass().getName() + Math.random() * 10000;
    String action_getData = className + API.getTemoHistroyData;
    String start = "";
    String end = "";
    long startStamp = 0;
    long endStamp = 0;
    String deviceId = "1";
    private EditText mTvStart;
    private EditText mTvEnd;
    private EditText mTvRange;
    private EditText mTvDevice;
    String timeRange[] = {"当天", "本周", "本月"};
    int currentTimeRange = 0;
    ArrayList<TempHistroy> tempHistroys = new ArrayList<>();

    private void assignSearchViews() {
        mTvStart = (EditText) findViewById(R.id.tv_start);
        mTvEnd = (EditText) findViewById(R.id.tv_end);
        mTvRange = (EditText) findViewById(R.id.tv_range);
        mTvDevice = (EditText) findViewById(R.id.tv_device);
        mTvStart.setOnClickListener(this);
        mTvEnd.setOnClickListener(this);
        mTvRange.setOnClickListener(this);
        mTvDevice.setOnClickListener(this);

        if (getDeviceNames().length != 0) {
            mTvDevice.setText(getDeviceNames()[0] + "");
            deviceId = "" + getDeviceId(0);
        }
        setSelectRange();

    }

    LineChart mChart;

    private void assignViews() {
        setSwipeRefresh(R.id.swipe, this);
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((ViewGroup) v.getParent()).requestDisallowInterceptTouchEvent(true);
                ((ViewGroup) v.getParent().getParent()).requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initReceiver(new String[]{action_getData});
        if (contain == null) {
            contain = inflater.inflate(R.layout.f_temp_chart, null);
            assignSearchViews();
            assignViews();
            initChart();
        }
        request();
        return contain;
    }

    public void initChart() {
        mChart.setOnChartValueSelectedListener(this);
        // no description text
        mChart.setDescription("");
//        mChart.setNoDataTextDescription("没有数据");
        mChart.setNoDataText("没有数据");
        // enable touch gestures
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setScaleYEnabled(false);
        mChart.setDrawGridBackground(true);
        mChart.setHighlightPerDragEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        // set an alternative background color
        mChart.setBackgroundColor(getResources().getColor(R.color.window_background));


//        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
//        l.setTypeface(tf);
        l.setTextSize(12f);
        l.setTextColor(getResources().getColor(R.color.black3));
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_INSIDE);
        l.setXOffset(30f);
        l.setFormSize(20);
        XAxis xAxis = mChart.getXAxis();
//        xAxis.setTypeface(tf);
        xAxis.setTextSize(8f);
        xAxis.setTextColor(getResources().getColor(R.color.black3));
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setSpaceBetweenLabels(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(80);
        xAxis.setLabelsToSkip(0);
        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(getResources().getColor(R.color.colorAccent));
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setLabelCount(11, true);
        YAxis rightAxis = mChart.getAxisRight();
//        rightAxis.setTypeface(tf);
        rightAxis.setTextColor(getResources().getColor(R.color.colorPrimary));
        rightAxis.setAxisMaxValue(100);
        rightAxis.setAxisMinValue(0);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        rightAxis.setLabelCount(11, true);
        //设置数据
        setData();
        mChart.animateX(600);

    }

    private void setData() {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < tempHistroys.size(); i++) {
            if (i > 100) {
                break;
            }
            xVals.add((tempHistroys.get(i).getEhmTime()) + "");
        }
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < tempHistroys.size(); i++) {
            if (i > 100) {
                break;
            }
            yVals1.add(new Entry((float) tempHistroys.get(i).getEhmTemp(), i));
        }
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < tempHistroys.size(); i++) {
            if (i > 100) {
                break;
            }
            yVals2.add(new Entry((float) tempHistroys.get(i).getEhmHum(), i));
        }
        LineDataSet set1, set2;
//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
//            LogUtil.log("mChart.getData() != null");
//            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
//            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
//            mChart.getData().clearValues();
//            for (int i = 0; i < xVals.size(); i++) {
//                mChart.getData().addXValue(xVals.get(i));
//            }
//            set1.clear();
//            for (int i = 0; i < yVals1.size(); i++) {
//                set1.addEntry(yVals1.get(i));
//            }
//            set2.clear();
//            for (int i = 0; i < yVals2.size(); i++) {
//                set2.addEntry(yVals2.get(i));
//            }
//            mChart.notifyDataSetChanged();
//        } else {
        LogUtil.log("mChart.getData() == null");
        // create a dataset and give it a type
        set1 = new LineDataSet(yVals1, "温度");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(getResources().getColor(R.color.colorAccent));
        set1.setCircleColor(Color.WHITE);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        //set1.setFillFormatter(new MyFillFormatter(0f));
        //set1.setDrawHorizontalHighlightIndicator(false);
        //set1.setVisible(false);
        //set1.setCircleHoleColor(Color.WHITE);

        // create a dataset and give it a type
        set2 = new LineDataSet(yVals2, "湿度");
        set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set2.setColor(getResources().getColor(R.color.colorPrimary));
        set2.setCircleColor(Color.WHITE);
        set2.setLineWidth(2f);
        set2.setCircleRadius(3f);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.RED);
        set2.setDrawCircleHole(false);
        set2.setHighLightColor(Color.argb(00, 00, 00, 00));
        //set2.setFillFormatter(new MyFillFormatter(900f));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        // add the datasets
        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(getResources().getColor(R.color.black3));
        data.setValueTextSize(9f);
        // set data
        mChart.setData(data);
//        }
    }

    public void refreshChart() {
        setData();
        mChart.animateX(600);
//        mChart.invalidate();
    }

    private void request() {
        requestData(start, end, deviceId);
    }

    public void requestData(String start, String end, String deviceId) {
        HttpGetController.getInstance().getTempHistroyData(deviceId, start, end, className);
    }

    public void handleData(String data) {
        ArrayList<TempHistroy> temps = new Gson().fromJson(data, new TypeToken<ArrayList<TempHistroy>>() {
        }.getType());
        tempHistroys.clear();
        tempHistroys.addAll(temps);
//        for (int i = 0; i < 10; i++) {
//            tempHistroys.add(new TempHistroy("2015-09-08 12:35:00", tempHistroys.size() + 3, tempHistroys.size() + 5));
//        }
        refreshChart();
    }

    @Override
    public void onHttpHandle(String action, String data) {
        if (getHttpStatus(data)) {
            if (action.equals(action_getData)) {
                handleData(data);
            }
        }
    }


    @Override
    public void onRefresh() {
        request();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mTvStart.getId()) {
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
        }
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
        request();
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
                    request();
                }
            }
        }).create();
        alertDialog.show();
    }

    public String[] getDeviceNames() {
        ArrayList<TempReal> tempReals = ((FragmentTempReal) ((FragmentControl) getParentFragment()).fragments.get(0)).tempReals;
        String names[] = new String[tempReals.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = tempReals.get(i).getEhmName();
        }
        return names;
    }

    public String getDeviceId(int position) {
        ArrayList<TempReal> tempReals = ((FragmentTempReal) ((FragmentControl) getParentFragment()).fragments.get(0)).tempReals;
        return tempReals.get(position).getEhmId() + "";
    }

    public void showDevice() {
        final String[] names = getDeviceNames();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(names, currentTimeRange, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentTimeRange = which;
                dialog.dismiss();
                deviceId = getDeviceId(which);
                mTvDevice.setText(names[which]);
                request();
            }
        }).create();
        alertDialog.show();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight highlight) {
        mChart.centerViewToAnimated(e.getXIndex(), e.getVal(), mChart.getData().getDataSetByIndex(dataSetIndex).getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {

    }
}
