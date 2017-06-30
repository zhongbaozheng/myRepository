package cn.meiqu.baseproject;

import android.util.Log;

import cn.meiqu.baseproject.dao.SettingDao;
import cn.meiqu.baseproject.util.StringUtil;

public class API {
    public static String port = "8090";
    public static String getHomePage = "ktr-mrms/queHeadJson.html";
    public static String getHomeChildPage = "ktr-mrms/queMonitorJson.html";
    public static String getHomeChildThirdPage = "ktr-mrms/queMainMenuJspn.html";
    public static String login = "ktr-mrms/loginJson.html";
    public static String getTempReal = "ktr-mrms/reqHumitureDataJson.html";
    public static String getNoLocationWaterReal = "ktr-mrms/reqNonLocationDataJson.html";
    public static String getLocationWaterReal = "ktr-mrms/reqLocationDataJson.html";
    public static String getDotWaterReal = "ktr-mrms/reqDotModelDataJson.html";
    public static String getThunderReal = "ktr-mrms/reqAntiThunderDataJson.html";
    public static String getCo2Real = "ktr-mrms/reqCo2DataJson.html";
    public static String getSElecReal = "ktr-mrms/reqElectricSupplyDataJson.html";
    public static String getPElecReal = "ktr-mrms/reqElectricBoxDataJson.html";
    public static String getFireReal = "ktr-mrms/reqFireControlDataJson.html";
    public static String getSmokeReal = "ktr-mrms/reqSmokeDataJson.html";
    public static String getInfraredReal = "ktr-mrms/reqInfraredDataJson.html";
    public static String getGlassReal = "ktr-mrms/reqGlassDataJson.html";
    public static String getDoorMagnReal = "ktr-mrms/reqDoorContactDataJson.html";
    public static String getNewFan = "ktr-mrms/reqNewFanDataJson.html";
    public static String siwtchNewFan = "ktr-mrms/switchKtr8060Json.html";
    public static String getWet = "ktr-mrms/reqHumidificationDataJson.html";
    public static String getLighting = "ktr-mrms/reqLightingDataJson.html";
    public static String getAirF = "ktr-mrms//queFissionAirControlJson.html";
    public static String switchAirF = "ktr-mrms/fissionAirControlJson.html";
    public static String getElecMachine = "ktr-mrms/reqElectricMeterDataJson.html";
    public static String getDoorControlReal = "ktr-mrms/reqDoorRealTimeJson.html";
    //
    public static String getTempAlart = "ktr-mrms/reqHumitureAlarmDataJson.html";
    public static String getTemoHistroyData = "ktr-mrms/reqHumitureHisDataJson.html";
    public static String getNoLocationWaterAlart = "ktr-mrms/reqNonLocationAlarmDataJson.html";
    public static String getLocationWaterAlart = "ktr-mrms/reqLocationAlarmDataJson.html";
    public static String getDotLocationWaterAlart = "ktr-mrms/reqDotModelAlarmDataJson.html";
    public static String getAirJAlart = "ktr-mrms/reqCracAlarmDataJson.html";
    public static String getCo2Alart = "ktr-mrms/reqCo2AlarmDataJson.html";
    public static String getThunderAlart = "ktr-mrms/reqAntiThunderAlarmDataJson.html";
    public static String getElecMachineAlart = "ktr-mrms/reqElectricMeterAlarmDataJson.html";
    public static String getPElecAlart = "ktr-mrms/reqElectricBoxAlarmDataJson.html";
    public static String getSElecAlart = "ktr-mrms/reqElectricSupplyAlarmDataJson.html";
    public static String getUPSAlart = "ktr-mrms/reqUpsAlarmDataJson.html";
    public static String getInfraredAlart = "ktr-mrms/reqInfraredAlarmDataJson.html";
    public static String getGlassAlart = "ktr-mrms/reqGlassAlarmDataJson.html";
    public static String getFireAlart = "ktr-mrms/reqFireControlAlarmDataJson.html";
    public static String getSmokeAlart = "ktr-mrms/reqSmokeAlarmDataJson.html";
    public static String getDoorMagnAlart = "ktr-mrms/reqDoorContactAlarmDataJson.html";
    public static String getDoorControlHistroy = "ktr-mrms/reqDoorOpenRecordJson.html";

