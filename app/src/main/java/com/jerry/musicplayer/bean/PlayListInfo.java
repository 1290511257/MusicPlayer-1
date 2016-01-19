package com.jerry.musicplayer.bean;

import java.io.Serializable;

/**
 * Created by Jerry on 2016/1/4.
 */
public class PlayListInfo implements Serializable {
    private String listName;
    private long ids[];

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public long[] getIds() {
        return ids;
    }

    public void setIds(long[] ids) {
        this.ids = ids;
    }
}
