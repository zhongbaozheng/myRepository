package cn.meiqu.lainmonitor.hkvideo;

import android.content.Intent;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.meiqu.baseproject.util.Config;

/**
 * Created by Administrator on 2017/8/8.
 */

public class CameraPlayActivity extends SimpleActivity implements View.OnTouchListener {

    private PointF mPoint;

    @Override
    public void play() {
        assistant.play("192.168.1.64",8000,"admin","admin123", Config.getInt("channel"));
        surfaceView.setOnTouchListener(this);
        surfaceView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });
    }

    @Override
    public void startIntent() {
        startActivity(new Intent(this,CameraPBActivity.class));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){

            case MotionEvent.ACTION_DOWN:
                mPoint = new PointF();
                mPoint.set(motionEvent.getRawX(),motionEvent.getRawY());
                break;

            case MotionEvent.ACTION_MOVE:

                if(mPoint.x-motionEvent.getRawX()>150 && mPoint.y-motionEvent.getRawY()<-150){
                        //开启左上转
                    Log.e("direction","左上");
                    assistant.startPTZControl(4);

                }else if(mPoint.x-motionEvent.getRawX()>150 && mPoint.y-motionEvent.getRawY()>150){
                    Log.e("direction","左下");
                    assistant.startPTZControl(6);
                       //开启左下
                }else if(mPoint.x-motionEvent.getRawX()<-150 && mPoint.y-motionEvent.getRawY()<-150){
                    Log.e("direction","右上");
                    assistant.startPTZControl(5);
                    //右上
                }else if(mPoint.x-motionEvent.getRawX()<-150 && mPoint.y-motionEvent.getRawY()>150){
                    //右下
                    Log.e("direction","右下");
                    assistant.startPTZControl(7);
                } else if((mPoint.x-motionEvent.getRawX()>150)){
                    //左转
                    Log.e("direction","左");
                    assistant.startPTZControl(3);
                }else if(mPoint.y-motionEvent.getRawY()<-150) {
                    //上偏
                    Log.e("direction","上");
                    assistant.startPTZControl(0);
                }else if(mPoint.y-motionEvent.getRawY()>150){
                    //下偏
                    Log.e("direction","下");
                    assistant.startPTZControl(1);
                }else if(mPoint.x-motionEvent.getRawX()<-150){
                    Log.e("direction","右");
                    //右转
                    assistant.startPTZControl(2);
                }

                super.onTouchEvent(motionEvent);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                assistant.stopPTZControl(7);
                assistant.stopPTZControl(6);
                assistant.stopPTZControl(5);
                assistant.stopPTZControl(4);
                assistant.stopPTZControl(3);
                assistant.stopPTZControl(2);
                assistant.stopPTZControl(1);
                assistant.stopPTZControl(0);
                break;
        }
        return super.onTouchEvent(motionEvent);
    }
}
