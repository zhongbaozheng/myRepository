package cn.meiqu.lainmonitor.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */

public class ManagerControlBean {
    /**
     * {
     "SNCard": 0,
     "card": "0010045088",
     "daui": [
     {
     "address": 3,
     "doorId": 6,
     "id": 26,
     "ipid": 0,
     "name": "电商部",
     "status": 0,
     "userId": 25
     },
     "id": 14,
     "ip": 0,
     "ipPort": "",
     "name": "lin",
     "number": 0,
     "realCard": "",
     "sectionId": 0,
     "sectionName": "开发部",
     "sn": 0,
     "status": 1
     }
     */
    public int SNCard;
    public String card;
    public ArrayList<Dauibean> daui;
    public int id;
    public String ip;
    public String ipPort;
    public String  name;
    public int number;
    public String realCard;
    public int sectionId;
    public String sectionName;
    public int sn;
    public int status;
}
