package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-31.
 */
public class Alart {

    /**
     * eMailStatus : 1
     * id : 1
     * intervalTime : 5
     * name : 星期日
     * phoneStatus : 1
     * smsStatus : 0
     * soundLightStatus : 1
     * timeQuantum1 : 07:00-12:00
     * timeQuantum2 : 14:00-19:00
     * timeQuantum3 : 20:00-23:30
     */

    private int eMailStatus;
    private int id;
    private int intervalTime;
    private String name;
    private int phoneStatus;
    private int smsStatus;
    private int soundLightStatus;
    private String timeQuantum1;
    private String timeQuantum2;
    private String timeQuantum3;

    public int getEMailStatus() {
        return eMailStatus;
    }

    public void setEMailStatus(int eMailStatus) {
        this.eMailStatus = eMailStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneStatus() {
        return phoneStatus;
    }

    public void setPhoneStatus(int phoneStatus) {
        this.phoneStatus = phoneStatus;
    }

    public int getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(int smsStatus) {
        this.smsStatus = smsStatus;
    }

    public int getSoundLightStatus() {
        return soundLightStatus;
    }

    public void setSoundLightStatus(int soundLightStatus) {
        this.soundLightStatus = soundLightStatus;
    }

    public String getTimeQuantum1() {
        return timeQuantum1;
    }

    public void setTimeQuantum1(String timeQuantum1) {
        this.timeQuantum1 = timeQuantum1;
    }

    public String getTimeQuantum2() {
        return timeQuantum2;
    }

    public void setTimeQuantum2(String timeQuantum2) {
        this.timeQuantum2 = timeQuantum2;
    }

    public String getTimeQuantum3() {
        return timeQuantum3;
    }

    public void setTimeQuantum3(String timeQuantum3) {
        this.timeQuantum3 = timeQuantum3;
    }
}
