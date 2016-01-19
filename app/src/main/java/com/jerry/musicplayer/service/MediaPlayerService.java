package com.jerry.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.jerry.musicplayer.bean.MusicInfo;
import com.jerry.musicplayer.music.MultiPlayer;
import com.jerry.musicplayer.utils.LogUtils;
import com.jerry.musicplayer.utils.MusicUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by jerry on 16-1-4.
 */
public class MediaPlayerService extends Service {
    private String mTag = "MediaPlayerService";

    private MultiPlayer mPlayer;
    private long[] playList;
    private static long currentId = -1;

    public static final int SHUFFLE_NONE = 0;
    public static final int SHUFFLE_NORMAL = 1;
    public static final int SHUFFLE_AUTO = 2;

    public static final int REPEAT_NONE = 0;
    public static final int REPEAT_CURRENT = 1;
    public static final int REPEAT_ALL = 2;
    //随机模式
    private int mShuffleMode = SHUFFLE_NONE;
    //循环模式
    private int mRepeatMode = REPEAT_NONE;

    @Override
    public IBinder onBind(Intent intent) {
        mPlayer = MultiPlayer.getInstance();
        currentId = MusicUtils.getSharePre(this);
        return new Stub(this);
    }

    /**
     * 创建一个binder对象
     */
    public class Stub extends Binder {
        /**
         * 音乐服务对象
         */
        WeakReference<MediaPlayerService> mService;

        public Stub(MediaPlayerService service) {
            mService = new WeakReference<MediaPlayerService>(service);
        }

        /**
         * 打开音乐
         */
        public void open(String path) {
            mService.get().open(path);
        }

        /**
         * 打开音乐列表
         */
        public void open(long[] list, int position) {
            mService.get().open(list, position);
        }

        /**
         * 打开音乐列表
         */
        public void open(long id, String path) {
            mService.get().open(id, path);
        }

        /**
         * 播放音乐
         */
        public void play() {
            mService.get().play();
        }

        /**
         * 暂停播放
         */
        public void pause() {
            mService.get().pause();
        }

        /**
         * 停止音乐
         */
        public void stop() {
            mService.get().stop();
        }

        /**
         * 设置循环模式
         *
         * @param repeatmode
         */
        public void setRepeatMode(int repeatmode) {
            synchronized (this) {
                mRepeatMode = repeatmode;
            }
        }

        /**
         * 获取当前音乐对象
         */
        public MusicInfo getCurrentMusicInfo() {
            return mService.get().getCurrentMusicInfo();
        }
    }

    /**
     * 打开一个音乐
     *
     * @param path
     */
    private void open(String path) {
        if (path == null)
            return;

        try {
            mPlayer.setDataSource(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 打开一个音乐列表
     *
     * @param list
     * @param position
     */
    private void open(long[] list, int position) {
        if (list != null && list.length > 0) {
            this.playList = list;
            position = (position < 0) ? 0 : position;
            currentId = list[position];
            MusicInfo mInfo = getCurrentMusicInfo();
            open(mInfo.getData());
        }
    }

    /**
     * 打开一个音乐列表
     *
     */
    private void open(long id, String path) {
            currentId = id;
        //每次打开音乐时，对音乐信息进行保存
        MusicUtils.saveSharePre(getApplicationContext(), currentId);
            open(path);
        LogUtils.i(mTag, "打开音乐保存数据---name=" + currentId);
    }

    private void play() {
        if (mPlayer.isInitialized()) {
            mPlayer.play();
        }
    }

    private void pause() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    private void stop() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    /**
     * 获取当前播放的音乐信息
     *
     * @return
     */
    private MusicInfo getCurrentMusicInfo() {
        return MusicUtils.getMusicInfoById(this, currentId);
    }
}
