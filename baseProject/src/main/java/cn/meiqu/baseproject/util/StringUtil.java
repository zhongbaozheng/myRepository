package cn.meiqu.baseproject.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class StringUtil {

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(String s) {
        if (s == null)
            return true;
        else if ("".equals(s))
            return true;
        else if (s.equals("null") || s.equals("NULL")) {
            return true;
        }
        return false;
    }

    public static String getMAC(String s) {
        String mac = "";
        if (s == null) {
            return mac;
        }
        int length = s.length() / 2;
        for (int i = 0; i < length; i++) {
            mac = mac + s.substring(2 * i, 2 * (i + 1));
            if (i != (length - 1)) {
                mac = mac + ":";
            }
        }
        return mac;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            stringBuilder.append(byteToHexString(src[i]));
        }
        return stringBuilder.toString();
    }

    public static String byteToHexString(byte src) {
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            hv = "0" + hv;
        }

        return hv;
    }

    public static String intToHexString(int src) {
        StringBuilder stringBuilder = new StringBuilder("");
        int v = src & 0xFF;
        // System.out.println("十进制---" +
        // v+"    >>>>>>>>>十六进制 -----"+Integer.toHexString(v));
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);

        return stringBuilder.toString();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toLowerCase().toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }

    public static Map<String, String> getParam(String uri) {
        Map<String, String> params = new HashMap<String, String>();
        try {
            if (!isEmpty(uri) && uri.contains("?")) {
                String subStr = uri.substring(uri.indexOf("?") + 1);
                String[] ary = subStr.split("&");
                for (int i = 0; i < ary.length; i++) {
                    String[] temp = ary[i].split("=");
                    if (temp.length < 2) {
                        params.put(temp[0], "");
                    } else {
                        params.put(temp[0], temp[1]);
                    }
                }
//                params.put(replaceName, replaceValue);
                return params;
            }
        } catch (Exception e) {
        }
        return params;
    }

    private final static DecimalFormat df = new DecimalFormat("0.00");

    /**
     * @param ds
     * @return 有小数部分则输出 #.0
     */
    public final static String getLess(String ds) {
        double d = Double.parseDouble(ds);
        if (d - Math.floor(d) > 0) {
            return df.format(d);
        } else {
            return "" + new Double(d).longValue();
        }
    }
}
