package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-6-1.
 */
public class Device80 {

    /**
     * address : 1
     * deviceLocationPojo : {"dlId":0,"dlName":"拒绝啦咯了跳转"}
     * deviceName : 声光报警
     * gallery : RL4
     * id : 10
     * ip : 11
     * ipPort : 192.168.1.222:5100
     * name : 2号新风机
     * number : 1
     * status : 0
     */

    private int address;
    /**
     * dlId : 0
     * dlName : 拒绝啦咯了跳转
     */

    private DeviceLocationPojoEntity deviceLocationPojo;
    private String deviceName;
    private String gallery;
    private int id;
    private int ip;
    private String ipPort;
    private String name;
    private int number;
    private int status;

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public DeviceLocationPojoEntity getDeviceLocationPojo() {
        return deviceLocationPojo;
    }

    public void setDeviceLocationPojo(DeviceLocationPojoEntity deviceLocationPojo) {
        this.deviceLocationPojo = deviceLocationPojo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
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

    public static class DeviceLocationPojoEntity {
        private int dlId;
        private String dlName;

        public int getDlId() {
            return dlId;
        }

        public void setDlId(int dlId) {
            this.dlId = dlId;
        }

        public String getDlName() {
            return dlName;
        }

        public void setDlName(String dlName) {
            this.dlName = dlName;
        }
    }
}
