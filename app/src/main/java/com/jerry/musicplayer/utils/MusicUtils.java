package com.jerry.musicplayer.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.jerry.musicplayer.Constants;
import com.jerry.musicplayer.MusicPlayerApp;
import com.jerry.musicplayer.R;
import com.jerry.musicplayer.bean.MusicInfo;
import com.jerry.musicplayer.service.MediaPlayerService;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 音乐文件辅助处理，操作的工具类
 * Created by jerry on 16-1-4.
 */
public class MusicUtils {
    private static int sArtId = -2;
    private static Bitmap mCachedBit = null;
    private static final BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();
    private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
    private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    private static final HashMap<Long, Drawable> sArtCache = new HashMap<Long, Drawable>();
    private static int sArtCacheId = -1;

    static {
        // 唱片集封面的缓存
        // 565 是快速解码并且显示
        sBitmapOptionsCache.inPreferredConfig = Bitmap.Config.RGB_565;
        sBitmapOptionsCache.inDither = false;

        sBitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        sBitmapOptions.inDither = false;
    }

    /**
     * 获取sd卡上所有的音乐文件
     *
     * @param context
     * @return
     */
    public static List<MusicInfo> getMusicInfoList(Context context) {
        List<MusicInfo> musicInfoList = new ArrayList<MusicInfo>();
        ContentResolver resolver = context.getContentResolver();
        //获取所有音乐列表
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        while (cursor.moveToNext()) {
            MusicInfo musicInfo = getMusicInfoByCursor(cursor);
            musicInfoList.add(musicInfo);
        }

        return musicInfoList;
    }

    /**
     * 通过id获取音乐
     *
     * @param context
     * @param id
     * @return
     */
    public static MusicInfo getMusicInfoById(Context context, long id) {
        if (id != -1) {
            ContentResolver resolver = context.getContentResolver();
            //获取所有音乐列表
            Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, "_id="+id, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if (cursor.moveToNext()) {
                return getMusicInfoByCursor(cursor);
            }
        }
        return null;
    }

    /**
     * 通过cursor获取音乐信息
     *
     * @param cursor
     * @return
     */
    private static MusicInfo getMusicInfoByCursor(Cursor cursor) {
        MusicInfo musicInfo = new MusicInfo();
        musicInfo.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
        musicInfo.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
        musicInfo.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
        musicInfo.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
        musicInfo.setData(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
        musicInfo.setDisplay_name(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
        musicInfo.setYear(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR)));
        musicInfo.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
        musicInfo.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
        musicInfo.setAlbumId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)));
        return musicInfo;
    }

    /**
     * 获取艺术家图片，如果使用unknown 则(album_id = -1)
     * 当没有找到相对应的唱片艺术图标的时候，会返回默认的唱片图标
     *
     * @param context
     * @param song_id
     * @param album_id
     * @return
     */
    public static Bitmap getArtwork(Context context, long song_id, long album_id) {
        return getArtwork(context, song_id, album_id, true);
    }

    /**
     * 获取艺术家图片，如果使用unknown 则(album_id = -1)
     *
     * @param context
     * @param song_id
     * @param album_id
     * @param allowdefault
     * @return
     */
    public static Bitmap getArtwork(Context context, long song_id, long album_id,
                                    boolean allowdefault) {

        if (album_id < 0) {
            // 这些图片不在数据库中，因此通过路径获取文件
            if (song_id >= 0) {
                Bitmap bm = getArtworkFromFile(context, song_id, -1);
                if (bm != null) {
                    return bm;
                }
            }
            if (allowdefault) {
                return getDefaultArtwork(context);
            }
            return null;
        }

        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
        if (uri != null) {
            InputStream in = null;
            try {
                in = res.openInputStream(uri);
                return BitmapFactory.decodeStream(in, null, sBitmapOptions);
            } catch (FileNotFoundException ex) {
                // The album art thumbnail does not actually exist. Maybe the user deleted it, or
                // maybe it never existed to begin with.
                Bitmap bm = getArtworkFromFile(context, song_id, album_id);
                if (bm != null) {
                    if (bm.getConfig() == null) {
                        bm = bm.copy(Bitmap.Config.RGB_565, false);
                        if (bm == null && allowdefault) {
                            return getDefaultArtwork(context);
                        }
                    }
                } else if (allowdefault) {
                    bm = getDefaultArtwork(context);
                }
                return bm;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                }
            }
        }

        return null;
    }

    private static final String sExternalMediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString();

    /**
     * 获取唱片艺术的文件
     *
     * @param context
     * @param songid
     * @param albumid
     * @return
     */
    private static Bitmap getArtworkFromFile(Context context, long songid, long albumid) {
        Bitmap bm = null;
        byte[] art = null;
        String path = null;

        if (albumid < 0 && songid < 0) {
            throw new IllegalArgumentException("必须是一个唱片，或者一个音乐文件的id");
        }

        try {
            if (albumid < 0) {
                Uri uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            } else {
                Uri uri = ContentUris.withAppendedId(sArtworkUri, albumid);
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            }
        } catch (FileNotFoundException ex) {
            //
        }
        if (bm != null) {
            mCachedBit = bm;
        }
        return bm;
    }

    private static Bitmap getDefaultArtwork(Context context) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeStream(
                context.getResources().openRawResource(R.drawable.albumart_mp_unknown), null, opts);
    }

    /**
     * 获取音乐服务
     *
     * @return
     */
    public static MediaPlayerService.Stub getService() {
        return MusicPlayerApp.getService();
    }

    /**
     * 保存当前数据
     *
     * @param id
     */
    public static void saveSharePre(Context context, long id) {
        SharedPreferences sp = context.getSharedPreferences(Constants.CURRENT_MUSIC, Context.MODE_APPEND);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(Constants.CURRENT_MUSIC_ID_KEY, id);
        edit.commit();
    }

    /**
     * 获取当前数据
     */
    public static long getSharePre(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.CURRENT_MUSIC, Context.MODE_APPEND);
        return sp.getLong(Constants.CURRENT_MUSIC_ID_KEY, -1);
    }
}
