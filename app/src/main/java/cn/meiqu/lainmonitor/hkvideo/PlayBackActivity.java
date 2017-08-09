package cn.meiqu.lainmonitor.hkvideo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_TIME;
import java.util.Calendar;

import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.util.Config;
import cn.meiqu.baseproject.util.TimeUtil;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.untils.TimePhrase;

/**
 * Created by Administrator on 2017/7/14.
 */

public class PlayBackActivity extends BaseActivity implements View.OnClickListener{

    private EditText mStartTime;
    private EditText mEndTime;
    private EditText mStartmin;
    private EditText mEndmin;

    private TextView mConcert;
    private SurfaceView mSurface;

    private long startStand;
    private long endStand;

    private boolean flag = true;
    private boolean isPlay  = true;

    private boolean isFull = false;

    FrameLayout.LayoutParams lp;
    FrameLayout.LayoutParams lp2;
    LinearLayout linearLayout;

    private Thread mProgressThread; //进度条线程
    private  int  isStop = -1;   //-1表示没有点击的状态，0表示继续，1停止
    private long progressMillns; //进度条对应的时间
    private float mProgress;

    public int loginId;


    ImageView iV;
    ImageView iV2;

    String start = "";
    NET_DVR_TIME time;  //查询时间类
    String end = "";
    NET_DVR_TIME time2;
    public PlayAssistant assistant;  //HKSDK控制方法类

