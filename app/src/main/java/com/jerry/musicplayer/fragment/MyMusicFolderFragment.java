package com.jerry.musicplayer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jerry.musicplayer.R;
import com.jerry.musicplayer.adapter.FolderListAdapter;
import com.jerry.musicplayer.bean.FolderInfo;
import com.jerry.musicplayer.bean.MusicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 2016/1/9.
 */
public class MyMusicFolderFragment extends Fragment {
    private ListView mFolderList;
    private List<FolderInfo> folders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        folders = new ArrayList<FolderInfo>();
        FolderInfo info = new FolderInfo();
        info.setName("111");
        info.setCount(1);
        info.setPath("hfjfhfheifj");
        folders.add(info);
        View mView = inflater.inflate(R.layout.fragment_music_local_folder, null);
        mFolderList = (ListView) mView.findViewById(R.id.music_folder_list);
        mFolderList.setAdapter(new FolderListAdapter(getActivity(), folders));
        mFolderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        })                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ;
        //设置mView界面
        return mView;
    }

}
