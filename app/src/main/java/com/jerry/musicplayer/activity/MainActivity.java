package com.jerry.musicplayer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.jerry.musicplayer.adapter.MPageAdapter;
import com.jerry.musicplayer.R;
import com.jerry.musicplayer.fragment.MusicLibFragment;
import com.jerry.musicplayer.fragment.MyMusicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 16-1-3.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    /**
     * viewpager选中第一个（标签）
     */
    private final int TAG_MUSIC_LIB = 0;
    /**
     * viewpager选中第二个（标签）
     */
    private final int TAG_MY_MUSIC = 1;

    private final String mTag = "MainActivity";
    private ViewPager mViewPage;
    private TextView mMusicLib;
    private TextView mMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mViewPage = (ViewPager) findViewById(R.id.main_vpager);
        mMusicLib = (TextView) findViewById(R.id.tab_musiclib);
        mMusic = (TextView) findViewById(R.id.tab_mymusic);
        mMusicLib.setOnClickListener(this);
        mMusic.setOnClickListener(this);
        mViewPage.setOnPageChangeListener(new MPageChangeListener());
        List<Fragment> fragments = new ArrayList<Fragment>();
        /**
         * 添加viewPage的两个子页面
         */
        fragments.add(new MusicLibFragment());
        fragments.add(new MyMusicFragment());
//        List<View> mViews = new ArrayList<View>();
//        View mMusicLibView = getLayoutInflater().inflate(R.layout.fragment_music_lib, null);
//        View mMusicView = getLayoutInflater().inflate(R.layout.fragment_my_music, null);
//        mViews.add(mMusicLibView);
//        mViews.add(mMusicLibView);
        mViewPage.setAdapter(new MPageAdapter(getSupportFragmentManager(), fragments));

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_musiclib://点击音乐库
                mViewPage.setCurrentItem(TAG_MUSIC_LIB);
                break;
            case R.id.tab_mymusic://点击我的音乐
                mViewPage.setCurrentItem(TAG_MY_MUSIC);
                break;
        }

    }

    class MPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            switch (i) {
                case TAG_MUSIC_LIB://当选中第一个页面时，设置tab
                    mMusicLib.setTextColor(MainActivity.this.getResources().getColor(R.color.tab_text_color_foc));
                    mMusic.setTextColor(MainActivity.this.getResources().getColor(R.color.tab_text_color_nor));
                    break;
                case TAG_MY_MUSIC://当选中第二个页面时，设置tab
                    mMusicLib.setTextColor(MainActivity.this.getResources().getColor(R.color.tab_text_color_nor));
                    mMusic.setTextColor(MainActivity.this.getResources().getColor(R.color.tab_text_color_foc));
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }


}
