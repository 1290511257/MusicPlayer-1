package com.jerry.musicplayer.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jerry.musicplayer.R;
import com.jerry.musicplayer.bean.MusicInfo;
import com.jerry.musicplayer.utils.MusicUtils;

import java.util.List;

/**
 * Created by Jerry on 2016/1/5.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    private String mTag = "BaseActivity";
    private FrameLayout mRootView;
    private static List<MusicInfo> mPlayList;
    private RelativeLayout mPlayBottom;
    private ImageView mArtistImg;
    private TextView mTitle;
    private TextView mArtist;
    private static MusicInfo currMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);
        initData();
        int colors[] = {0xff255779, 0xff3e7492, 0xffa6c0cd};//分别为开始颜色，中间夜色，结束颜色
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        mRootView = (FrameLayout) findViewById(R.id.main_root_view);
        mRootView.setBackground(gd);
        mRootView.removeAllViews();
        mRootView.addView(initView());
        mPlayBottom = (RelativeLayout) findViewById(R.id.main_play_bottom_bar);
        mArtistImg = (ImageView) findViewById(R.id.main_artist_img);
        mTitle = (TextView) findViewById(R.id.main_title);
        mArtist = (TextView) findViewById(R.id.main_artist);


        mPlayBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, MusicPlayActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.translate_bottom_top, android.R.anim.fade_out);
//                overridePendingTransition(android.R.anim.fade_out,android.R.anim.fade_out);
            }
        });

    }

    /**
     * 初始化界面
     */
    protected abstract View initView();


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currMusic!=null){
            setBottomInfo();
        }
    }

    /**
     * 设置底部歌曲信息
     */
    protected void setBottomInfo() {
        mArtistImg.setImageBitmap(MusicUtils.getArtwork(this, currMusic.get_id(), currMusic.getAlbumId()));
        mTitle.setText(currMusic.getTitle());
        mArtist.setText(currMusic.getArtist());
    }

    /**
     * 设置当前播放的音乐信息
     *
     * @param info
     */
    public void setCurrMusic(MusicInfo info) {
        this.currMusic = info;
        setBottomInfo();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mPlayList = MusicUtils.getMusicInfoList(BaseActivity.this);
                long currentId = MusicUtils.getSharePre(BaseActivity.this);
                currMusic = MusicUtils.getMusicInfoById(BaseActivity.this, currentId);
            }
        }).start();
    }

    /**
     * 获取播放列表
     *
     * @return
     */
    public List<MusicInfo> getPlayList() {
        return mPlayList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}

