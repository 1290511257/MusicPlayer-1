package com.jerry.musicplayer.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.jerry.musicplayer.R;
import com.jerry.musicplayer.adapter.MPageAdapter;
import com.jerry.musicplayer.fragment.MusicLibFragment;
import com.jerry.musicplayer.fragment.MyMusicFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 2016/1/5.
 */
public class HomeActivity extends BaseActivity {
    private final String mTag = "HomeActivity";
    private ListView mListView;
    /**
     * viewpager选中第一个（标签）
     */
    private final int TAG_MUSIC_LIB = 0;

    /**
     * viewpager选中第二个（标签）
     */
    private final int TAG_MY_MUSIC = 1;
    private ViewPager mViewPage;
    private TextView mMusicLib;
    private TextView mMusic;

    @Override
    protected View initView() {
        View mView = View.inflate(this, R.layout.activity_home, null);

        mViewPage = (ViewPager) mView.findViewById(R.id.main_vpager);
        mMusicLib = (TextView) mView.findViewById(R.id.tab_musiclib);
        mMusic = (TextView) mView.findViewById(R.id.tab_mymusic);
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
        return mView;
//初始化音乐播放列表
//        mListView = (ListView) findViewById(R.id.music_list);//得到listview对象的引用
//        /**
//         * 为listview设置adapter来绑定数据
//         */
//        mListView.setAdapter(new MListAdapter(this, getPlayList()));
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                /*
//                * 点击我的音乐要执行的行为*/
//                // TODO: 2016/1/4  点击我的音乐要执行的行为
//                MediaPlayerService.Stub service = MusicUtils.getService();
//                service.open(getPlayList().get(position).getData());
//                service.play();
//            }
//        });
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
                    mMusicLib.setTextColor(HomeActivity.this.getResources().getColor(R.color.tab_text_color_foc));
                    mMusic.setTextColor(HomeActivity.this.getResources().getColor(R.color.tab_text_color_nor));
                    break;
                case TAG_MY_MUSIC://当选中第二个页面时，设置tab
                    mMusicLib.setTextColor(HomeActivity.this.getResources().getColor(R.color.tab_text_color_nor));
                    mMusic.setTextColor(HomeActivity.this.getResources().getColor(R.color.tab_text_color_foc));
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
