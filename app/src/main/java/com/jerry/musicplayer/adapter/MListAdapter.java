package com.jerry.musicplayer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jerry.musicplayer.R;
import com.jerry.musicplayer.bean.MusicInfo;

import java.util.List;

/**
 * Created by Jerry on 2016/1/4.
 */
public class MListAdapter extends BaseAdapter {

    private final List<MusicInfo> musics;
    private final Context content;

    public MListAdapter(Context context, List<MusicInfo> musics) {
        this.content=context;
        this.musics=musics;
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Object getItem(int position) {
        return musics.get(position);
    }

    @Override
    public long getItemId(int position) {
       return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder mHolder;
        if(convertView==null){
            mHolder= new Holder();
            convertView = View.inflate(content, R.layout.activity_music_all_list_item, null);
            mHolder.mTitle = (TextView) convertView.findViewById(R.id.music_list_title);
            mHolder.mArtist = (TextView)convertView.findViewById(R.id.music_list_artist);
            mHolder.mAlbum = (TextView)convertView.findViewById(R.id.music_list_album);
            convertView.setTag(mHolder);
        }else{
            mHolder = (Holder) convertView.getTag();
        }
        mHolder.mTitle.setText(musics.get(position).getTitle());
        mHolder.mArtist.setText(musics.get(position).getArtist());
        mHolder.mAlbum.setText(musics.get(position).getAlbum());
        return convertView;
    }

    /**
     * 对Listview的Item进行封装，复用
     */
    class Holder{
        TextView mTitle,mArtist,mAlbum;
    }
}
