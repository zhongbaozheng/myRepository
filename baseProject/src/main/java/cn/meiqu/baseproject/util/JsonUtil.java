package cn.meiqu.baseproject.util;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Fatel on 15-4-3.
 */
public class JsonUtil {

    static Gson mGson = new Gson();

    public static JSONObject getJsonObject(String data) {
        JSONObject object = null;
        try {
            object = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static boolean getStatusLegal(String data) {
        return getStatus(data) == 0;
    }

    public static int getStatus(String data) {
        JSONObject object = getJsonObject(data);
        if (object != null) {
            return object.optInt("error_code", -1);
        }
        return -1;
    }

    public static String getErroMsg(String data) {
        JSONObject object = getJsonObject(data);
        if (object != null) {
            return object.optString("error_msg", "");
        }
        return "";
    }

    public static String getData(String data) {
        JSONObject object = getJsonObject(data);
        if (object != null) {
            return object.optString("data", "");
        }
        return "";
    }

    public static <T> T parseJson2bean(String data, Class<T> c) {
        JSONObject object = getJsonObject(data);
        String info = "";
        if (object != null) {
            info = object.optString("data", "");
        }
        T t = mGson.fromJson(info, c);
        return t;
    }

    public static <T> ArrayList<T> parseJson2Listbean(String data, Type type) {
        JSONObject object = getJsonObject(data);
        String info = "";
        if (object != null) {
            info = object.optString("info", "");
        }
        ArrayList<T> infos = mGson.fromJson(info, type);
        return infos;
    }
}
