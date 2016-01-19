package com.jerry.musicplayer.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

/**
 * 数据库操作帮助类
 * Created by jerry on 16-1-4.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private final String mTag = "DBOpenHelper";
    //数据库名称
    private final static String DB_NAME = "music_player.db";
    //当前数据库版本
    private static final int DB_VERSION = 1;

    private static final String COL_TYPE_AUTO_ID = "INTEGER PRIMARY KEY";
    private static final String COL_TYPE_INT = "INT";
    private static final String COL_TYPE_TEXT = "TEXT";
    private static final String COL_TYPE_BLOB = "BLOB";
    private static final String COL_TYPE_BIGINT = "BIGINT";
    private static final String COL_TYPE_INT_DEFAULT = "INT default -1";

    private static DBOpenHelper instance;
    private Context mContext;

    private DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    /**
     * 获取databasehelper实例
     * @param context
     * @return
     */
    public static DBOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        initDbInfo(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.beginTransaction();
        if (oldVersion < 3) {
            sqLiteDatabase.rawQuery("CREATE TABLE ZJJ_XJJ WH", new String[]{});
        }

        if (oldVersion == 3 && newVersion == 4) {
        }
        if (oldVersion < 5) {
        }
        sqLiteDatabase.setTransactionSuccessful();

        sqLiteDatabase.endTransaction();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    /**
     * 执行sql脚本
     * @param db
     */
    private void initDbInfo(SQLiteDatabase db) {
        try {
            InputStream in = mContext.getAssets().open("init_db.sql");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(in, "utf-8"));
            String sql = null;
            while ((sql = bufferedReader.readLine()) != null) {
                if (!TextUtils.isEmpty(sql)) {
                    db.execSQL(sql);
                }
            }
            bufferedReader.close();
            in.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断列是否存在
     * @param db
     * @param tableName
     * @param columnName
     * @return
     */
    private boolean checkColumnExist(SQLiteDatabase db, String tableName,
                                     String columnName) {
        // db.beginTransaction();
        boolean result = false;
        Cursor cursor = null;
        try {
            // 查询一行
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0",
                    null);
            result = cursor != null && cursor.getColumnIndex(columnName) != -1;
            // db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            // db.endTransaction();
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * 是否存在表
     * @param db
     * @return
     */
    public boolean isTableExists(SQLiteDatabase db, String tableName) {
        String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tableName+"';";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建表
     * @param paramSQLiteDatabase
     * @param paramMap
     * @param tablename
     */
    private void createTable(SQLiteDatabase paramSQLiteDatabase,
                             Map<String, String> paramMap, String tablename) {
        StringBuilder createtablestr = new StringBuilder();
        createtablestr.append("CREATE TABLE " + tablename + " (");

        Iterator<Map.Entry<String, String>> iter = paramMap.entrySet()
                .iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
                    .next();
            String key = entry.getKey();
            String value = entry.getValue();
            String colstr = key.toString() + " " + value.toString() + ",";
            createtablestr.append(colstr);
        }
        // 去掉末尾的逗号
        if (createtablestr.lastIndexOf(",") == createtablestr.length() - 1) {
            createtablestr.deleteCharAt(createtablestr.length() - 1);
        }
        createtablestr.append(")");
        // System.out.println("sql========"+createtablestr.toString());
        paramSQLiteDatabase.execSQL(createtablestr.toString());

    }

    /**
     * 关闭db
     */
    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

}
