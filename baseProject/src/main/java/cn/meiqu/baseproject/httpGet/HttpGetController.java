package cn.meiqu.baseproject.httpGet;


import android.util.Log;
import android.util.StringBuilderPrinter;

import com.squareup.okhttp.FormEncodingBuilder;

import java.security.PolicySpi;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.bean.User;
import cn.meiqu.baseproject.dao.SettingDao;


public class HttpGetController {

    private HttpGetController() {
    }

    private static HttpGetController controller = new HttpGetController();

    public static HttpGetController getInstance() {
        return controller;
    }

    //    private HttpResponController responController = HttpResponController
//            .getInstance();
//
    private void get(String uri, final String parmas, String action) {
//        HttpGetBase.newInstance().get(uri, params, action, responController);
        HttpGetBase.newInstance().get(uri, parmas, action);
    }

    private void post(final String uri, final FormEncodingBuilder builder, final String action) {
//        BaseApp.mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //LogUtil.log("------" + uri + "---" + params + "" + action+responController);
//                HttpGetBase.newInstance().post(uri, params, action, responController);
//            }
//        }, 2 * 100);
        HttpGetBase.newInstance().post(uri, builder, action);
    }

    //    public RequestParams getTokenParma() {
//        RequestParams params = new RequestParams();
//        User user = SettingDao.getInstance().getUser();
//        if (user != null) {
//            params.put("access_token", user.token);
//            LogUtil.log("token=" + user.token);
//        }
//        return params;
//    }
    public FormEncodingBuilder getTokenBuild() {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        User user = SettingDao.getInstance().getUser();
        if (user != null) {
            builder.add("access_token", user.token);
        }
        builder.add("fatel", "jj");
        return builder;
    }

    public void login(String loginName, String password, String className) {
        FormEncodingBuilder builder = getTokenBuild();
        builder.add("loginName", loginName);
        builder.add("password", password);
        post(API.login, builder, className);
    }

    public void getHomePage(String className) {
        post(API.getHomePage, getTokenBuild(), className);
    }

    public void getHomeChildPage(String number, String className) {
        FormEncodingBuilder builder = getTokenBuild();
        builder.add("number", number);
        post(API.getHomeChildPage, builder, className);
    }

    public void getHomeChildThirdPage(String number1, String number2, String className) {
        FormEncodingBuilder builder = getTokenBuild();
        builder.add("number1", number1);
        builder.add("number2", number2);
        post(API.getHomeChildThirdPage, builder, className);
    }

    public void getTempReal(String className) {
        post(API.getTempReal, getTokenBuild(), className);
    }

    public void getnLWaterReal(String className) {
        post(API.getNoLocationWaterReal, getTokenBuild(), className);
    }

    public void getLWaterReal(String className) {
        post(API.getLocationWaterReal, getTokenBuild(), className);
    }

    public void getDotWaterReal(String className) {
        post(API.getDotWaterReal, getTokenBuild(), className);
    }

    public void getThunderReal(String className) {
        post(API.getThunderReal, getTokenBuild(), className);
    }

    public void getCO2Real(String className) {
        post(API.getCo2Real, getTokenBuild(), className);
    }

    /**
     * 精密空调
     */
    public void getAirAcurrate(String className){
        post(API.accurateAirUrl,getTokenBuild(),className);
    }
    //UPS
    public void getUPS(String className){
        post(API.UpsUrl,getTokenBuild(),className);
    }

    //SerVerData
    public void getServerData(String className){
        post(API.ServerUrl,getTokenBuild(),className);
    }

