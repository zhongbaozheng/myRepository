package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-26.
 */
public class Security {

    /**
     * address : 0
     * deviceLocationPojo : null
     * deviceName :
     * gallery : DI5
     * id : 0
     * ip : 0
     * ipPort :
     * name : 1号门磁
     * number : 0
     * order :
     * status : 0
     */

    private int address;
    private Object deviceLocationPojo;
    private String deviceName;
    private String gallery;
    private int id;
    private int ip;
    private String ipPort;
    private String name;
    private int number;
    private String order;
    private int status;

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public Object getDeviceLocationPojo() {
        return deviceLocationPojo;
    }

    public void setDeviceLocationPojo(Object deviceLocationPojo) {
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
