package com.jerry.musicplayer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jerry.musicplayer.R;
import com.jerry.musicplayer.activity.BaseActivity;
import com.jerry.musicplayer.adapter.MListAdapter;
import com.jerry.musicplayer.bean.MusicInfo;
import com.jerry.musicplayer.service.MediaPlayerService;
import com.jerry.musicplayer.utils.MusicUtils;

import java.util.List;

/**
 * Created by Jerry on 2016/1/9.
 */
public abstract class MusicListFragment extends Fragment {
    private ListView mLocalList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_music_local_list, null);
        mLocalList = (ListView) mView.findViewById(R.id.local_list);
        mLocalList.setAdapter(new MListAdapter(getActivity(), getMusicInfos()));
        mLocalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaPlayerService.Stub service = MusicUtils.getService();
                MusicInfo musicInfo = getMusicInfos().get(position);
                service.open(musicInfo.get_id(), musicInfo.getData());
                service.play();
                //强制转 父转子 保存当前状态
                ((BaseActivity)getActivity()).setCurrMusic(musicInfo);
            }
        });
        //设置mView界面
        return mView;
    }

    /**
     * 抽象方法的目的是：传值给调用方
     *
     * @return
     */
    public abstract List<MusicInfo> getMusicInfos();
}