    //
    public static String getLocationList = "ktr-mrms/queDeviceLocationJson.html";
    public static String locationAdd = "ktr-mrms/addDeviceLocationJson.html";
    public static String locationEdt = "ktr-mrms/updDeviceLocationJson.html";
    public static String locationDel = "ktr-mrms/delDeviceLocationJson.html";

    public static String getAlartList = "ktr-mrms/queDeviceAlarmJson.html";
    public static String alartEdit = "ktr-mrms/updDeviceAlarmParameter.html";
    public static String alartStatus = "ktr-mrms/updDeviceAlarmStatusJson.html";

    public static String getIpList = "ktr-mrms/queIpParameterJson.html";
    public static String getIpSetionList = "ktr-mrms/queDeviceNameJson.html";
    public static String ipEdit = "ktr-mrms/updIpJson.html";
    public static String ipStart = "ktr-mrms/startConnectJson.html";
    public static String ipStop = "ktr-mrms/stopConnectJson.html";
    public static String ipAdd = "ktr-mrms/addIpJson.html";
    public static String ipDel = "ktr-mrms/delIpJson.html";
    public static String get8060 = "ktr-mrms/queKtr8060Json.html";
    public static String add8060 = "ktr-mrms/addKtr8060Json.html";
    public static String edt8060 = "ktr-mrms/updKtr8060Json.html";
    public static String del8060 = "ktr-mrms/delKtr8060Json.html";
    public static String get8060Location = "ktr-mrms/queLocationJson.html";
    public static String get8060Device = "ktr-mrms/queKTR8060DeviceJson.html";
    public static String get8060IP = "ktr-mrms/queIpJson.html";
    public static String get8052 = "ktr-mrms/queKtr8052Json.html";
    public static String add8052 = "ktr-mrms/addKtr8052Json.html";
    public static String edt8052 = "ktr-mrms/updKtr8052.html";
    public static String del8052 = "ktr-mrms/delKtr8052Json.html";
    public static String get8052Device = "ktr-mrms/queKTR8052DeviceJson.html";

    public static String getLocationManage = "ktr-mrms/queLocationManageJson.html";
    public static String edtLocation = "ktr-mrms/updLocationManageInfo.html";
    public static String addLocation = "ktr-mrms/addLocationManageInfo.html";
    public static String delLocation = "ktr-mrms/delLocationManageInfo.html";
    public static String getLocationIP = "ktr-mrms/queIpJson.html";
    public static String getLocationlocations = "ktr-mrms/queLocationJson.html";

    public static String getCo2Manage = "ktr-mrms/queCo2ManageJson.html";
    public static String edtCo2 = "ktr-mrms/updCo2ManageInfoJson.html";
    public static String addCo2 = "ktr-mrms/addCo2ManageInfoJson.html";
    public static String delCo2 = "ktr-mrms/delCo2ManageInfoJson.html";
    public static String getCo2IP = "ktr-mrms/queIpJson.html";
    public static String getCo2locations = "ktr-mrms/queLocationJson.html";

    public static String getTempManage = "ktr-mrms/queHumitureManageJson.html";
    public static String edtTemp = "ktr-mrms/updHumitureManageInfo.html";
    public static String addTemp = "ktr-mrms/addHumitureManageInfo.html";
    public static String delTemp = "ktr-mrms/delHumitureManageInfo.html";
    public static String getTempIP = "ktr-mrms/queIpJson.html";
    public static String getTemplocations = "ktr-mrms/queLocationJson.html";

    public static String getAirFManage = "ktr-mrms/queFissionAirManageJson.html";
    public static String edtAirF = "ktr-mrms/updFissionAirManageInfoJson.html";
    public static String addAirF = "ktr-mrms/addFissionAirManageInfoJson.html";
    public static String delAirF = "ktr-mrms/delFissionAirManageInfoJson.html";
    public static String getAirFIP = "ktr-mrms/queIpJson.html";
    public static String getAirFlocations = "ktr-mrms/queLocationJson.html";

