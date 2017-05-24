package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-31.
 */
public class Ip {

    /**
     * diAddress : 192.168.1.222
     * diDeviceNumber : 1
     * diId : 2
     * diIsConnect : 0
     * diName : 温湿度
     * diOperate : 0
     * diPort : 5200
     */

    private String diAddress;
    private int diDeviceNumber;
    private int diId;
    private int diIsConnect;
    private String diName;
    private int diOperate;
    private int diPort;

    public String getDiAddress() {
        return diAddress;
    }

    public void setDiAddress(String diAddress) {
        this.diAddress = diAddress;
    }

    public int getDiDeviceNumber() {
        return diDeviceNumber;
    }

    public void setDiDeviceNumber(int diDeviceNumber) {
        this.diDeviceNumber = diDeviceNumber;
    }

    public int getDiId() {
        return diId;
    }

    public void setDiId(int diId) {
        this.diId = diId;
    }

    public int getDiIsConnect() {
        return diIsConnect;
    }

    public void setDiIsConnect(int diIsConnect) {
        this.diIsConnect = diIsConnect;
    }

    public String getDiName() {
        return diName;
    }

    public void setDiName(String diName) {
        this.diName = diName;
    }

    public int getDiOperate() {
        return diOperate;
    }

    public void setDiOperate(int diOperate) {
        this.diOperate = diOperate;
    }

    public int getDiPort() {
        return diPort;
    }

    public void setDiPort(int diPort) {
        this.diPort = diPort;
    }
}
