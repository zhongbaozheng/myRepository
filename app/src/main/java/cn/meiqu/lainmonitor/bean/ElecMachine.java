package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-27.
 */
public class ElecMachine {

    /**
     * deviceLocationPojo : null
     * electricMeterManagePojo : {"deviceLocationPojo":null,"emmAddress":0,"emmId":0,"emmName":"","emmOrder":"","ip":0,"ipPort":"","maxABvol":400,"maxAcur":100,"maxApap":120,"maxAppf":100,"maxAprp":120,"maxAvol":240,"maxBCvol":400,"maxBcur":100,"maxBpap":120,"maxBppf":100,"maxBprp":120,"maxBvol":240,"maxCAvol":400,"maxCcur":100,"maxCpap":120,"maxCppf":100,"maxCprp":120,"maxCvol":240,"maxTpap":300,"maxTppf":100,"maxTprp":300,"minABvol":350,"minAcur":0,"minApap":0,"minAppf":0,"minAprp":0,"minAvol":200,"minBCvol":350,"minBcur":0,"minBpap":0,"minBppf":0,"minBprp":0,"minBvol":200,"minCAvol":350,"minCcur":0,"minCpap":0,"minCppf":0,"minCprp":0,"minCvol":200,"minTpap":0,"minTppf":0,"minTprp":0,"number":0}
     * emdABvol : 371.23
     * emdAcur : 0
     * emdApap : 0
     * emdAppf : 0.99
     * emdAprp : 0
     * emdAvol : 206.64
     * emdBCvol : 387.95
     * emdBcur : 0
     * emdBpap : 0
     * emdBppf : 0.99
     * emdBprp : 0
     * emdBvol : 221.93
     * emdCAvol : 374.82
     * emdCcur : 0
     * emdCpap : 0
     * emdCppf : 0.99
     * emdCprp : 0
     * emdCvol : 226.03
     * emdId : 0
     * emdName : 1号电量仪
     * emdTpap : 0
     * emdTppf : 0.99
     * emdTprp : 0
     */

    private Object deviceLocationPojo;
    /**
     * deviceLocationPojo : null
     * emmAddress : 0
     * emmId : 0
     * emmName :
     * emmOrder :
     * ip : 0
     * ipPort :
     * maxABvol : 400
     * maxAcur : 100
     * maxApap : 120
     * maxAppf : 100
     * maxAprp : 120
     * maxAvol : 240
     * maxBCvol : 400
     * maxBcur : 100
     * maxBpap : 120
     * maxBppf : 100
     * maxBprp : 120
     * maxBvol : 240
     * maxCAvol : 400
     * maxCcur : 100
     * maxCpap : 120
     * maxCppf : 100
     * maxCprp : 120
     * maxCvol : 240
     * maxTpap : 300
     * maxTppf : 100
     * maxTprp : 300
     * minABvol : 350
     * minAcur : 0
     * minApap : 0
     * minAppf : 0
     * minAprp : 0
     * minAvol : 200
     * minBCvol : 350
     * minBcur : 0
     * minBpap : 0
     * minBppf : 0
     * minBprp : 0
     * minBvol : 200
     * minCAvol : 350
     * minCcur : 0
     * minCpap : 0
     * minCppf : 0
     * minCprp : 0
     * minCvol : 200
     * minTpap : 0
     * minTppf : 0
     * minTprp : 0
     * number : 0
     */

    private ElectricMeterManagePojoEntity electricMeterManagePojo;
    private double emdABvol;
    private int emdAcur;
    private int emdApap;
    private double emdAppf;
    private int emdAprp;
    private double emdAvol;
    private double emdBCvol;
    private int emdBcur;
    private int emdBpap;
    private double emdBppf;
    private int emdBprp;
    private double emdBvol;
    private double emdCAvol;
    private int emdCcur;
    private int emdCpap;
    private double emdCppf;
    private int emdCprp;
    private double emdCvol;
    private int emdId;
    private String emdName;
    private int emdTpap;
    private double emdTppf;
    private int emdTprp;

    public Object getDeviceLocationPojo() {
        return deviceLocationPojo;
    }

    public void setDeviceLocationPojo(Object deviceLocationPojo) {
        this.deviceLocationPojo = deviceLocationPojo;
    }

