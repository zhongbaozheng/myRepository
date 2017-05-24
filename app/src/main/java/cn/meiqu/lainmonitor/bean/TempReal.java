package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-25.
 */
public class TempReal {

    /**
     * deviceLocationPojo : null
     * ehmAddress : 0
     * ehmHum : 61.5
     * ehmId : 2
     * ehmInterval : 0
     * ehmMaxHum : 90
     * ehmMaxTemp : 30
     * ehmMinHum : 20
     * ehmMinTemp : 10
     * ehmName : 2号温湿度
     * ehmTemp : 26.6
     * ip : 0
     * ipPort :
     * number : 0
     */

    private DeviceLocationPojo deviceLocationPojo;
    private int ehmAddress;
    private double ehmHum;
    private int ehmId;
    private int ehmInterval;
    private double ehmMaxHum;
    private double ehmMaxTemp;
    private double ehmMinHum;
    private double ehmMinTemp;
    private String ehmName;
    private double ehmTemp;
    private int ip;
    private String ipPort;
    private int number;

    public class DeviceLocationPojo {
        public String getDlId() {
            return dlId;
        }

        public void setDlId(String dlId) {
            this.dlId = dlId;
        }

        private String dlId;
        private String dlName;

        public String getDlName() {
            return dlName;
        }

        public void setDlName(String dlName) {
            this.dlName = dlName;
        }
    }

    public DeviceLocationPojo getDeviceLocationPojo() {
        return deviceLocationPojo;
    }

    public void setDeviceLocationPojo(DeviceLocationPojo deviceLocationPojo) {
        this.deviceLocationPojo = deviceLocationPojo;
    }

    public int getEhmAddress() {
        return ehmAddress;
    }

    public void setEhmAddress(int ehmAddress) {
        this.ehmAddress = ehmAddress;
    }

    public double getEhmHum() {
        return ehmHum;
    }

    public void setEhmHum(double ehmHum) {
        this.ehmHum = ehmHum;
    }

    public int getEhmId() {
        return ehmId;
    }

    public void setEhmId(int ehmId) {
        this.ehmId = ehmId;
    }

    public int getEhmInterval() {
        return ehmInterval;
    }

    public void setEhmInterval(int ehmInterval) {
        this.ehmInterval = ehmInterval;
    }

    public double getEhmMaxHum() {
        return ehmMaxHum;
    }

    public void setEhmMaxHum(int ehmMaxHum) {
        this.ehmMaxHum = ehmMaxHum;
    }

    public double getEhmMaxTemp() {
        return ehmMaxTemp;
    }

    public void setEhmMaxTemp(int ehmMaxTemp) {
        this.ehmMaxTemp = ehmMaxTemp;
    }

    public double getEhmMinHum() {
        return ehmMinHum;
    }

    public void setEhmMinHum(int ehmMinHum) {
        this.ehmMinHum = ehmMinHum;
    }

    public double getEhmMinTemp() {
        return ehmMinTemp;
    }

    public void setEhmMinTemp(int ehmMinTemp) {
        this.ehmMinTemp = ehmMinTemp;
    }

    public String getEhmName() {
        return ehmName;
    }

    public void setEhmName(String ehmName) {
        this.ehmName = ehmName;
    }

    public double getEhmTemp() {
        return ehmTemp;
    }

    public void setEhmTemp(double ehmTemp) {
        this.ehmTemp = ehmTemp;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
