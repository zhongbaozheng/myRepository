package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-27.
 */
public class AirF {

    /**
     * address : 1
     * deviceLocationPojo : null
     * id : 5
     * ip : 26
     * ipPort :
     * name : 出生地
     * number : 0
     * offOrder : F01002
     * onOrder : F01001
     * status : 0
     * studyOffOrder : S01002
     * studyOnOrder : S01001
     */

    private int address;
    private DeviceLocationPojo deviceLocationPojo;
    private int id;
    private int ip;
    private String ipPort;
    private String name;
    private int number;
    private String offOrder;
    private String onOrder;
    private int status;
    private String studyOffOrder;
    private String studyOnOrder;
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

    public String getOffOrder() {
        return offOrder;
    }

    public void setOffOrder(String offOrder) {
        this.offOrder = offOrder;
    }

    public String getOnOrder() {
        return onOrder;
    }

    public void setOnOrder(String onOrder) {
        this.onOrder = onOrder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStudyOffOrder() {
        return studyOffOrder;
    }

    public void setStudyOffOrder(String studyOffOrder) {
        this.studyOffOrder = studyOffOrder;
    }

    public String getStudyOnOrder() {
        return studyOnOrder;
    }

    public void setStudyOnOrder(String studyOnOrder) {
        this.studyOnOrder = studyOnOrder;
    }
}