    public ElectricMeterManagePojoEntity getElectricMeterManagePojo() {
        return electricMeterManagePojo;
    }

    public void setElectricMeterManagePojo(ElectricMeterManagePojoEntity electricMeterManagePojo) {
        this.electricMeterManagePojo = electricMeterManagePojo;
    }

    public double getEmdABvol() {
        return emdABvol;
    }

    public void setEmdABvol(double emdABvol) {
        this.emdABvol = emdABvol;
    }

    public int getEmdAcur() {
        return emdAcur;
    }

    public void setEmdAcur(int emdAcur) {
        this.emdAcur = emdAcur;
    }

    public int getEmdApap() {
        return emdApap;
    }

    public void setEmdApap(int emdApap) {
        this.emdApap = emdApap;
    }

    public double getEmdAppf() {
        return emdAppf;
    }

    public void setEmdAppf(double emdAppf) {
        this.emdAppf = emdAppf;
    }

    public int getEmdAprp() {
        return emdAprp;
    }

    public void setEmdAprp(int emdAprp) {
        this.emdAprp = emdAprp;
    }

    public double getEmdAvol() {
        return emdAvol;
    }

    public void setEmdAvol(double emdAvol) {
        this.emdAvol = emdAvol;
    }

    public double getEmdBCvol() {
        return emdBCvol;
    }

    public void setEmdBCvol(double emdBCvol) {
        this.emdBCvol = emdBCvol;
    }

    public int getEmdBcur() {
        return emdBcur;
    }

    public void setEmdBcur(int emdBcur) {
        this.emdBcur = emdBcur;
    }

    public int getEmdBpap() {
        return emdBpap;
    }

    public void setEmdBpap(int emdBpap) {
        this.emdBpap = emdBpap;
    }

    public double getEmdBppf() {
        return emdBppf;
    }

    public void setEmdBppf(double emdBppf) {
        this.emdBppf = emdBppf;
    }

    public int getEmdBprp() {
        return emdBprp;
    }

    public void setEmdBprp(int emdBprp) {
        this.emdBprp = emdBprp;
    }

    public double getEmdBvol() {
        return emdBvol;
    }

    public void setEmdBvol(double emdBvol) {
        this.emdBvol = emdBvol;
    }

    public double getEmdCAvol() {
        return emdCAvol;
    }

    public void setEmdCAvol(double emdCAvol) {
        this.emdCAvol = emdCAvol;
    }

    public int getEmdCcur() {
        return emdCcur;
    }

    public void setEmdCcur(int emdCcur) {
        this.emdCcur = emdCcur;
    }

    public int getEmdCpap() {
        return emdCpap;
    }

    public void setEmdCpap(int emdCpap) {
        this.emdCpap = emdCpap;
    }

    public double getEmdCppf() {
        return emdCppf;
    }

    public void setEmdCppf(double emdCppf) {
        this.emdCppf = emdCppf;
    }

    public int getEmdCprp() {
        return emdCprp;
    }

    public void setEmdCprp(int emdCprp) {
        this.emdCprp = emdCprp;
    }

    public double getEmdCvol() {
        return emdCvol;
    }

    public void setEmdCvol(double emdCvol) {
        this.emdCvol = emdCvol;
    }

    public int getEmdId() {
        return emdId;
    }

    public void setEmdId(int emdId) {
        this.emdId = emdId;
    }

    public String getEmdName() {
        return emdName;
    }

    public void setEmdName(String emdName) {
        this.emdName = emdName;
    }

    public int getEmdTpap() {
        return emdTpap;
    }

    public void setEmdTpap(int emdTpap) {
        this.emdTpap = emdTpap;
    }

    public double getEmdTppf() {
        return emdTppf;
    }

    public void setEmdTppf(double emdTppf) {
        this.emdTppf = emdTppf;
    }

    public int getEmdTprp() {
        return emdTprp;
    }

    public void setEmdTprp(int emdTprp) {
        this.emdTprp = emdTprp;
    }

