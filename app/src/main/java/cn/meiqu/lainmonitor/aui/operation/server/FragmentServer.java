package cn.meiqu.lainmonitor.aui.operation.server;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.lainmonitor.R;
import cn.meiqu.lainmonitor.aui.FragmentNormal;
import cn.meiqu.lainmonitor.bean.ServerBean;

/**
 * Created by Administrator on 2017/5/5.
 */

public class FragmentServer extends FragmentNormal{

    //服务器界面
    String action_getData = className + API.ServerUrl;
    private ArrayList<ServerBean> mList = new ArrayList<ServerBean>();
    private TextView mCPU1;
    private TextView mCPU2;
    private TextView mCPU3;
    private TextView mCPU4;
    private TextView mCPU5;
    private TextView mCPU6;
    private TextView mCPU7;

    private TextView mSys1;
    private TextView mSys2;
    private TextView mSys3;

    private TextView mSwap1;
    private TextView mSwap2;
    private TextView mSwap3;

    private TextView mDisk1;
    private TextView mDisk2;
    private TextView mDisk3;
    private TextView mDisk4;

    private ProgressBar mProgress;






    @Override
    public String getAction() {
        return action_getData;
    }

    @Override
    public void requestData() {
        HttpGetController.getInstance().getServerData(className);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void handleData(String data) {
        ArrayList<ServerBean> temps = new Gson().
                fromJson(data,new TypeToken<ArrayList<ServerBean>>(){}.getType());
        if(temps != null){
            mList.clear();
            mList.addAll(temps);
        }
        setData(mList.get(0));
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_server;
    }

    @Override
    public void initViews() {
        //cpu
        mCPU1 = (TextView)findViewById(R.id.cpu_tv1);
        mCPU2 = (TextView)findViewById(R.id.cpu_tv2);
        mCPU3 = (TextView)findViewById(R.id.cpu_tv3);
        mCPU4 = (TextView)findViewById(R.id.cpu_tv4);
        mCPU5 = (TextView)findViewById(R.id.cpu_tv5);
        mCPU6 = (TextView)findViewById(R.id.cpu_tv6);
        mCPU7 = (TextView)findViewById(R.id.cpu_tv7);

        //System
        mSys1 = (TextView)findViewById(R.id.sys_tv1);
        mSys2 = (TextView)findViewById(R.id.sys_tv2);
        mSys3 = (TextView)findViewById(R.id.sys_tv3);

        //Swap
        mSwap1 = (TextView)findViewById(R.id.swap_tv1);
        mSwap2 = (TextView)findViewById(R.id.swap_tv2);
        mSwap3 = (TextView)findViewById(R.id.swap_tv3);

        //disk
        mDisk1 = (TextView)findViewById(R.id.disk_tv1);
        mDisk2 = (TextView)findViewById(R.id.disk_tv2);
        mDisk3 = (TextView)findViewById(R.id.disk_tv3);
        mDisk4 = (TextView)findViewById(R.id.disk_tv4);
        mProgress = new ProgressBar(getActivity());

    }

    private void setData(ServerBean serverBean){

        mCPU1.setText("连接总量\n\n"+serverBean.connectionSum);
        mCPU2.setText("总使用率\n\n"+serverBean.cpuCombined+"%");
        mCPU3.setText("用户使用率\n\n"+serverBean.cpuUser+"%");
        mCPU4.setText("系统使用率\n\n"+serverBean.cpuSys+"%");
        mCPU5.setText("当前等待率\n\n"+serverBean.cpuWait+"%");
        mCPU6.setText("当前错误率\n\n"+serverBean.cpuNice+"%");
        mCPU7.setText("当前空置率\n\n"+serverBean.cpuIdle+"%");

        mSys1.setText("总物理内存\n\n"+serverBean.memTotal);
        mSys2.setText("已使用\n\n"+serverBean.memUsed);
        mSys3.setText("剩余\n\n"+serverBean.memFree);

        mSwap1.setText("交换区总量\n\n"+serverBean.swapTotal);
        mSwap2.setText("已使用\n\n"+serverBean.swapUsed);
        mSwap3.setText("剩余\n\n"+serverBean.swapFree);
        mDisk1.setText("      "+serverBean.diskName[0]+"\n\n总容量："+serverBean.disInfo.Cdisk[0]+"G\n\n已使用："+serverBean.disInfo.Cdisk[1]+"G\n\n未使用："+serverBean.disInfo.Cdisk[2]+"G");
        mDisk2.setText("      "+serverBean.diskName[1]+"\n\n总容量："+serverBean.disInfo.Ddisk[0]+"G\n\n已使用："+serverBean.disInfo.Ddisk[1]+"G\n\n未使用："+serverBean.disInfo.Ddisk[2]+"G");
        mDisk3.setText("      "+serverBean.diskName[2]+"\n\n总容量："+serverBean.disInfo.Edisk[0]+"G\n\n已使用："+serverBean.disInfo.Edisk[1]+"G\n\n未使用："+serverBean.disInfo.Edisk[2]+"G");
        mDisk4.setText("      "+serverBean.diskName[3]+"\n\n总容量："+serverBean.disInfo.Fdisk[0]+"G\n\n已使用："+serverBean.disInfo.Fdisk[1]+"G\n\n未使用："+serverBean.disInfo.Fdisk[2]+"G");

    }
}
