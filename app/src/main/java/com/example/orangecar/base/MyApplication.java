package com.example.orangecar.base;

import android.app.Application;

import com.example.orangecar.greendao.DaoMaster;
import com.example.orangecar.greendao.DaoSession;
import com.example.orangecar.until.MyOpenHelper;

import org.greenrobot.greendao.database.Database;

public class MyApplication extends Application {

    private static DaoSession daoSession;
    private static MyApplication appApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
        setGreenDao();
    }

    private void setGreenDao() {
        MyOpenHelper openHelper = new MyOpenHelper(getApplicationContext(), "exam", null);
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static MyApplication getApplication() {
        return appApplication;
    }
    /**
     * 获取daosession
     *
     * @return
     */
    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
