package cn.meiqu.baseproject.dao;

import com.lidroid.xutils.DbUtils;

import cn.meiqu.baseproject.baseUi.BaseApp;


public class BaseDao {
    DbUtils dbUtils = null;

    public DbUtils getDbUtils() {
        if (dbUtils == null) {
            dbUtils = DbUtils.create(BaseApp.mContext);
        }
        return dbUtils;
    }
}
