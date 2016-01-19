package com.jerry.musicplayer.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaScannerConnection;
import android.provider.MediaStore;

/**
 * Created by jerry on 16-1-4.
 */
public class DataBaseAdapter{
    private final String mTag = "DataBaseAdapter";
    private final Context context;

    private DBOpenHelper mDbHelper = null;

    public DataBaseAdapter(Context mContext) {
        this.context = mContext;
        mDbHelper = DBOpenHelper.getInstance(mContext);
    }

    public void getMusicInfo(){
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        ContentResolver resolver = context.getContentResolver();
        //获取所有音乐列表
//        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
//        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
//        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
//        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
//        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR));
//        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
        //获取播放表单
        String[] cols = new String[] {MediaStore.Audio.Playlists.NAME};
        String whereclause = MediaStore.Audio.Playlists.NAME + " != ''";
        Cursor cursor = resolver.query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, cols, whereclause, null,
                MediaStore.Audio.Playlists.NAME);
        boolean done = false;

    }

}
