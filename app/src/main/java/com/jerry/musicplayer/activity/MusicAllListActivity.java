package com.jerry.musicplayer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jerry.musicplayer.R;
import com.jerry.musicplayer.adapter.MListAdapter;
import com.jerry.musicplayer.bean.MusicInfo;
import com.jerry.musicplayer.service.MediaPlayerService;
import com.jerry.musicplayer.utils.MusicUtils;

import java.util.List;

/**
 * Created by Jerry on 2016/1/3.
 */
public class MusicAllListActivity extends BaseActivity {
    private ListView mListView;

    /**
     * 初始化界面
     */
    @Override
    protected View initView() {
        View mView = View.inflate(this, R.layout.activity_music_all_list, null);
        mListView = (ListView) mView.findViewById(R.id.music_list);//得到listview对象的引用
        // 为listview设置adapter来绑定数据
        mListView.setAdapter(new MListAdapter(this, getPlayList()));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击我的音乐要执行的行为
                MediaPlayerService.Stub service = MusicUtils.getService();
                MusicInfo currInfo = getPlayList().get(position);
                service.open(currInfo.get_id(), currInfo.getData());
                service.play();
                setCurrMusic(currInfo);
            }
        });

        return mView;
    }
}
