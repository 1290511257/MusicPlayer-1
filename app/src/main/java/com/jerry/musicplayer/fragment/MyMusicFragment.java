package com.jerry.musicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jerry.musicplayer.R;
import com.jerry.musicplayer.activity.MusicAllListActivity;
import com.jerry.musicplayer.activity.MusicLocalActivity;
import com.jerry.musicplayer.activity.UserOptionActivity;

/**
 * Created by Jerry on 2016/1/3.
 */
public class MyMusicFragment extends Fragment implements View.OnClickListener {
    private TextView mUserOption;
    private RelativeLayout mAllMusic;
    private RelativeLayout mLocalMusic;
    private RelativeLayout mStarMusic;
    private RelativeLayout mAddMtoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_my_music, null);
        //设置mView界面
        initView(mView);
        return mView;
    }

    /**
     * 初始化界面
     *
     * @param mView
     */
    private void initView(View mView) {
        mUserOption = (TextView) mView.findViewById(R.id.my_music_user_option);
        mAllMusic = (RelativeLayout) mView.findViewById(R.id.my_music_all);
        mLocalMusic = (RelativeLayout) mView.findViewById(R.id.my_music_local);
        mStarMusic = (RelativeLayout) mView.findViewById(R.id.my_music_single);
        mAddMtoList = (RelativeLayout) mView.findViewById(R.id.my_music_add_list);

        mUserOption.setOnClickListener(this);
        mAllMusic.setOnClickListener(this);
        mLocalMusic.setOnClickListener(this);
        mStarMusic.setOnClickListener(this);
        mAddMtoList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.my_music_user_option://进入个人设置页面
                 intent = new Intent(this.getActivity(),UserOptionActivity.class);
                this.startActivity(intent);
                break;
            case R.id.my_music_all://进入全部歌曲列表
                intent = new Intent(this.getActivity(),MusicAllListActivity.class);
                this.startActivity(intent);
                break;
            case R.id.my_music_local://进入本地歌曲列表
                intent= new Intent(this.getActivity(), MusicLocalActivity.class);
                this.startActivity(intent);
                break;
            case R.id.my_music_single://进入我的收藏单曲列表
                break;
            case R.id.my_music_add_list://进入添加歌曲列表
                break;
        }

    }
}
