package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-6-2.
 */
public class TempHistroy {
    private double ehmHum;
    private int ehmId;
    private double ehmTemp;
    private String ehmName;
    private String ehmTime;

    public TempHistroy() {
    }

    public TempHistroy(String ehmTime, double ehmTemp, double ehmHum) {
        this.ehmHum = ehmHum;
        this.ehmTemp = ehmTemp;
        this.ehmTime = ehmTime;
    }

    public double getEhmHum() {
        return ehmHum;
    }

    public void setEhmHum(double ehmHum) {
        this.ehmHum = ehmHum;
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

    public int getEhmId() {
        return ehmId;
    }

    public void setEhmId(int ehmId) {
        this.ehmId = ehmId;
    }

    public String getEhmTime() {
        return ehmTime;
    }

    public void setEhmTime(String ehmTime) {
        this.ehmTime = ehmTime;
    }
}
