package com.jerry.musicplayer.music;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.jerry.musicplayer.R;
import com.jerry.musicplayer.utils.UIUtils;

import java.io.IOException;

/**
 * Created by jerry on 16-1-5.
 */
public class MultiPlayer {
    private final MediaPlayer mediaPlayer;
    private boolean mIsInitialized = false;

    private MultiPlayer() {
        super();
        mediaPlayer = new MediaPlayer();
    }

    public static MultiPlayer getInstance() {
        return new MultiPlayer();
    }

    public void setDataSource(String path) {
        if (path == null) {
            mIsInitialized = false;
            return;
        }
        try {
            mediaPlayer.reset();
            if (path.startsWith("content://")) {
                mediaPlayer.setDataSource(UIUtils.getContext(), Uri.parse(path));
            } else {
                mediaPlayer.setDataSource(path);
            }
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();

        } catch (IOException e) {
            mIsInitialized = false;
            e.printStackTrace();
        }
        //当音乐播放完成时执行该方法
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
        mIsInitialized = true;
    }

    public boolean isInitialized() {
        return mIsInitialized;
    }

    public void play() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void seekTo(int msec) {
        mediaPlayer.seekTo(msec);
    }

    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }

    public void getDuration() {
        mediaPlayer.getDuration();
    }

    public long getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void release() {
        stop();
        mediaPlayer.release();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }


}