    public static class ElectricMeterManagePojoEntity {
        private Object deviceLocationPojo;
        private int emmAddress;
        private int emmId;
        private String emmName;
        private String emmOrder;
        private int ip;
        private String ipPort;
        private int maxABvol;
        private int maxAcur;
        private int maxApap;
        private int maxAppf;
        private int maxAprp;
        private int maxAvol;
        private int maxBCvol;
        private int maxBcur;
        private int maxBpap;
        private int maxBppf;
        private int maxBprp;
        private int maxBvol;
        private int maxCAvol;
        private int maxCcur;
        private int maxCpap;
        private int maxCppf;
        private int maxCprp;
        private int maxCvol;
        private int maxTpap;
        private int maxTppf;
        private int maxTprp;
        private int minABvol;
        private int minAcur;
        private int minApap;
        private int minAppf;
        private int minAprp;
        private int minAvol;
        private int minBCvol;
        private int minBcur;
        private int minBpap;
        private int minBppf;
        private int minBprp;
        private int minBvol;
        private int minCAvol;
        private int minCcur;
        private int minCpap;
        private int minCppf;
        private int minCprp;
        private int minCvol;
        private int minTpap;
        private int minTppf;
        private int minTprp;
        private int number;

        public Object getDeviceLocationPojo() {
            return deviceLocationPojo;
        }

        public void setDeviceLocationPojo(Object deviceLocationPojo) {
            this.deviceLocationPojo = deviceLocationPojo;
        }

        public int getEmmAddress() {
            return emmAddress;
        }

        public void setEmmAddress(int emmAddress) {
            this.emmAddress = emmAddress;
        }

        public int getEmmId() {
            return emmId;
        }

        public void setEmmId(int emmId) {
            this.emmId = emmId;
        }

        public String getEmmName() {
            return emmName;
        }

        public void setEmmName(String emmName) {
            this.emmName = emmName;
        }

        public String getEmmOrder() {
            return emmOrder;
        }