    String showTime = "";
    SeekBar seekBar;
    //更新进度条
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(msg.what == 1){
                mProgress = (float)msg.obj;
                seekBar.setProgress((int) mProgress);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_playback);
        Config.register(this);
        assignView();
        HCNetSDK.getInstance().NET_DVR_Init();
        assistant = new PlayAssistant(mSurface);
        getLoginId();
        addViews();
    }

    public void getLoginId(){
        loginId = assistant.login("192.168.1.65",8000,"admin","lain123456",Config.getInt("channel"));
    }


    public void getShowTime(long startStand ,long endStand,int progress){

        long show = (long) ((endStand - startStand)*progress*1.0/100);
        showTime = TimeUtil.getTime(show+startStand,TimeUtil.DEFAULT_DATE_FORMAT);
        toast(showTime);
    }



    private void addViews(){

        iV = new ImageView(this);
        iV.setMinimumHeight(100);
        iV.setMinimumWidth(100);
        iV.setImageResource(R.mipmap.ic_stop);
        iV.setBackgroundColor(getResources().getColor(R.color.color_transparents));
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.CENTER ;
        lp1.setMargins(0,60,0,0);
        addContentView(iV,lp1);

        iV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                assistant.poausPlayBack();

                if(checkDate()){

                    if(!isPlay){
                        iV.setImageResource(R.mipmap.ic_open);
                        assistant.goAheadPlayBack();
                        isPlay = true;
                        isStop = 0;
                        progressThreadNotify();
                    }else{

                        isPlay = false;
                        iV.setImageResource(R.mipmap.ic_stop);
                        assistant.poausPlayBack();
                        isStop = 1;

                    }
                }

            }
        });


        iV2 = new ImageView(this);
        iV2.setMinimumHeight(100);
        iV2.setMinimumWidth(100);
        iV2.setImageResource(R.mipmap.ic_full);

        iV2.setBackgroundColor(getResources().getColor(R.color.color_transparents));
        lp2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp2.gravity = Gravity.BOTTOM | Gravity.RIGHT ;
        lp2.setMargins(0,0,0,320);
        addContentView(iV2,lp2);

        iV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //全屏
                if(!isFull){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    iV2.setImageResource(R.mipmap.ic_full_exit);
                    isFull = true;
                }else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    iV2.setImageResource(R.mipmap.ic_full);
                    isFull = false;
                }
            }
        });


        seekBar = new SeekBar(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //在这里获取我们的进度，最终开始播放
                toast("final progress"+seekBar.getProgress());
                getShowTime(startStand,endStand,seekBar.getProgress());
                assistant.stopPlayBack();
                TimePhrase phrase2 = getTimePhrase(showTime);
                final NET_DVR_TIME time3 = new NET_DVR_TIME();
                time3.dwYear = phrase2.year;
                time3.dwMonth = phrase2.month;
                time3.dwDay  = phrase2.day;
                time3.dwHour = phrase2.hour;
                time3.dwMinute = phrase2.min;
                time3.dwSecond = phrase2.second;
                assistant.seekPlayBack(time3,time2,loginId);
                //赋值，继续开启
                progressMillns = (long) ((endStand - startStand)*seekBar.getProgress()*1.0/100);
                isStop = -1;
            }
        });
        seekBar.setBackgroundColor(Color.WHITE);
        seekBar.setMinimumHeight(100);
        seekBar.setMinimumWidth(100);
        lp  = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM | Gravity.LEFT;
        lp.rightMargin = 150;
        lp.bottomMargin = 360;
        lp.leftMargin = 20;
        addContentView(seekBar,lp);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
            lp.setMargins(0,0,150,60);
            lp2.setMargins(0,0,0,20);
            seekBar.setLayoutParams(lp);
            iV2.setLayoutParams(lp2);
            toolbar.setVisibility(View.GONE);
            iV2.setImageResource(R.mipmap.ic_full_exit);
            linearLayout.setVisibility(View.GONE);

        }
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            toolbar.setVisibility(View.VISIBLE);
            lp.setMargins(20,0,150,360);
            lp2.setMargins(0,0,0,320);
            seekBar.setLayoutParams(lp);
            iV2.setLayoutParams(lp2);
            iV2.setImageResource(R.mipmap.ic_full);
            linearLayout.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.start_time_ed){
            showStartDate();
        }
        if(view.getId() == R.id.end_time_ed){
            showEndDate();
        }
        if(view.getId() == R.id.concern_tv){

            if(checkDate()){
                //将开启我们回放
                assistant.stopPlayBack();
                setData();
                iV.setImageResource(R.mipmap.ic_open);
                seekBar.setProgress(0);
                createProgessThread();
                mProgressThread.start();
                progressMillns = 0;
            }

        }
        if(view.getId() == R.id.start_min_ed){
            showStartTime();
        }
        if(view.getId() == R.id.end_min_ed){
            showEndTime();
        }

        if(view.getId() == R.id.surfaceviewId){
            if(flag){
                iV2.setVisibility(View.GONE);
                iV.setVisibility(View.GONE);
                seekBar.setVisibility(View.GONE);
                flag = false;
            }else{
                iV2.setVisibility(View.VISIBLE);
                iV.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                flag = true;
            }
        }

    }


    public TimePhrase getTimePhrase(String date){

        TimePhrase timePhrase = new TimePhrase();

        String[] str = date.split(" ");
        Log.e("strs",str[0].toString()+" | "+str[1].toString());
        String[] s1 = str[0].split("-");
        for(int j=0;j<s1.length;j++){
            timePhrase.year = Integer.parseInt(s1[0]);
            timePhrase.month = Integer.parseInt(s1[1]);
            timePhrase.day = Integer.parseInt(s1[2]);
        }
        String[] s2 = str[1].split(":");
        for(int j=0;j<s1.length;j++){
            timePhrase.hour = Integer.parseInt(s2[0]);
            timePhrase.min = Integer.parseInt(s2[1]);
            timePhrase.second = Integer.parseInt(s2[2]);

        }

        return timePhrase;
    }

    //控制进度条继续
    public synchronized void progressThreadNotify(){

        synchronized (mProgressThread){

            if(isStop == 0){
                mProgressThread.notify();
            }else {
                toast("wating wait...");
            }
        }

    }


    //创建控制进度条线程
    public void createProgessThread(){
        mProgressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        Thread.sleep(1000);
                        progressMillns = progressMillns + 1*1000;
                        mProgress = progressMillns*100/(endStand - startStand);
                        Message message = new Message();
                        message.what = 1;
                        message.obj = mProgress;
                        mHandler.sendMessage(message);
                        //线程锁，等待唤醒
                        synchronized (mProgressThread){
                            //控制暂停
                            Log.e("isStop",isStop+"");
                            if(isStop == 1){
                                mProgressThread.wait();
                            }
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void setData(){

        TimePhrase phrase1 = getTimePhrase(start);

        time = new NET_DVR_TIME();
        time.dwYear = phrase1.year;
        time.dwMonth = phrase1.month;
        time.dwDay  = phrase1.day;
        time.dwHour = phrase1.hour;
        time.dwMinute = phrase1.min;
        time.dwSecond = phrase1.second;

        TimePhrase phrase2 = getTimePhrase(end);
        time2 = new NET_DVR_TIME();
        time2.dwYear = phrase2.year;
        time2.dwMonth = phrase2.month;
        time2.dwDay  = phrase2.day;
        time2.dwHour = phrase2.hour;
        time2.dwMinute = phrase2.min;
        time2.dwSecond = phrase2.second;
        assistant.playback(assistant.setPlayBack(time,time2),loginId);

    }


    //核对我们的时间是否正确
    private boolean checkDate(){

        start = mStartTime.getText().toString()+" "+mStartmin.getText().toString()+":00";
        startStand = TimeUtil.getTimeMills(start);
        end = mEndTime.getText().toString()+ " "+mEndmin.getText().toString()+":00";
        endStand = TimeUtil.getTimeMills(end);

        if(endStand <= startStand){
            toast("请检查您的时间是否正确！");
            return false;
        }

        return true;
    }


    private  void assignView(){
        linearLayout = (LinearLayout) findViewById(R.id.ll_time);
        initTitle("视频回放");
        mStartTime = (EditText)findViewById(R.id.start_time_ed);
        mStartmin = (EditText)findViewById(R.id.start_min_ed);
        mEndTime = (EditText)findViewById(R.id.end_time_ed);
        mEndmin = (EditText)findViewById(R.id.end_min_ed);
        mConcert = (TextView) findViewById(R.id.concern_tv);
        mSurface = (SurfaceView)findViewById(R.id.surfaceviewId);
        mSurface.setOnClickListener(this);
        mStartTime.setOnClickListener(this);
        mStartmin.setOnClickListener(this);
        mEndTime.setOnClickListener(this);
        mEndmin.setOnClickListener(this);
        mConcert.setOnClickListener(this);
    }

    //时间选择器-日期
    public void showEndDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog
                datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mEndTime.setText("" + TimeUtil.getTime(calendar.getTimeInMillis(), TimeUtil.DATE_FORMAT_DATE));
            }
        }, year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    //时间选择器-小时-分钟
    public void showEndTime(){

        final Calendar calendar = Calendar.getInstance();

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hours,minutes);
                mEndmin.setText(""+TimeUtil.getTime(calendar.getTimeInMillis(),TimeUtil.MIN_DATE_FORMAT));
            }
        },hour,minute,true);
        timeDialog.show();

    }


    public void showStartDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog
                datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                onSelectDate(calendar.getTimeInMillis());
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void onSelectDate(long timeMinlls) {

        mStartTime.setText("" + TimeUtil.getTime(timeMinlls, TimeUtil.DATE_FORMAT_DATE));
        mEndTime.setText("" + TimeUtil.getTime(System.currentTimeMillis(), TimeUtil.DATE_FORMAT_DATE));

    }


    public void showStartTime(){

        final Calendar calendar = Calendar.getInstance();

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hours,minutes);
                onSelectTime(calendar.getTimeInMillis());
            }
        },hour,minute,true);
        timeDialog.show();


    }

    public void onSelectTime(long timeMinlls){

        mStartmin.setText(""+TimeUtil.getTime(timeMinlls,TimeUtil.MIN_DATE_FORMAT));
        mEndmin.setText(""+TimeUtil.getTime(System.currentTimeMillis(),TimeUtil.MIN_DATE_FORMAT));
    }


    @Override
    public void onHttpHandle(String action, String data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        assistant.Cleanup();
        mProgressThread.interrupt();
    }

    @Override
    public void initFragment() {

    }
}
