package com.jerry.musicplayer.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.jerry.musicplayer.R;
import com.jerry.musicplayer.adapter.MPageAdapter;
import com.jerry.musicplayer.bean.MusicInfo;
import com.jerry.musicplayer.fragment.MusicListFragment;
import com.jerry.musicplayer.fragment.MyMusicFolderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 2016/1/9.
 */
public class MusicLocalActivity extends BaseActivity {
    private final String mTag = "MusicLocalActivity";
    /**
     * viewpager选中第一个（标签）
     */
    private final int TAG_MUSIC_LOCAL_LIST = 0;
    /**
     * viewpager选中第二个（标签）
     */
    private final int TAG_MUSIC_FILE = 1;
    private ViewPager mViewPager;
    private TextView mLocalMusicList;
    private TextView mFile;
    @Override
    protected View initView() {
        View mView = View.inflate(this, R.layout.activity_music_local, null);
        mViewPager = (ViewPager) mView.findViewById(R.id.main_vlocal_music);
        mLocalMusicList = (TextView) mView.findViewById(R.id.tab_local_music_list);
        mFile = (TextView) mView.findViewById(R.id.tab_file);
        mLocalMusicList.setOnClickListener(this);
        mFile.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new MPageChangeListener());
        List<Fragment> fragments = new ArrayList<Fragment>();
        /**
         * 添加viewPage的两个子页面
         */
        fragments.add(new MusicListFragment() {
            /**
             * 通过接口的实现方法 返回数据
             * 抽象方法 传递数据
             * @return
             */
            @Override
            public List<MusicInfo> getMusicInfos() {
                return getPlayList();
            }
        });
        fragments.add(new MyMusicFolderFragment());
        mViewPager.setAdapter(new MPageAdapter(getSupportFragmentManager(), fragments));

        return mView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_local_music_list://点击歌曲列表
                mViewPager.setCurrentItem(TAG_MUSIC_LOCAL_LIST);
                break;
            case R.id.tab_file://点击文件夹
                mViewPager.setCurrentItem(TAG_MUSIC_FILE);
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
                case TAG_MUSIC_LOCAL_LIST://当选中第一个页面时，设置tab
                    mLocalMusicList.setTextColor(MusicLocalActivity.this.getResources().getColor(R.color.tab_text_color_foc));
                    mFile.setTextColor(MusicLocalActivity.this.getResources().getColor(R.color.tab_text_color_nor));
                    break;
                case TAG_MUSIC_FILE://当选中第二个页面时，设置tab
                    mFile.setTextColor(MusicLocalActivity.this.getResources().getColor(R.color.tab_text_color_foc));
                    mLocalMusicList.setTextColor(MusicLocalActivity.this.getResources().getColor(R.color.tab_text_color_nor));
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