    public static String getDoorManage = "ktr-mrms/queDoorUserManageJson.html";
    public static String addDoorManage = "ktr-mrms/addDoorUser.html";
    public static String edtDoorF = "ktr-mrms/updFissionAirManageInfoJson.html";
    public static String delDoorF = "ktr-mrms/delFissionAirManageInfoJson.html";
    public static String getDoorIP = "ktr-mrms/queIpJson.html";
    public static String addDoorSection = "ktr-mrms/addSectionJson.html";
    public static String getDoorSection = "ktr-mrms/queSectionJson.html";
    public static String addDoorList = "ktr-mrms/addDoorJson.html";
    public static String getDoorList = "ktr-mrms/queDoorJson.html";
    //
    //
    public static String webOperateUrl = "ktr-mrms/main.html";
    public static String webAirCondUrl = "ktr-mrms/queCracRealTime.html";
    public static String webUPSUrl = "ktr-mrms/queUpsRealTime.html";
    public static String webDoorUrl = "ktr-mrms/queDoorUserManage.html";
    public static String webElecUrl = "ktr-mrms//queElectricMeterManage.html";


    //精密空调
    public static String  accurateAirUrl = "/ktr-mrms/reqCracRealTimeDataJson.html";
    //UPS
    public static String UpsUrl = "/ktr-mrms/reqUpsRealTimeDataJson.html";
    //Server服务器参数显示
    public static String ServerUrl = "/ktr-mrms/queServers.html";
    //门禁
    public static String doorcontrlUrl = "ktr-mrms/queDoorUserManageJson.html";
    //门禁信息返回
    public static String doorMessageUrl = "ktr-mrms/queDoorJson.html";
    //修改门禁
    public static String doorUpdateUrl = "ktr-mrms/updSeDoorJson.html";
    //部门信息
    public static String queSectionUrl = "ktr-mrms/queSectionJson.html";
    //提取卡号
    public static String getCardNumberUrl = "ktr-mrms/getCardNumber.html";
    //门禁用户添加
    public static String addDoorUserUrl = "ktr-mrms/addDoorUserJson.html";
    //修改部门
    public static String updateSelectionUrl = "ktr-mrms/updSectionJson.html";

    //删除用户
    public static String deleteUserUrl = "ktr-mrms/delDoorUserJson.html";
    //更改用户
    public static String updateUserNameUrl = "ktr-mrms/updDoorUserJson.html";
    //授权管理,解除权限
    public static String changModUrl = "ktr-mrms/relieveDoorUserJson.html";
    //请求权限
    public static String requestModUrl = "ktr-mrms/accreditDoorUserJson.html";
    //电量仪信息返回
    public static String batteryUrl = "ktr-mrms/queElectricMeterManageJson.html";
    //删除
    public static String deleteElctroMachineUrl = "ktr-mrms/delElectricMeterManageInfoJson.html";
    //更新
    public static String updateElctroMachineUrl = "ktr-mrms/updElectricMeterManageInfoJson.html";
    //添加电量仪设备
    public static String addElctroMachineUrl = "ktr-mrms/addElectricMeterManageInfoJson.html";
    //loginUser
    public static String showCurrentLoginUserUrl = "ktr-mrms/showCurrentLoginUserJson.html";
    //更新管理员
    public static String updateLoginUserUrl = "ktr-mrms/updCurrentLoginUserJson.html";

    //精密空调发送控制
    public static String kgjStateOpenUrl = "ktr-mrms/opencracJson.html";
    public static String kgjStateCloseUrl = "ktr-mrms/closecracJson.html";


    public static String getBaseUrl() {
        String baseUrl = StringUtil.isEmpty(SettingDao.getInstance().getHostIp()) ? SettingDao.getInstance().getHostAddr() : SettingDao.getInstance().getHostIp();
        Log.e("baseUrl",baseUrl);
//        return "http://" + baseUrl + ":" + port + "/";
        return "http://" + baseUrl  + "/";
    }

    public static String getAbsolutePath(String url) {
        Log.e("rul",getBaseUrl()+url);
        return getBaseUrl() + url;
    }
}
