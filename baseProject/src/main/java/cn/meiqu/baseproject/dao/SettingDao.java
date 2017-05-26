package cn.meiqu.baseproject.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import cn.meiqu.baseproject.baseUi.BaseApp;
import cn.meiqu.baseproject.bean.User;


public class SettingDao {
    private static SettingDao dao;
    private String settingName = "entrepreneur";
    private String account = "account";
    private String pwd = "pwd";
    private String userJson = "userJson";
    private User user;
    private String hostIp = "hostIp";
    private String hostAddr = "hostAddr";
    private String isLogined = "isLogined";
    private String homePageJson = "homePageJson";

    private SettingDao() {
    }


    public static SettingDao getInstance() {
        if (dao == null)
            dao = new SettingDao();
        return dao;
    }

    private SharedPreferences getPreferences() {
        return BaseApp.mContext.getSharedPreferences(settingName,
                Context.MODE_PRIVATE);
    }

    public void set(String key, String value) {
        Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void set(String key, boolean value) {
        Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String get(String key) {
        return getPreferences().getString(key, "");
    }


    public void setAccount(String name) {
        set(account, name);
    }

    public String getAccount() {
        return get(account);
    }

    public void setPwd(String name) {
        set(pwd, name);
    }

    public String getPwd() {
        return get(pwd);
    }

    public void setUserJson(String json) {
        set(userJson, json);
    }

    public String getUserJson() {
        return get(userJson);
    }

    public User getUser() {
        String userJson = getUserJson();

        User user = new Gson().fromJson(userJson, User.class);
        return user;
    }

    public void setUser(User user) {
        if (user != null) {
            String userJson = new Gson().toJson(user);
            setUserJson(userJson);
        } else {
            setUserJson("");
        }

    }

    public void setHostIp(String value) {
        set(hostIp, value);
    }

    public String getHostIp() {
        return get(hostIp);
    }

    public void setHostAddr(String value) {
        set(hostAddr, value);
    }

    public String getHostAddr() {
        return get(hostAddr);
    }

    public void setIsLogin(int number) {
        set(isLogined, ""+number);
    }


    public boolean getIsLogin() {
        return get(isLogined).equals("1");
    }

    public void setHomePagerJson(String jsonData) {
        set(homePageJson, jsonData);
    }

    public String getHomePagerJson() {
        return get(homePageJson);
    }
}