        public void setEmmOrder(String emmOrder) {
            this.emmOrder = emmOrder;
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

        public int getMaxABvol() {
            return maxABvol;
        }

        public void setMaxABvol(int maxABvol) {
            this.maxABvol = maxABvol;
        }

        public int getMaxAcur() {
            return maxAcur;
        }

        public void setMaxAcur(int maxAcur) {
            this.maxAcur = maxAcur;
        }

        public int getMaxApap() {
            return maxApap;
        }

        public void setMaxApap(int maxApap) {
            this.maxApap = maxApap;
        }

        public int getMaxAppf() {
            return maxAppf;
        }

        public void setMaxAppf(int maxAppf) {
            this.maxAppf = maxAppf;
        }

        public int getMaxAprp() {
            return maxAprp;
        }

        public void setMaxAprp(int maxAprp) {
            this.maxAprp = maxAprp;
        }

        public int getMaxAvol() {
            return maxAvol;
        }

        public void setMaxAvol(int maxAvol) {
            this.maxAvol = maxAvol;
        }

        public int getMaxBCvol() {
            return maxBCvol;
        }

        public void setMaxBCvol(int maxBCvol) {
            this.maxBCvol = maxBCvol;
        }

        public int getMaxBcur() {
            return maxBcur;
        }

        public void setMaxBcur(int maxBcur) {
            this.maxBcur = maxBcur;
        }

        public int getMaxBpap() {
            return maxBpap;
        }

        public void setMaxBpap(int maxBpap) {
            this.maxBpap = maxBpap;
        }

        public int getMaxBppf() {
            return maxBppf;
        }

        public void setMaxBppf(int maxBppf) {
            this.maxBppf = maxBppf;
        }

        public int getMaxBprp() {
            return maxBprp;
        }

        public void setMaxBprp(int maxBprp) {
            this.maxBprp = maxBprp;
        }

        public int getMaxBvol() {
            return maxBvol;
        }

        public void setMaxBvol(int maxBvol) {
            this.maxBvol = maxBvol;
        }

        public int getMaxCAvol() {
            return maxCAvol;
        }

        public void setMaxCAvol(int maxCAvol) {
            this.maxCAvol = maxCAvol;
        }

        public int getMaxCcur() {
            return maxCcur;
        }

        public void setMaxCcur(int maxCcur) {
            this.maxCcur = maxCcur;
        }

        public int getMaxCpap() {
            return maxCpap;
        }

        public void setMaxCpap(int maxCpap) {
            this.maxCpap = maxCpap;
        }

        public int getMaxCppf() {
            return maxCppf;
        }

        public void setMaxCppf(int maxCppf) {
            this.maxCppf = maxCppf;
        }

        public int getMaxCprp() {
            return maxCprp;
        }

        public void setMaxCprp(int maxCprp) {
            this.maxCprp = maxCprp;
        }

        public int getMaxCvol() {
            return maxCvol;
        }

        public void setMaxCvol(int maxCvol) {
            this.maxCvol = maxCvol;
        }

        public int getMaxTpap() {
            return maxTpap;
        }

        public void setMaxTpap(int maxTpap) {
            this.maxTpap = maxTpap;
        }

        public int getMaxTppf() {
            return maxTppf;
        }

        public void setMaxTppf(int maxTppf) {
            this.maxTppf = maxTppf;
        }

        public int getMaxTprp() {
            return maxTprp;
        }

        public void setMaxTprp(int maxTprp) {
            this.maxTprp = maxTprp;
        }

        public int getMinABvol() {
            return minABvol;
        }

        public void setMinABvol(int minABvol) {
            this.minABvol = minABvol;
        }

        public int getMinAcur() {
            return minAcur;
        }

        public void setMinAcur(int minAcur) {
            this.minAcur = minAcur;
        }

        public int getMinApap() {
            return minApap;
        }

        public void setMinApap(int minApap) {
            this.minApap = minApap;
        }

        public int getMinAppf() {
            return minAppf;
        }

        public void setMinAppf(int minAppf) {
            this.minAppf = minAppf;
        }

        public int getMinAprp() {
            return minAprp;
        }

        public void setMinAprp(int minAprp) {
            this.minAprp = minAprp;
        }

        public int getMinAvol() {
            return minAvol;
        }

        public void setMinAvol(int minAvol) {
            this.minAvol = minAvol;
        }

        public int getMinBCvol() {
            return minBCvol;
        }

        public void setMinBCvol(int minBCvol) {
            this.minBCvol = minBCvol;
        }

        public int getMinBcur() {
            return minBcur;
        }

        public void setMinBcur(int minBcur) {
            this.minBcur = minBcur;
        }

        public int getMinBpap() {
            return minBpap;
        }

        public void setMinBpap(int minBpap) {
            this.minBpap = minBpap;
        }

        public int getMinBppf() {
            return minBppf;
        }

        public void setMinBppf(int minBppf) {
            this.minBppf = minBppf;
        }

        public int getMinBprp() {
            return minBprp;
        }

        public void setMinBprp(int minBprp) {
            this.minBprp = minBprp;
        }

        public int getMinBvol() {
            return minBvol;
        }

        public void setMinBvol(int minBvol) {
            this.minBvol = minBvol;
        }

        public int getMinCAvol() {
            return minCAvol;
        }

        public void setMinCAvol(int minCAvol) {
            this.minCAvol = minCAvol;
        }

        public int getMinCcur() {
            return minCcur;
        }

        public void setMinCcur(int minCcur) {
            this.minCcur = minCcur;
        }

        public int getMinCpap() {
            return minCpap;
        }

        public void setMinCpap(int minCpap) {
            this.minCpap = minCpap;
        }

        public int getMinCppf() {
            return minCppf;
        }

        public void setMinCppf(int minCppf) {
            this.minCppf = minCppf;
        }

        public int getMinCprp() {
            return minCprp;
        }

        public void setMinCprp(int minCprp) {
            this.minCprp = minCprp;
        }

        public int getMinCvol() {
            return minCvol;
        }

        public void setMinCvol(int minCvol) {
            this.minCvol = minCvol;
        }

        public int getMinTpap() {
            return minTpap;
        }

        public void setMinTpap(int minTpap) {
            this.minTpap = minTpap;
        }

        public int getMinTppf() {
            return minTppf;
        }

        public void setMinTppf(int minTppf) {
            this.minTppf = minTppf;
        }

        public int getMinTprp() {
            return minTprp;
        }

        public void setMinTprp(int minTprp) {
            this.minTprp = minTprp;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
