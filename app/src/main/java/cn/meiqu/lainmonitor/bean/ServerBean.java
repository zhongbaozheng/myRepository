package cn.meiqu.lainmonitor.bean;

/**
 * Created by Administrator on 2017/5/6.
 */

public class ServerBean {
    /***
     * "connectionSum": 138,
     "memFree": 3840.63,
     "swapUsed": 2498.21,
     "memTotal": 6056.89,
     "cpuCombined": 100,
     "diskName": [
     "Cdisk",
     "Ddisk",
     "Edisk",
     "Fdisk"
     ],
     "cpuMhz": 3200,
     "swapTotal": 12111.94,
     "localAddress": "192.168.1.169",
     "cpuNice": 0,
     "cpuIdle": 0,
     "cpuSys": 0,
     "disInfo": {
     "Ddisk": [
     129,
     3.25,
     125.75
     ],
     "Fdisk": [
     127.75,
     0.46,
     127.3
     ],
     "Cdisk": [
     80,
     38.7,
     41.3
     ],
     "Edisk": [
     129,
     0.12,
     128.88
     ]
     },
     "cpuUser": 100,
     "swapFree": 9613.73,
     "cpuWait": 0,
     "memUsed": 2216.26
     }
     */
    public float connectionSum;
    public float memFree;
    public float swapUsed;
    public float memTotal;
    public float cpuCombined;
    public String[] diskName;
    public float cpuMhz;
    public float swapTotal;
    public String localAddress;
    public float cpuNice;
    public float cpuIdle;
    public float cpuSys;
    public DisInfoBean disInfo;
    public float cpuUser;
    public float swapFree;
    public float cpuWait;
    public float memUsed;
}
