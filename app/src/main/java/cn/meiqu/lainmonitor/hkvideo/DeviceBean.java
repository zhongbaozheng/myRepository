package cn.meiqu.lainmonitor.hkvideo;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/6/21.
 */

public class DeviceBean {

    public int channel;   //通道号
    public String strIP;  //Ip
    public int nPort;     //ip端口号
    public String strUser;  //登录名
    public String strPsd;   //密码

    public Bitmap bitmap;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getStrIP() {
        return strIP;
    }

    public void setStrIP(String strIP) {
        this.strIP = strIP;
    }

    public int getnPort() {
        return nPort;
    }

    public void setnPort(int nPort) {
        this.nPort = nPort;
    }

    public String getStrUser() {
        return strUser;
    }

    public void setStrUser(String strUser) {
        this.strUser = strUser;
    }

    public String getStrPsd() {
        return strPsd;
    }

    public void setStrPsd(String strPsd) {
        this.strPsd = strPsd;
    }

    public DeviceBean(){

    }

    public DeviceBean(int position){
        channel = position;
    }


}