    //门禁
    public void getDoorUserControl(String classname){
        post(API.doorcontrlUrl,getTokenBuild(),classname);
    }
    //门禁信息返回
    public  void getDoorMessage(String className){
        post(API.doorMessageUrl,getTokenBuild(),className);
    }
    //门禁修改
    public void updateDoorMessage(String id ,String doorName,String doorAddress,String className){
//        FormEncodingBuilder builder = getTokenBuild();
//        builder.add("id",id);
//        builder.add("doorName", doorName);
//        builder.add("doorAddress", doorAddress);
//        post(API.doorUpdateUrl, builder, className);
        StringBuilder params = new StringBuilder();
        params.append("id="+id+"&doorName="+doorName+"&doorAddress="+doorAddress);
        get(API.doorUpdateUrl, params.toString(), className);
    }
    //修改部门
    public void updateSelectionName(String id ,String selectionName,String className){
        StringBuilder params = new StringBuilder();
        params.append("id="+id+"&sectionName="+selectionName);
        get(API.updateSelectionUrl, params.toString(), className);
    }

    //用户添加
    /**
     * sectionId=1&doorAddress=1&userName=阿斯顿
     * &userCard=11111111
     */
    public void addUser(String selectionId,String address,String userName,String card,String className){
        StringBuilder params = new StringBuilder();
        params.append("sectionId="+selectionId+"&doorAddress="+address+"&userName="+userName+"&userCard="+card);
        get(API.addDoorUserUrl,params.toString(),className);
    }

    //查询部门信息
    public void querySelection(String className){
        post(API.queSectionUrl,getTokenBuild(),className);
    }
    //提取卡号
    public void queryCardNumber(String className){
        post(API.getCardNumberUrl,getTokenBuild(),className);
    }

    //删除用户
    public void deleteDoorUser(String userId,String className){
        StringBuilder params = new StringBuilder();
        params.append("id="+userId);
        get(API.deleteUserUrl,params.toString(),className);
    }
    //更新用户名称
    public void updateDoorUserName(String id,String userName,String className){
        StringBuilder params = new StringBuilder();
        params.append("id="+id+"&userName="+userName);
        get(API.updateUserNameUrl,params.toString(),className);
    }

    //解除授权
    public void releasedMod(String userId,String usercard,String className){
        StringBuilder params = new StringBuilder();
        params.append("id="+userId+"&userCard="+usercard);
        get(API.changModUrl,params.toString(),className);
    }

    //请求权限
    public void requestMod(String userId,String usercard,String className){
        StringBuilder params = new StringBuilder();
        params.append("id="+userId+"&userCard="+usercard);
        get(API.requestModUrl,params.toString(),className);
    }

    //获取电量仪信息
    public void getBaterry(String className){
        post(API.batteryUrl,getTokenBuild(),className);
    }
    //删除电量仪设备
    public void deleteBattery(String className,String address,String id,String ip){
        StringBuilder params = new StringBuilder();
        params.append("address="+address+"&ip="+ip+"&id="+id);
        get(API.deleteElctroMachineUrl,params.toString(),className);
    }
    //更新电量仪设备
    public void updateBattery(String className,String jsonStr){
        StringBuilder params = new StringBuilder();
        params.append("emObj="+jsonStr);
        get(API.updateElctroMachineUrl,params.toString(),className);
    }
    ////Add电量仪设备
    public void addBattery(String className,String ip,String location,String address,String name){
        StringBuilder params = new StringBuilder();
        params.append("address="+address+"&location="+location+"&name="+name+"&ipAddress="+ip);
        get(API.addElctroMachineUrl,params.toString(),className);
    }

    public void getSElecReal(String className) {
        post(API.getSElecReal, getTokenBuild(), className);
    }

    public void getPElecReal(String className) {
        post(API.getPElecReal, getTokenBuild(), className);
    }

    public void getFireReal(String className) {
        post(API.getFireReal, getTokenBuild(), className);
    }

    public void getSmokeReal(String className) {
        post(API.getSmokeReal, getTokenBuild(), className);
    }

    public void getInfraredReal(String className) {
        post(API.getInfraredReal, getTokenBuild(), className);
    }

    public void getGlassReal(String className) {
        post(API.getGlassReal, getTokenBuild(), className);
    }

    public void getDoorMagnReal(String className) {
        post(API.getDoorMagnReal, getTokenBuild(), className);
    }

    public void getNewFan(String className) {
        post(API.getNewFan, getTokenBuild(), className);
    }

    public void getWet(String className) {
        post(API.getWet, getTokenBuild(), className);
    }

