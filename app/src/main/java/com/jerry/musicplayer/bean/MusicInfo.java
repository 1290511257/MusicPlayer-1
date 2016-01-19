package com.jerry.musicplayer.bean;

import java.io.Serializable;

/**
 * Created by jerry on 16-1-4.
 */
public class MusicInfo implements Serializable {
    //音乐id
    private long _id;
    //歌曲的总播放时长， 歌曲文件的大小
    private long duration, size, albumId;
    //歌曲的名称， 歌曲的歌手名， 歌曲的专辑名唱片集， 歌曲文件的全路径 , 歌曲文件的名称, 歌曲文件的发行日期
    private String title, artist, album, data, display_name, year;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }
}
