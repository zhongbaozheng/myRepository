package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-25.
 */
public class LocationWater {

    /**
     * address : 1
     * deviceLocationPojo : null
     * id : 5
     * ip : 0
     * ipPort :
     * length : 0
     * name : vessel
     * number : 0
     * status : 2
     */

    private int address;
    private DeviceLocationPojo deviceLocationPojo;
    private int id;
    private int ip;
    private String ipPort;
    private int length;
    private String name;
    private int number;
    private int status;

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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
