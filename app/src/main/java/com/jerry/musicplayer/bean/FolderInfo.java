package com.jerry.musicplayer.bean;

import java.io.Serializable;

/**
 * Created by Jerry on 2016/1/10.
 */
public class FolderInfo implements Serializable{
    //     歌曲id   歌曲数
    private int id,count;
    //    歌曲名     歌曲路径
    private String name,path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
