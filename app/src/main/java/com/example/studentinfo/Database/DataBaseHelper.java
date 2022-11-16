package com.example.studentinfo.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.studentinfo.Model.StudentInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "studentinfo.db";
    private static final int DATABASE_VERSION = 1;
    ConnectionSource objConnectionSource;
    private Dao<StudentInfo, Integer> userDao = null;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {


        Log.e("DBStatus", "OnCreate" + connectionSource);

        //creating the user table
        try {
            TableUtils.createTable(connectionSource, StudentInfo.class);

            userDao = DaoManager.createDao(connectionSource, StudentInfo.class);
            Log.e("Member", "member table is created");

            objConnectionSource = connectionSource;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.e("DBStatus", "OnUpgrade" + connectionSource);
        try {
            TableUtils.dropTable(connectionSource, StudentInfo.class, true);

            onCreate(database, connectionSource);
            objConnectionSource = connectionSource;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //user DAO
    public Dao<StudentInfo, Integer> getUserDao() {
        if (userDao == null) {
            try {
                //  userDao = DaoManager.createDao(objConnectionSource,User.class);
                userDao = getDao(StudentInfo.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userDao;
    }
}
