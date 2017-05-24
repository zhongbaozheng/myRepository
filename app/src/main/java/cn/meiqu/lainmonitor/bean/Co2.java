package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-25.
 */
public class Co2 {

    /**
     * address : 0
     * alarmData : 0.2
     * currentData : 1500
     * deviceLocationPojo : null
     * id : 1
     * ip : 0
     * ipPort :
     * name : fsdfas呵呵呵
     * number : 0
     */

    private int address;
    private double alarmData;
    private double currentData;
    private DeviceLocationPojo deviceLocationPojo;
    private int id;
    private int ip;
    private String ipPort;
    private String name;
    private double number;
    public class DeviceLocationPojo {
        public String getDlId() {
            return dlId;
        }

        public void setDlId(String dlId) {
            this.dlId = dlId;
        }

        public String getDlName() {
            return dlName;
        }

        public void setDlName(String dlName) {
            this.dlName = dlName;
        }

        private String dlId;
        private String dlName;
    }
    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public double getAlarmData() {
        return alarmData;
    }

    public void setAlarmData(double alarmData) {
        this.alarmData = alarmData;
    }

    public double getCurrentData() {
        return currentData;
    }

    public void setCurrentData(int currentData) {
        this.currentData = currentData;
    }

    public DeviceLocationPojo getDeviceLocationPojo() {
        return deviceLocationPojo;
    }

    public void setDeviceLocationPojo(DeviceLocationPojo deviceLocationPojo) {
        this.deviceLocationPojo = deviceLocationPojo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
