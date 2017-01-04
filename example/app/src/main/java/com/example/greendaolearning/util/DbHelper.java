package com.example.greendaolearning.util;

import android.content.Context;

import com.example.greendaolearning.greendao.DaoMaster;
import com.example.greendaolearning.greendao.DaoSession;

/**
 * Created by Administrator on 2017/1/3.
 */

public class DbHelper {
    private static  DbHelper dbhelper=null;
    private Context context;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private final String dbname="greenDaoTest";
    public DbHelper(Context context){
        this.context=context;
        init();
    }
    private void init(){
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context,dbname);
        daoMaster=new DaoMaster(openHelper.getWritableDatabase());
        daoSession=daoMaster.newSession();
    }

    public static DbHelper getInstance(Context context){
        if(dbhelper==null)
            dbhelper= new DbHelper(context);
        return dbhelper;
    }

    public DaoMaster getDaoMaster(){
        return daoMaster;
    }
    public DaoSession getDaoSession(){
        return daoSession;
    }
}
