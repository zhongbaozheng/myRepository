package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-28.
 */
public class AlertType1 {

    /**
     * address : 1
     * gallery : DI0
     * id : 0
     * info : 监测到1号监控机房中的111有报警，请及时处理！
     * location : 1号监控机房
     * name : 111
     * time : 2015-09-20 16:13:39.0
     */

    private int address;
    private String gallery;
    private int id;
    private String info;
    private String location;
    private String name;
    private String time;

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
