package com.jerry.musicplayer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.jerry.musicplayer.R;
import com.jerry.musicplayer.bean.MusicInfo;
import com.jerry.musicplayer.service.MediaPlayerService;
import com.jerry.musicplayer.utils.MusicUtils;

import java.io.IOException;

/**
 * Created by jerry on 16-1-3.
 */
public class MusicPlayActivity extends Activity{
    private String mTag = "MusicPlayActivity";
    private static final int REFRESH = 1;

    private MediaPlayerService.Stub mService;
    private boolean paused;
    private MusicInfo currMusic;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case ALBUM_ART_DECODED:
//                    mAlbum.setImageBitmap((Bitmap)msg.obj);
//                    mAlbum.getDrawable().setDither(true);
//                    break;

                case REFRESH:
                    long next = refreshNow();
                    queueNextRefresh(next);
                    break;

//                case QUIT:
//                    // This can be moved back to onCreate once the bug that prevents
//                    // Dialogs from being started from onCreate/onResume is fixed.
//                    new AlertDialog.Builder(MediaPlaybackActivity.this)
//                            .setTitle(R.string.service_start_error_title)
//                            .setMessage(R.string.service_start_error_msg)
//                            .setPositiveButton(R.string.service_start_error_button,
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int whichButton) {
//                                            finish();
//                                        }
//                                    })
//                            .setCancelable(false)
//                            .show();
//                    break;

                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.audio_player);
        mService = MusicUtils.getService();
        mService.getCurrentMusicInfo();
        initView();
    }

    @Override
    protected void onStart() {
        paused = false;
        super.onStart();
    }

    @Override
    protected void onStop() {
        paused = true;
        super.onStop();
    }

    /**
     * 初始化界面
     */
    private void initView() {

    }

    /**
     * 初始化数据
     */
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long currentId = MusicUtils.getSharePre(MusicPlayActivity.this);
                currMusic = MusicUtils.getMusicInfoById(MusicPlayActivity.this, currentId);
            }
        }).start();
    }

    private void queueNextRefresh(long delay) {
        if (!paused) {
            Message msg = mHandler.obtainMessage(REFRESH);
            mHandler.removeMessages(REFRESH);
            mHandler.sendMessageDelayed(msg, delay);
        }
    }

    private void setPauseButtonImage() {
//        try {
//            if (mService != null && mService.isPlaying()) {
//                mPauseButton.setImageResource(android.R.drawable.ic_media_pause);
//            } else {
//                mPauseButton.setImageResource(android.R.drawable.ic_media_play);
//            }
//        } catch (RemoteException ex) {
//        }
    }

    private long refreshNow() {
//        if(mService == null)
//            return 500;
//        try {
//            long pos = mPosOverride < 0 ? mService.position() : mPosOverride;
//            long remaining = 1000 - (pos % 1000);
//            if ((pos >= 0) && (mDuration > 0)) {
//                mCurrentTime.setText(MusicUtils.makeTimeString(this, pos / 1000));
//
//                if (mService.isPlaying()) {
//                    mCurrentTime.setVisibility(View.VISIBLE);
//                } else {
//                    // blink the counter
//                    int vis = mCurrentTime.getVisibility();
//                    mCurrentTime.setVisibility(vis == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
//                    remaining = 500;
//                }
//
//                mProgress.setProgress((int) (1000 * pos / mDuration));
//            } else {
//                mCurrentTime.setText("--:--");
//                mProgress.setProgress(1000);
//            }
//            // return the number of milliseconds until the next full second, so
//            // the counter can be updated at just the right time
//            return remaining;
//        } catch (RemoteException ex) {
//        }
        return 500;
    }

    private static class Worker implements Runnable {
        private final Object mLock = new Object();
        private Looper mLooper;

        /**
         * Creates a worker thread with the given name. The thread
         * then runs a {@link android.os.Looper}.
         * @param name A name for the new thread
         */
        Worker(String name) {
            Thread t = new Thread(null, this, name);
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
            synchronized (mLock) {
                while (mLooper == null) {
                    try {
                        mLock.wait();
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }

        public Looper getLooper() {
            return mLooper;
        }

        public void run() {
            synchronized (mLock) {
                Looper.prepare();
                mLooper = Looper.myLooper();
                mLock.notifyAll();
            }
            Looper.loop();
        }

        public void quit() {
            mLooper.quit();
        }
    }
}