    public void getLighting(String className) {
        post(API.getLighting, getTokenBuild(), className);
    }

    public void siwtchNewFan(String id, String status, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("status=" + status);
        get(API.siwtchNewFan, param.toString(), className);
    }

    public void getAirF(String className) {
        post(API.getAirF, getTokenBuild(), className);
    }

    //number  1学习开机，2学习关机，3开机，4关机
    public void switchAirF(String id, String ip, String number, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("ip=" + ip).append("&");
        param.append("number=" + number);
        get(API.switchAirF, param.toString(), className);
    }

    public void getElecMachine(String className) {
        post(API.getElecMachine, getTokenBuild(), className);
    }

    public void getTempAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getTempAlart, param.toString(), className);
    }

    public void getNoLocationWaterAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getNoLocationWaterAlart, param.toString(), className);
    }

    public void getLocationWaterAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getLocationWaterAlart, param.toString(), className);
    }

    public void getDotLocationWaterAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getDotLocationWaterAlart, param.toString(), className);
    }

    public void getAirJAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getAirJAlart, param.toString(), className);
    }

    public void getCo2Alart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getCo2Alart, param.toString(), className);
    }

    public void getThunderAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getThunderAlart, param.toString(), className);
    }

    public void getElecMachineAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getElecMachineAlart, param.toString(), className);
    }

    public void getPElecAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getPElecAlart, param.toString(), className);
    }

    public void getSElecAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getSElecAlart, param.toString(), className);
    }

    public void getUPSAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getUPSAlart, param.toString(), className);
    }

    public void getInfraredAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getInfraredAlart, param.toString(), className);
    }

    public void getFireAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getFireAlart, param.toString(), className);
    }

    public void getSmokeAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getSmokeAlart, param.toString(), className);
    }

    public void getGlassAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getGlassAlart, param.toString(), className);
    }

    public void getDoorMagnAlart(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getDoorMagnAlart, param.toString(), className);
    }

    public void getDoorControl(String className) {
        post(API.getDoorControlReal, getTokenBuild(), className);
    }

    public void getDoorControlHistroy(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getDoorControlHistroy, param.toString(), className);
    }

    public void getLocationList(String className) {
        post(API.getLocationList, getTokenBuild(), className);
    }

    public void locationAdd(String locationName, String className) {
        StringBuilder param = new StringBuilder();
        param.append("locationName=" + locationName);
        get(API.locationAdd, param.toString(), className);
    }

    public void locationEdt(String locationId, String name, String className) {
        StringBuilder param = new StringBuilder();
        param.append("locationId=" + locationId).append("&");
        param.append("name=" + name);
        get(API.locationEdt, param.toString(), className);
    }

    public void locationDel(String locationId, String className) {
        StringBuilder param = new StringBuilder();
        param.append("locationId=" + locationId);
        get(API.locationDel, param.toString(), className);
    }

    public void getAlartList(String className) {
        post(API.getAlartList, getTokenBuild(), className);
    }

    public void alartEdt(String id, String timeQuantum1, String timeQuantum2, String timeQuantum3, String intervalTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("timeQuantum1=" + timeQuantum1).append("&");
        param.append("timeQuantum2=" + timeQuantum2).append("&");
        param.append("timeQuantum3=" + timeQuantum3).append("&");
        param.append("intervalTime=" + intervalTime);
        get(API.alartEdit, param.toString(), className);
    }

    public void alartStatus(String id, String number, String status, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("number=" + number).append("&");
        param.append("status=" + status);
        get(API.alartStatus, param.toString(), className);
    }

    public void getIpList(String className) {
        post(API.getIpList, getTokenBuild(), className);
    }

    public void ipStart(String ip, String port, String number, String id, String className) {
        StringBuilder param = new StringBuilder();
        param.append("ip=" + ip).append("&");
        param.append("port=" + port).append("&");
        param.append("number=" + number).append("&");
        param.append("id=" + id);
        get(API.ipStart, param.toString(), className);
    }

    public void ipStop(String ip, String port, String number, String id, String className) {
        StringBuilder param = new StringBuilder();
        param.append("ip=" + ip).append("&");
        param.append("port=" + port).append("&");
        param.append("number=" + number).append("&");
        param.append("id=" + id);
        get(API.ipStop, param.toString(), className);
    }

    public void ipAdd(String deviceId, String ipAddress, String ipPort, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("ipAddress=" + ipAddress).append("&");
        param.append("ipPort=" + ipPort);
        get(API.ipAdd, param.toString(), className);
    }

    public void ipDel(String deviceId, String ipId, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("ipId=" + ipId);
        get(API.ipDel, param.toString(), className);
    }

    public void getIpSetionList(String className) {
        post(API.getIpSetionList, getTokenBuild(), className);
    }

    public void ipEdt(String ipId, String ipAddress, String ipPort, String className) {
        StringBuilder param = new StringBuilder();
        param.append("ipId=" + ipId).append("&");
        param.append("ipAddress=" + ipAddress).append("&");
        param.append("ipPort=" + ipPort);
        get(API.ipEdit, param.toString(), className);
    }

    public void get8060List(String className) {
        post(API.get8060, getTokenBuild(), className);
    }

    public void add8060(String address, String gallery, String location, String ipAddress, String number, String name, String className) {
        StringBuilder param = new StringBuilder();
        param.append("address=" + address).append("&");
        param.append("gallery=" + gallery).append("&");
        param.append("ipAddress=" + ipAddress).append("&");
        param.append("number=" + number).append("&");
        param.append("name=" + name).append("&");
        param.append("location=" + location);
        get(API.add8060, param.toString(), className);
    }

    public void edt8060(String id, String name, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("name=" + name);
        get(API.edt8060, param.toString(), className);
    }

    public void del8060(String id, String ip, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("ip=" + ip);
        get(API.del8060, param.toString(), className);
    }

    public void get8060LocationList(String className) {
        post(API.get8060Location, getTokenBuild(), className);
    }

    public void get8060DeviceList(String className) {
        post(API.get8060Device, getTokenBuild(), className);
    }

    public void get8060IpList(String className) {
        post(API.get8060IP, getTokenBuild(), className);
    }

    public void get8052List(String className) {
        post(API.get8052, getTokenBuild(), className);
    }

    public void add8052(String address, String gallery, String location, String ipAddress, String number, String name, String className) {
        StringBuilder param = new StringBuilder();
        param.append("address=" + address).append("&");
        param.append("gallery=" + gallery).append("&");
        param.append("ipAddress=" + ipAddress).append("&");
        param.append("number=" + number).append("&");
        param.append("name=" + name).append("&");
        param.append("location=" + location);
        get(API.add8052, param.toString(), className);
    }

    public void del8052(String id, String ip, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("ip=" + ip);
        get(API.del8052, param.toString(), className);
    }

    public void edt8052(String id, String name, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("name=" + name);
        get(API.edt8052, param.toString(), className);
    }

    public void get8052DeviceList(String className) {
        post(API.get8052Device, getTokenBuild(), className);
    }

    public void getLocationManageList(String className) {
        post(API.getLocationManage, getTokenBuild(), className);
    }

    public void addLocationManage(String address, String location, String ipAddress, String name, String className) {
        StringBuilder param = new StringBuilder();
        param.append("address=" + address).append("&");
        param.append("ipAddress=" + ipAddress).append("&");
        param.append("name=" + name).append("&");
        param.append("location=" + location);
        get(API.addLocation, param.toString(), className);
    }

    public void edtLocationManage(String id, String name, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("name=" + name);
        get(API.edtLocation, param.toString(), className);
    }

    public void delLocationManage(String id, String ip, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("ip=" + ip);
        get(API.delLocation, param.toString(), className);
    }


    public void getLocationIpList(String className) {
        post(API.getLocationIP, getTokenBuild(), className);
    }

    public void getLocationLocationsList(String className) {
        post(API.getLocationlocations, getTokenBuild(), className);
    }

    public void getCo2ManageList(String className) {
        post(API.getCo2Manage, getTokenBuild(), className);
    }

    public void addCo2Manage(String deviceAddress, String deviceLocation, String ipAddress, String deviceName, String alarmData, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceAddress=" + deviceAddress).append("&");
        param.append("deviceLocation=" + deviceLocation).append("&");
        param.append("ipAddress=" + ipAddress).append("&");
        param.append("alarmData=" + alarmData).append("&");
        param.append("deviceName=" + deviceName);
        get(API.addCo2, param.toString(), className);
    }

    public void edtCo2Manage(String id, String name, String alarmData, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("alarmData=" + alarmData).append("&");
        param.append("name=" + name);
        get(API.edtCo2, param.toString(), className);
    }

    public void delCo2Manage(String id, String ip, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("ip=" + ip);
        get(API.delCo2, param.toString(), className);
    }


    public void getCo2IpList(String className) {
        post(API.getCo2IP, getTokenBuild(), className);
    }

    public void getCo2LocationsList(String className) {
        post(API.getCo2locations, getTokenBuild(), className);
    }

    public void getTempManageList(String className) {
        post(API.getTempManage, getTokenBuild(), className);
    }

    public void addTempManage(String deviceAddress, String deviceLocation, String ipAddress, String deviceName, String maxTemp, String minTemp, String maxHum, String minHum, String interval, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceAddress=" + deviceAddress).append("&");
        param.append("deviceLocation=" + deviceLocation).append("&");
        param.append("ipAddress=" + ipAddress).append("&");
        param.append("maxTemp=" + maxTemp).append("&");
        param.append("minTemp=" + minTemp).append("&");
        param.append("maxHum=" + maxHum).append("&");
        param.append("minHum=" + minHum).append("&");
        param.append("interval=" + interval).append("&");
        param.append("deviceName=" + deviceName);
        get(API.addTemp, param.toString(), className);
    }

    public void edtTempManage(String id, String address, String name, String maxTemp, String minTemp, String maxHum, String minHum, String interval, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("address=" + address).append("&");
        param.append("maxTemp=" + maxTemp).append("&");
        param.append("minTemp=" + minTemp).append("&");
        param.append("maxHum=" + maxHum).append("&");
        param.append("minHum=" + minHum).append("&");
        param.append("interval=" + interval).append("&");
        param.append("name=" + name);
        get(API.edtTemp, param.toString(), className);
    }

    public void delTempManage(String id, String ip, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("ip=" + ip);
        get(API.delTemp, param.toString(), className);
    }


    public void getTempIpList(String className) {
        post(API.getTempIP, getTokenBuild(), className);
    }

    public void getTempLocationsList(String className) {
        post(API.getTemplocations, getTokenBuild(), className);
    }

    public void getAirFManageList(String className) {
        post(API.getAirFManage, getTokenBuild(), className);
    }

    public void addAirFManage(String address, String location, String ipAddress, String name, String className) {
        StringBuilder param = new StringBuilder();
        param.append("address=" + address).append("&");
        param.append("location=" + location).append("&");
        param.append("ipAddress=" + ipAddress).append("&");
        param.append("name=" + name);
        get(API.addAirF, param.toString(), className);
    }

    public void edtAirFManage(String id, String name, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("name=" + name);
        get(API.edtAirF, param.toString(), className);
    }

    public void delAirFManage(String id, String ip, String className) {
        StringBuilder param = new StringBuilder();
        param.append("id=" + id).append("&");
        param.append("ip=" + ip);
        get(API.delAirF, param.toString(), className);
    }


    public void getAirFIpList(String className) {
        post(API.getAirFIP, getTokenBuild(), className);
    }

    public void getAirFLocationsList(String className) {
        post(API.getAirFlocations, getTokenBuild(), className);
    }

    public void getTempHistroyData(String deviceId, String startTime, String endTime, String className) {
        StringBuilder param = new StringBuilder();
        param.append("deviceId=" + deviceId).append("&");
        param.append("startTime=" + startTime).append("&");
        param.append("endTime=" + endTime);
        get(API.getTemoHistroyData, param.toString(), className);
    }

}
