package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-29.
 */
public class TempAlart {

    /**
     * ehaId : 1
     * ehaInfo : 1号监控机房-1号温湿度:检测温度46.4°C超过报警阀值35.0°C。检测湿度107.2%超过报警阀值90.0%。
     * ehaLocation : 1号监控机房
     * ehaName : 2号温湿度
     * ehaTime : 2016-04-01 07:02:44.0
     */

    private int ehaId;
    private String ehaInfo;
    private String ehaLocation;
    private String ehaName;
    private String ehaTime;

    public int getEhaId() {
        return ehaId;
    }

    public void setEhaId(int ehaId) {
        this.ehaId = ehaId;
    }

    public String getEhaInfo() {
        return ehaInfo;
    }

    public void setEhaInfo(String ehaInfo) {
        this.ehaInfo = ehaInfo;
    }

    public String getEhaLocation() {
        return ehaLocation;
    }

    public void setEhaLocation(String ehaLocation) {
        this.ehaLocation = ehaLocation;
    }

    public String getEhaName() {
        return ehaName;
    }

    public void setEhaName(String ehaName) {
        this.ehaName = ehaName;
    }

    public String getEhaTime() {
        return ehaTime;
    }

    public void setEhaTime(String ehaTime) {
        this.ehaTime = ehaTime;
    }
}
