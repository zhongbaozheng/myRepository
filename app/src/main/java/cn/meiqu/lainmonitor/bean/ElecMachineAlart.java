package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-29.
 */
public class ElecMachineAlart {

    /**
     * emaId : 1
     * emaInfo : 1号监控机房-1号电量仪:检测到408.68V超过BC相电压上限。
     * emaLocation : 1号监控机房
     * emaName : 1号电量仪
     * emaTime : 2015-09-22 18:47:48.0
     */

    private int emaId;
    private String emaInfo;
    private String emaLocation;
    private String emaName;
    private String emaTime;

    public int getEmaId() {
        return emaId;
    }

    public void setEmaId(int emaId) {
        this.emaId = emaId;
    }

    public String getEmaInfo() {
        return emaInfo;
    }

    public void setEmaInfo(String emaInfo) {
        this.emaInfo = emaInfo;
    }

    public String getEmaLocation() {
        return emaLocation;
    }

    public void setEmaLocation(String emaLocation) {
        this.emaLocation = emaLocation;
    }

    public String getEmaName() {
        return emaName;
    }

    public void setEmaName(String emaName) {
        this.emaName = emaName;
    }

    public String getEmaTime() {
        return emaTime;
    }

    public void setEmaTime(String emaTime) {
        this.emaTime = emaTime;
    }
}
