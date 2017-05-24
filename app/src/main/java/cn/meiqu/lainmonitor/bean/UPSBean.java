package cn.meiqu.lainmonitor.bean;

/**
 * Created by Administrator on 2017/5/2.
 */

public class UPSBean {
    /** @id UPS地址*/
    public int id;
    /** @name UPS设备名称*/
    public String name;
    /** @intAvol 输入电压*/
    public double intAvol;
    /** @intBvol B相输入电压*/
    public double intBvol;
    /** @intCvol C相输入电压*/
    public double intCvol;
    /** @outAvol 输出电压*/
    public double outAvol;
    /** @outBvol B相输出电压*/
    public double outBvol;
    /** @outCvol C相输出电压*/
    public double outCvol;
    /** @bypAvol 旁路电压*/
    public double bypAvol;
    /** @bypBvol B相旁路电压*/
    public double bypBvol;
    /** @bypCvol C相旁路电压*/
    public double bypCvol;
    /** @loadA 负载百分比*/
    public double loadA;
    /** @loadB B相负载百分比*/
    public double loadB;
    /** @loadC C相负载百分比*/
    public double loadC;
    /** @batVol 电池电压*/
    public double batVol;
    /** @batCapacity 电池容量*/
    public double batCapacity;
    /** @resTime 剩余时间*/
    public double resTime;
    /** @batCur 输入电流*/
    public double batCur;
    /** @batTemp 电池温度*/
    public double batTemp;
    /** @intFrequency 输入频率*/
    public double intFrequency
            /** @bypFrequency 旁路频率*/;
    public double bypFrequency;
    /** @ UPS故障*/
    public int intFault;
    /** @ 电池电压低*/
    public int batVolLow;
    /** @ 旁路电压激活*/
    public int bypActivate;
    /** @ 输入电压故障*/
    public int intVolFault;
    /** @ ups类型（在线式/后备式）*/
    public int upsType;
    /** @ 测试中*/
    public int inTest;
    //不相关变量-->NULL
    public  String deviceLocationPojo;
}
