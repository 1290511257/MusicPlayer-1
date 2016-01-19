package com.jerry.musicplayer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jerry.musicplayer.R;
import com.jerry.musicplayer.bean.FolderInfo;

import java.util.List;

/**
 * Created by Jerry on 2016/1/10.
 */
public class FolderListAdapter extends BaseAdapter{
    private Context context;
    private List<FolderInfo> folderInfos;

    public FolderListAdapter(Context context,List<FolderInfo> folderInfos) {
        this.folderInfos = folderInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return folderInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return folderInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder mHolder;
        if(convertView==null){
            mHolder = new Holder();
            convertView = View.inflate(context, R.layout.fragment_music_local_folder_item,null);
            mHolder.mName = (TextView) convertView.findViewById(R.id.folder_item_name);
            mHolder.mCount = (TextView) convertView.findViewById(R.id.folder_item_count);
            mHolder.mPath = (TextView) convertView.findViewById(R.id.folder_item_path);
            convertView.setTag(mHolder);//设置标记
        }else{
            mHolder = (Holder) convertView.getTag();
        }
        mHolder.mName.setText(folderInfos.get(position).getName());
        mHolder.mCount.setText(folderInfos.get(position).getCount()+"首歌");
        mHolder.mPath.setText(folderInfos.get(position).getPath());
        return convertView;
    }

    /**
     * item的临时封装对象 复用
     */

    class Holder{
        TextView mName,mCount,mPath;

    }
}
