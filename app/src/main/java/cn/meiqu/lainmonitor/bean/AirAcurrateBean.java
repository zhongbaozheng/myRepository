package cn.meiqu.lainmonitor.bean;

/**
 * Created by Administrator on 2017/5/2.
 */

public class AirAcurrateBean {

    /**
     * {
     "csfState": 1,
     "deviceLocationPojo": null,
     "hfttAlarm": 1,
     "hum": 23.3,
     "id": 1,
     "jsqddlAlarm": 1,
     "jsqgswAlarm": 1,
     "jsqjsfState": 1,
     "jsqpsfState": 1,
     "kgjState": 1,
     "name": "1号精密空调",
     "nfj2State": 1,
     "nfj2gzAlarm": 1,
     "rqptState": 1,
     "temp": 23.3,
     "txgzAlarm": 1,
     "wfj1State": 1,
     "ysj1State": 1,
     "ysj2State": 1,
     "ysj2dyAlarm": 1,
     "ysj2gyAlarm": 1,
     "yxmsState": 1
     },
     */

    /** @id 地址*/
    public int id;
    /** @name 设备名称*/
    public String name;
    /** @temp 温度*/
    public double temp;
    /** @hum 湿度*/
    public double hum;
    /** @ysj1State 压缩机1状态*/
    public int ysj1State;
    /** @wfj1State 风机状态*/
    public int wfj1State;
    /** @syj2State 制冷状态*/
    public int ysj2State;
    /** @csfState 除湿状态*/
    public int csfState;
    /** @nfj2State 加热状态*/
    public int nfj2State;
    /** @jsqpsfState 加湿状态*/
    public int jsqpsfState;
    /** @jsqjsfState 状态*/
    public int jsqjsfState;
    /** @rqptState 热气旁通阀状态*/
    public int rqptState;
    /** @yxmsState 运行状态*/
    public int yxmsState;
    /** @kgjState 开关机状态*/
    public int kgjState;
    /** @jsqddlAlarm 低湿报警*/
    public int jsqddlAlarm;
    /** @jsqgswAlarm 高湿报警*/
    public int jsqgswAlarm;
    /** @hfttAlarm 高温报警*/
    public int hfttAlarm;
    /** @txgzAlarm 低温报警*/
    public int txgzAlarm;
    /** @ysj2dyAlarm 低压报警*/
    public int ysj2dyAlarm;
    /** @ysj2gyAlarm 高压报警*/
    public int ysj2gyAlarm;
    /** @nfj2gzAlarm 电源故障报警*/
    public int nfj2gzAlarm;

}
