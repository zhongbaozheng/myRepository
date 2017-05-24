//package cn.meiqu.baseproject.util;
//
//import android.content.Context;
//import android.net.wifi.ScanResult;
//import android.net.wifi.WifiConfiguration;
//import android.net.wifi.WifiManager;
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.util.Enumeration;
//import java.util.List;
//
//import cn.meiqu.baseproject.basefatel.BaseApp;
//import cz.msebera.android.httpclient.conn.util.InetAddressUtils;
//
//
//public class WifiUtil {
//    public static WifiManager getWifiManager() {
//        return (WifiManager) BaseApp.mContext
//                .getSystemService(Context.WIFI_SERVICE);
//    }
//
//    public static boolean openWIFI() {
//        WifiManager wifiManager = getWifiManager();
//        // 判断wifi是否开启
//        if (!wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(true);
//        }
//        return true;
//    }
//
//    public static List<WifiConfiguration> getConfigList() {
//        return getWifiManager().getConfiguredNetworks();
//    }
//
//    public static List<ScanResult> getScanResults() {
//        return getWifiManager().getScanResults();
//    }
//
//    public static void startScan() {
//        WifiManager wifiManager = getWifiManager();
//        openWIFI();
//        wifiManager.startScan();
//    }
//
//    public static int getLevel(Context mContext) {
//        WifiManager mWifiManager = (WifiManager) mContext
//                .getSystemService(Context.WIFI_SERVICE);
//        if (mWifiManager.getConnectionInfo() != null) {
//            return mWifiManager.getConnectionInfo().getRssi();
//        } else {
//            return -100;
//        }
//    }
//
//    public static String getCurrentWifiName() {
//        if (getWifiManager().getConnectionInfo() == null) {
//            return "";
//        }
//        if (getWifiManager().getConnectionInfo().getSSID() != null)
//            return getWifiManager().getConnectionInfo().getSSID()
//                    .replace("\"", "");
//        else {
//            return "";
//        }
//    }
//
//    public static String getLocalIPAddress() {
//        try {
//            for (Enumeration<NetworkInterface> en = NetworkInterface
//                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
//                NetworkInterface intf = en.nextElement();
//                for (Enumeration<InetAddress> enumIpAddr = intf
//                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                    InetAddress inetAddress = enumIpAddr.nextElement();
//
//                    if (!inetAddress.isLoopbackAddress()
//                            && InetAddressUtils.isIPv4Address(inetAddress
//                            .getHostAddress())) {
//                        return inetAddress.getHostAddress().toString();
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            // Log.e(LOG_TAG, ex.toString());
//        }
//        return "";
//    }
//
//    public static int getCurrentNetId() {
//        if (getWifiManager().getConnectionInfo() != null) {
//            return getWifiManager().getConnectionInfo().getNetworkId();
//        } else {
//            return 0;
//        }
//    }
//
//    public static String getTypeByCapabilities(String capabilities) {
//        if (capabilities.contains("WPA2")) {
//            return "1";
//        } else if (capabilities.contains("WPA")) {
//            return "2";
//        } else if (capabilities.contains("WEP")) {
//            return "3";
//        } else {
//            return "0";
//        }
//    }
//
//    // 判定指定WIFI是否已经配置好,依据WIFI的地址BSSID,返回NetId
//    public static int isConfiguration(String SSID) {
//        LogUtil.log("ssid=" + SSID);
//        int netId = -1;
//        List<WifiConfiguration> wifiConfigList = getConfigList();
//        for (int i = 0; i < wifiConfigList.size(); i++) {
//            LogUtil.log("ssid=" + wifiConfigList.get(i).SSID);
//            if (wifiConfigList.get(i).SSID.replace("\"", "").equals(SSID)) {// 地址相同
//                netId = wifiConfigList.get(i).networkId;
//                break;
//            }
//        }
//        return netId;
//    }
//
//    public static boolean removeConfig(int netId) {
//        return getWifiManager().removeNetwork(netId);
//    }
//
//    // 连接指定Id的WIFI
//    public static boolean connectWifi(int netId) {
//        return getWifiManager().enableNetwork(netId, true);
//    }
//
//    public static boolean disConnectWifi(int netId) {
//        return getWifiManager().disableNetwork(netId);
//    }
//
//    /* 设置要连接的热点的参数 */
//    public static int addWifiConfig(String ssid, String password, String type) {
//        ssid = ssid.replace("\"", "");
//        WifiConfiguration apConfig = new WifiConfiguration();
//        apConfig.allowedAuthAlgorithms.clear();
//        apConfig.allowedGroupCiphers.clear();
//        apConfig.allowedKeyManagement.clear();
//        apConfig.allowedPairwiseCiphers.clear();
//        apConfig.allowedProtocols.clear();
//        //
//        apConfig.SSID = "\"" + ssid + "\"";
//
//        // none
//        if (type.equals("0")) {
//            // apConfig.wepKeys[0] = "";
//            apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//            // apConfig.wepTxKeyIndex = 0;
//        }
//        // wps
//        else if (type.equals("1") || type.equals("2")) {
//            //
//            apConfig.preSharedKey = "\"" + password + "\"";
//            apConfig.hiddenSSID = true;
//            apConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
//            apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
//            apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
//            apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
//// 此处需要修改否则不能自动重联
//// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
//            apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
//            apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
//            apConfig.status = WifiConfiguration.Status.ENABLED;
//        }
//        // wep
//        else if (type.equals("3")) {
//            apConfig.hiddenSSID = true;
//            apConfig.wepKeys[0] = "\"" + password + "\"";
//            apConfig.allowedAuthAlgorithms
//                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
//            apConfig.allowedGroupCiphers
//                    .set(WifiConfiguration.GroupCipher.CCMP);
//            apConfig.allowedGroupCiphers
//                    .set(WifiConfiguration.GroupCipher.TKIP);
//            apConfig.allowedGroupCiphers
//                    .set(WifiConfiguration.GroupCipher.WEP40);
//            apConfig.allowedGroupCiphers
//                    .set(WifiConfiguration.GroupCipher.WEP104);
//            apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//        }
//
//        int netId = getWifiManager().addNetwork(apConfig);
//        if (netId != -1) {
//            getWifiManager().saveConfiguration();
//        }
//        LogUtil.log("netId=" + netId);
//        return netId;
//    }
//}
